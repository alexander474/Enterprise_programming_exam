package no.ab.moc.service;


import no.ab.moc.entity.Item;
import no.ab.moc.entity.Rank;
import no.ab.moc.entity.User;
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
        if(item.getRanks().size()==0) return 0d;
        return item.getRanks().stream().mapToDouble(Rank::getScore).average().getAsDouble();
    }


    public long createItem(String title, String description, String category){
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);

        em.persist(item);
        return item.getId();
    }

    public List<String> getAllCategories(){
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);
        List<String> list = new ArrayList<>();
        for (Item item : query.getResultList()) {
            String category = item.getCategory().toUpperCase();
            if(!list.contains(category))list.add(category);
        }
        return list;
    }

    public List<Item> getAllItemsSortedByScore(Boolean getAll, String category) {
        TypedQuery<Item> query;
        if(!getAll){
            query = em.createQuery("select i from Item i where i.category = ?1", Item.class);
            query.setParameter(1, category);
        }else{
            query = em.createQuery("select i from Item i", Item.class);
        }
        // SOURCE https://stackoverflow.com/questions/36720544/sorting-finding-average-comparing-and-much-more-in-2d-object-array/36720771#36720771
        List<Item> list = query.getResultList().stream()
                            .sorted(Comparator.comparingDouble(i->getItemRankAverage(i.getId())))
                            .collect(Collectors.toList());
        Collections.reverse(list);
        return list;
    }

    public boolean userHasRankedItem(String email, Long itemId){
        Boolean ranked = false;
        User user = em.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User " + email + " does not exist");
        }
        Item item = em.find(Item.class, itemId);
        if(item == null){
            throw new IllegalArgumentException("Item " + itemId + " does not exist");
        }

        for(Rank r : item.getRanks()){
            if(r.getUser().getEmail().equalsIgnoreCase(email)) ranked = true;
        }

        return ranked;
    }

    public Rank getItemUserRank(String email, Long itemId){
        User user = em.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User " + email + " does not exist");
        }
        Item item = em.find(Item.class, itemId);
        if(item == null){
            throw new IllegalArgumentException("Item " + itemId + " does not exist");
        }

        for(Rank r : item.getRanks()){
            if(r.getUser().getEmail().equalsIgnoreCase(email)) return r;
        }
        return null;
    }


}
