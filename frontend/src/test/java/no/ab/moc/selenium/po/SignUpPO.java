package no.ab.moc.selenium.po;


import no.ab.moc.selenium.PageObject;

/**
 * Created by arcuri82 on 12-Feb-18.
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/SignUpPO.java
 */
public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO createUser(String email, String password, String name, String surname){

        setText("email", email);
        setText("name", name);
        setText("surname", surname);
        setText("password", password);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);

        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
