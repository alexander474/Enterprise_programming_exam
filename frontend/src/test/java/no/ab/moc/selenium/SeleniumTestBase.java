package no.ab.moc.selenium;


import no.ab.moc.selenium.po.*;
import no.ab.moc.selenium.po.admin.CreateItemPO;
import no.ab.moc.selenium.po.admin.UsersPO;
import no.ab.moc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.openqa.selenium.WebDriver;

import java.util.List;
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
    private CreateItemPO createItem;


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
        String password = getUniqueId();
        String name = getUniqueId();
        String surName = getUniqueId();
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
        String password = getUniqueId();
        String name = getUniqueId();
        assertTrue(userService.createUser(user,name,"admin",password, false));

        assertFalse(home.isLoggedIn());
        home = home.doLogin(user, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(name));
    }

    @Test
    public void testDisableAndEnableUser(){
        String user = getUniqueId();
        String userPassword = getUniqueId();
        String admin = getUniqueId();
        String adminPassword = getUniqueId();

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

    @Test
    public void testFilterItems(){
        String category = "ACTION";
        String defaultCategory = "ALL";
        assertEquals(home.getCurrentSelectedCategory(), defaultCategory);
        home.filterCategories(category);
        assertEquals(home.getCurrentSelectedCategory(), category);
        int item_count_after_filter = home.getAmountOfDisplayedItems();

        String email = getUniqueId();
        String password = "a";
        assertTrue(userService.createUser(email, "foo", "bar", password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());


        createItem = home.toCreateItem();
        String title = getUniqueId();
        String description = getUniqueId();
        createItem.createItem(title, description, category);
        home.toStartingPage();
        int item_count_after_new_created_item = home.getAmountOfDisplayedItems();

        //Checking the amount of items in a category before and after adding a new one with that category
        //Then checking that the value is incremented by 1
        assertEquals(item_count_after_filter+1, item_count_after_new_created_item);
    }

    @Test
    public void testCreateItem(){
        assertFalse(home.isLoggedIn());
        String email = getUniqueId();
        String password = "a";
        assertTrue(userService.createUser(email, "foo", "bar", password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());
        int itemsCount = home.getAmountOfDisplayedItems();


        createItem = home.toCreateItem();
        String title = getUniqueId();
        String description = getUniqueId();
        String category = "ACTION";
        createItem.createItem(title, description, category);
        home.toStartingPage();
        assertEquals(itemsCount+1, home.getAmountOfDisplayedItems());
    }

    @Test
    public void testItemSorting(){
        List<Double> list = home.getItemsAverageRatings();
        for(int i=0; i<list.size()-1; i++){
            assertTrue(list.get(i) >= list.get(i+1));
        }
    }


}
