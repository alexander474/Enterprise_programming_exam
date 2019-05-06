package no.ab.moc.service;


import no.ab.moc.entity.Item;
import no.ab.moc.entity.Rank;
import no.ab.moc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RankService {

    @Autowired
    private EntityManager em;

    public List<Rank> getAllRanks(){
        TypedQuery<Rank> query = em.createQuery("select r from Rank r", Rank.class);
        return query.getResultList();
    }

    public Rank getRank(long id){
        return em.find(Rank.class, id);
    }


    public long createRank(String email, Long itemId, String comment, int score){

        User user = em.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User " + email + " does not exist");
        }
        Item item = em.find(Item.class, itemId);
        if(item == null){
            throw new IllegalArgumentException("Trip " + itemId + " does not exist");
        }
        if(score<1 || score>5){
            throw new IllegalArgumentException("Cannot create rank with size "+score);
        }

        Rank rank = new Rank();
        rank.setUser(user);
        rank.setItem(item);
        rank.setComment(comment);
        rank.setScore(score);

        em.persist(rank);
        return rank.getId();
    }

    public void updateRank(Rank rank){
        em.merge(rank);
    }

}
