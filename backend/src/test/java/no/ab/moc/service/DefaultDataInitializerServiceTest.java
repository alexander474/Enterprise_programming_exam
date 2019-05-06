package no.ab.moc.service;

import no.ab.moc.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

/**
 * Created by arcuri82 on 15-Dec-17.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = BEFORE_CLASS)
public class DefaultDataInitializerServiceTest {




    @Test
    public void testInit() {


    }
}