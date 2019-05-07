package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import no.ab.moc.selenium.po.admin.CreateItemPO;
import no.ab.moc.selenium.po.admin.UsersPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Home");
    }

    public boolean isLoggedIn() {
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }

    public UsersPO toUsers(){
        clickAndWait("usersId");
        UsersPO po = new UsersPO(this);

        assertTrue(po.isOnPage());
        return po;
    }

    public CreateItemPO toCreateItem() {
        clickAndWait("createItemId");

        CreateItemPO po = new CreateItemPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public ItemDetailPO toItemDetail(String row){
        clickAndWait(row+"detailBtn");

        ItemDetailPO po = new ItemDetailPO(this);
        assertTrue(po.isOnPage());
        return po;
    }

    public void filterCategories(String category){
        Select drop = new Select(driver.findElement(By.id("categoryFilterId")));
        drop.selectByValue(category);
    }

    public String getCurrentSelectedCategory(){
        Select drop = new Select(driver.findElement(By.id("categoryFilterId")));
        return drop.getFirstSelectedOption().getText();
    }



    public List<Double> getItemsAverageRatings(){
        List<Double> returnValue = new ArrayList<>();
        List<WebElement> list = driver.findElements(
                By.xpath("//table[@id='itemsTable']/tbody/tr/td[4]/label[@class='itemRating']")
        );
        int size = list.size();
        for(int i = 0; i<size; i++) {
            WebElement element = driver.findElement(By.xpath("//*[@id='itemsTable:"+i+":rating']"));
            if (!element.getText().equalsIgnoreCase("No rating")) {
                String text = element.getText();
                returnValue.add(Double.parseDouble(text.split("/")[0]));
            }
        }
        return returnValue;
    }



    public int getAmountOfDisplayedItems(){
        List<WebElement> e = driver.findElements(
                By.xpath("//table[@id='itemsTable']/tbody/tr")
        );
        return e.size();
    }
}
