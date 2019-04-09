package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Purchase;
import no.ab.moc.entity.Trip;
import no.ab.moc.service.PurchaseService;
import no.ab.moc.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class TripsController implements Serializable {

    @Autowired
    private TripService tripService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserInfoController userInfoController;

    private Trip trip;


    public Trip getTrip(long tripId){
        return tripService.getTrip(tripId);
    }

    public List<Trip> getAllTrips(){
        return tripService.getAllTrips();
    }

    public void bookTrip(){
        purchaseService.createPurchase(userInfoController.getUserEmail(), trip.getId());
    }

    public List<Trip> getUserTrips(){
        List<Purchase> purchases = purchaseService.getUserPurchase(userInfoController.getUserEmail());
        return purchases.stream().map(Purchase::getTrip).collect(Collectors.toList());
    }


    public String toTripDetailsPage(Trip trip){
        setTrip(trip);
        return "/trip-detail.jsf¥faces-redirect=true";
    }


    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
