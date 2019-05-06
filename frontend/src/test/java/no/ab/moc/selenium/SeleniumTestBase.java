package no.ab.moc.selenium;


import no.ab.moc.selenium.po.*;
import no.ab.moc.selenium.po.admin.UsersPO;
import no.ab.moc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@test.com";
    }

    private IndexPO home;
    private UsersPO users;


    @Autowired
    private UserService userService;


    private IndexPO createNewUser(String email, String password, String name, String surname){

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(email,password,name,surname);
        assertNotNull(indexPO);

        return indexPO;
    }


    @BeforeEach
    public void initTest() {
        getDriver().manage().deleteAllCookies();
        home = new IndexPO(getDriver(), getServerHost(), getServerPort());
        home.toStartingPage();
        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String email = getUniqueId();
        String password = "123456789";
        String name = "foo";
        String surName = "bar";
        home = createNewUser(email, password, name, surName);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(name));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(name));
    }

    @Test
    public void testLoginAndLogout(){
        String user = getUniqueId();
        String password = "a";
        String name = "name";
        assertTrue(userService.createUser(user,name,"admin",password, false));

        assertFalse(home.isLoggedIn());
        home = home.doLogin(user, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(name));
    }

    @Test
    public void testDisableAndEnableUser(){
        String user = getUniqueId();
        String userPassword = "a";
        String admin = getUniqueId();
        String adminPassword = "b";

        assertTrue(userService.createUser(user,"password","admin",userPassword, false));
        assertTrue(userService.createUser(admin,"password","admin",adminPassword, true));

        assertFalse(home.isLoggedIn());
        home.doLogin(admin, adminPassword);
        assertTrue(home.isLoggedIn());
        users = home.toUsers();

        int userCount = users.getAmountOfUsers();
        users.disableUser(user);
        assertEquals(userCount, users.getAmountOfUsers());
        home.doLogout();
        assertFalse(home.isLoggedIn());

        home.doLoginWithDisabledUser(user, userPassword);
        assertFalse(home.isLoggedIn());

        home.doLogin(admin, adminPassword);
        assertTrue(home.isLoggedIn());
        users = home.toUsers();
        assertEquals(userCount, users.getAmountOfUsers());
        users.enableUser(user);
        home.doLogout();

        home.doLogin(user, userPassword);
        assertTrue(home.isLoggedIn());
    }


}
