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
import java.util.Random;
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
    private ItemDetailPO itemDetail;
    private UserDetailPO userDetail;


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
        String userName = getUniqueId();
        String adminName = getUniqueId();
        String userSurName = getUniqueId();
        String adminSurName = getUniqueId();

        assertTrue(userService.createUser(user,userName,userSurName,userPassword, false));
        assertTrue(userService.createUser(admin,adminName,adminSurName,adminPassword, true));

        assertFalse(home.isLoggedIn());
        home.doLogin(admin, adminPassword);
        assertTrue(home.isLoggedIn());
        users = home.toUsers();

        assertTrue(users.textExistsOnPage(user));
        assertTrue(users.textExistsOnPage(userName));
        assertTrue(users.textExistsOnPage(userSurName));
        assertTrue(users.textExistsOnPage(admin));
        assertTrue(users.textExistsOnPage(adminName));
        assertTrue(users.textExistsOnPage(adminSurName));

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

    @Test
    public void testItemDetailsAndCreateAndUpdateRating(){
        assertFalse(home.isLoggedIn());
        String email = getUniqueId();
        String name = getUniqueId();
        String surName = getUniqueId();
        String password = getUniqueId();
        assertTrue(userService.createUser(email, name, surName, password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());
        int itemsCount = home.getAmountOfDisplayedItems();

        for(int i=0; i<itemsCount; i++){
            itemDetail = home.toItemDetail(i);
            assertTrue(itemDetail.isOnPage());
            home.toStartingPage();
        }

        int randomRow = new Random().nextInt(itemsCount);
        itemDetail = home.toItemDetail(randomRow);
        String uniqueComment = getUniqueId();
        String score = "2";
        String newUniqueComment = getUniqueId();
        String newScore = "5";
        itemDetail.createRating(uniqueComment, score);
        assertTrue(itemDetail.textExistsOnPage(uniqueComment));
        assertTrue(itemDetail.textExistsOnPage(score));

        itemDetail.updateRating(newUniqueComment, score);
        assertTrue(itemDetail.textExistsOnPage(newUniqueComment));
        assertTrue(itemDetail.textExistsOnPage(newScore));
        assertFalse(itemDetail.textExistsOnPage(uniqueComment));
    }

    @Test
    public void testRateItemAndSeeUserDetails(){
        assertFalse(home.isLoggedIn());
        String email = getUniqueId();
        String name = getUniqueId();
        String surName = getUniqueId();
        String password = getUniqueId();
        assertTrue(userService.createUser(email, name, surName, password, true));

        home.doLogin(email, password);
        assertTrue(home.isLoggedIn());
        int itemsCount = home.getAmountOfDisplayedItems();

        int randomRow = new Random().nextInt(itemsCount);
        itemDetail = home.toItemDetail(randomRow);
        String uniqueComment = getUniqueId();
        String score = "2";
        itemDetail.createRating(uniqueComment, score);
        assertTrue(itemDetail.textExistsOnPage(uniqueComment));
        assertTrue(itemDetail.textExistsOnPage(score));

        home.toStartingPage();
        assertTrue(home.isOnPage());

        userDetail = home.toUserDetail();
        assertTrue(userDetail.isOnPage());
        assertTrue(userDetail.textExistsOnPage(email));
        assertTrue(userDetail.textExistsOnPage(name));
        assertTrue(userDetail.textExistsOnPage(surName));

        assertTrue(userDetail.textExistsOnPage(uniqueComment));
        assertTrue(userDetail.textExistsOnPage(score));

        assertFalse(userDetail.textExistsOnPage(password));


    }


}
