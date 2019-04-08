package no.ab.moc.service;

import no.ab.moc.entity.Purchase;
import no.ab.moc.entity.Trip;
import no.ab.moc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TripService {

    @Autowired
    private EntityManager em;

    public List<Trip> getAllTrips(){
        TypedQuery<Trip> query = em.createQuery("select t from Trip t", Trip.class);
        return query.getResultList();
    }

    public Trip getTrip(long id){
        return em.find(Trip.class, id);
    }

    public long createTrip(String title, String description, double cost, String location, Date date){

        Trip trip = new Trip();
        trip.setTitle(title);
        trip.setDescription(description);
        trip.setCost(cost);
        trip.setLocation(location);
        trip.setDate(date);

        em.persist(trip);
        return trip.getId();
    }
}
