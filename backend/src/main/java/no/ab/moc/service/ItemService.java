package no.ab.moc.service;


import no.ab.moc.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

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

    public long createItem(String title, String description, String category){


        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);

        em.persist(item);
        return item.getId();
    }


}
