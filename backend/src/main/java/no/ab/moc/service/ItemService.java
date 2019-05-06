package no.ab.moc.service;


import no.ab.moc.entity.Item;
import no.ab.moc.entity.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemService {

    @Autowired
    private EntityManager em;

    public List<Item> getAllItems(){
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);
        return query.getResultList();
    }

    public Item getItem(long id){
        return em.find(Item.class, id);
    }

    public Double getItemRankAverage(long id){
        Item item = em.find(Item.class, id);
        return item.getRanks().stream().mapToInt(Rank::getScore).average().getAsDouble();
    }


    public long createItem(String title, String description, String category){


        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);

        em.persist(item);
        return item.getId();
    }


    public List<Item> getAllItemsSortedByScore(String category) {
        TypedQuery<Item> query;
        if(category != null){
            query = em.createQuery("select i from Item i where i.category = ?1", Item.class);
            query.setParameter(1, category);
        }else{
            query = em.createQuery("select i from Item i", Item.class);
        }


        return query.getResultList().sort(i -> Comparator.comparing(getItemRankAverage(i.getId())));
    }
}
