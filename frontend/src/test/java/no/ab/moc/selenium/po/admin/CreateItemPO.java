package no.ab.moc.selenium.po.admin;

import no.ab.moc.selenium.PageObject;
import no.ab.moc.selenium.po.LayoutPO;
import org.openqa.selenium.WebDriver;

public class CreateItemPO extends LayoutPO {

    public CreateItemPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public CreateItemPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Create Item");
    }

    public void createItem(String title, String description, String category){
        setText("addTitle", title);
        setText("addDescription", description);
        setText("addCategory", category);
        clickAndWait("addBtn");
    }
}
