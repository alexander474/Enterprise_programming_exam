package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import org.openqa.selenium.WebDriver;

public class TripDetailPO extends LayoutPO {

    public TripDetailPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public TripDetailPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Details");
    }
}
