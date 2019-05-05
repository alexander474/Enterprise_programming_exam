package no.ab.moc.frontend.controller.admin;

import no.ab.moc.entity.Purchase;
import no.ab.moc.entity.Trip;
import no.ab.moc.service.PurchaseService;
import no.ab.moc.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Date;

@Named
@RequestScoped
public class AdminController {

    @Autowired
    TripService tripService;

    @Autowired
    PurchaseService purchaseService;

    private String title, description, location;
    private double cost;
    private Date date;

    private boolean tripExists(String title){
        boolean exists = false;
        for(Trip trip : tripService.getAllTrips()){
            if(trip.getTitle().equalsIgnoreCase(title)) exists = true;
        }
        return exists;
    }

    public String createTrip(){
        if(title.isEmpty()||description.isEmpty()||location.isEmpty()
        ||cost<=0){
            return "/admin/trip-registery.jsf?faces-redirect=true&error=true";
        }
        tripService.createTrip(title,description,cost,location,date);
        return "/admin/trip-registry.jsf?faces-redirect=true&success=true";
    }

    public void deleteTrip(Long tripId){
        tripService.deleteTrip(tripId);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
