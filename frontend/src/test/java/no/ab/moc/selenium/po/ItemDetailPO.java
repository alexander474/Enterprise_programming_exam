package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import org.openqa.selenium.WebDriver;

public class ItemDetailPO extends LayoutPO{

    public ItemDetailPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public ItemDetailPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return false;
    }
}
