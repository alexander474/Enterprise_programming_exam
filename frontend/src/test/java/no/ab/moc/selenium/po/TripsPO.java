package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripsPO extends LayoutPO {

    public TripsPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public TripsPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Trips");
    }

    public boolean defaultTripsIsDisplayed(){
        return getDriver().findElement(By.id("tripsTable:0:title")).isDisplayed() &&
                getDriver().findElement(By.id("tripsTable:1:title")).isDisplayed() &&
                getDriver().findElement(By.id("tripsTable:2:title")).isDisplayed() &&
                getDriver().findElement(By.id("tripsTable:3:title")).isDisplayed();
    }

    public TripDetailPO toTripDetail(String row){
        clickAndWait(row+"tripDetailBtn");
        TripDetailPO po = new TripDetailPO(this);

        assertTrue(po.isOnPage());
        return po;
    }

    public int getTripCount(){
        List<WebElement> elements = driver.findElements(
                By.xpath("//table[@id='tripsTable']/tbody/tr")
        );
        return elements.size();
    }

}
