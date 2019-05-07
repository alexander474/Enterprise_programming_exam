package no.ab.moc.service;

import no.ab.moc.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by arcuri82 on 14-Dec-17.
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/ResetService.java
 */
@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase(){
        Query query = em.createNativeQuery("delete from user_roles");
        query.executeUpdate();

        deleteEntities(Rank.class);
        deleteEntities(Item.class);
        deleteEntities(User.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }

}
