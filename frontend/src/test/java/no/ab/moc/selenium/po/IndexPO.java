package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import no.ab.moc.selenium.po.admin.UsersPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        return getDriver().getTitle().contains("Title");
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

}
