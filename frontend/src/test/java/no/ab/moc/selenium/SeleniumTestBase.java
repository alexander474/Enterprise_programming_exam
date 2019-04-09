package no.ab.moc.selenium;


import no.ab.moc.selenium.po.IndexPO;
import no.ab.moc.selenium.po.SignUpPO;
import no.ab.moc.selenium.po.ui.MatchPO;
import no.ab.moc.selenium.po.ui.ResultPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();



    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement();
    }


    private IndexPO home;


}
