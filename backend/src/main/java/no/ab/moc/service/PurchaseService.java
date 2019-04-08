package no.ab.moc.service;

import no.ab.moc.entity.Purchase;
import no.ab.moc.entity.Trip;
import no.ab.moc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PurchaseService {

    @Autowired
    private EntityManager em;

    public List<Purchase> getAllPurchases(){
        TypedQuery<Purchase> query = em.createQuery("select p from Purchase p", Purchase.class);
        return query.getResultList();
    }

    public Purchase getPurchase(long id){
        return em.find(Purchase.class, id);
    }

    public long createPurchase(String email, Long tripId){

        User user = em.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User " + email + " does not exist");
        }

        Trip trip = em.find(Trip.class, tripId);
        if(trip == null){
            throw new IllegalArgumentException("Trip " + tripId + " does not exist");
        }

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setTrip(trip);
        em.persist(purchase);

        return purchase.getId();
    }
}
