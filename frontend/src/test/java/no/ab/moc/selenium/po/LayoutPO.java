package no.ab.moc.selenium.po;

import no.ab.moc.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Created by arcuri82 on 12-Feb-18.
 */
public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp(){

        clickAndWait("linkToSignupId");

        SignUpPO po = new SignUpPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public IndexPO doLogin(String email, String password) {
        clickAndWait("linkToLoginId");
        IndexPO po = new IndexPO(this);

        setText("username", email);
        setText("password", password);
        clickAndWait("submit");

        assertTrue(po.isOnPage());

        return po;

    }

    public IndexPO doLoginWithDisabledUser(String email, String password) {
        clickAndWait("linkToLoginId");
        IndexPO po = new IndexPO(this);

        setText("username", email);
        setText("password", password);
        clickAndWait("submit");

        return po;
    }



        public IndexPO doLogout(){
        clickAndWait("logoutId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public boolean isLoggedIn(){
        return getDriver().findElements(By.id("logoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }
}
