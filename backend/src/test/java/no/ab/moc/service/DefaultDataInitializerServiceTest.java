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
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerServiceTest.java
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = BEFORE_CLASS)
public class DefaultDataInitializerServiceTest {


    @Autowired
    private ItemService itemService;

    @Autowired
    private RankService rankService;



    @Test
    public void testInit() {
        assertTrue(itemService.getAllItems().size() > 0);
        assertTrue(rankService.getAllRanks().size() > 0);

    }
}