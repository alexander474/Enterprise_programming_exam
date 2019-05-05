package no.ab.moc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TripService tripService;

    @PostConstruct
    public void initialize() {
        String email = "foo@bar.com";
        String adminEmail = "admin@admin.com";

        attempt(() -> userService.createUser(email, "foo", "bar", "a", false));
        attempt(() -> userService.createUser(adminEmail, "admin", "admin", "a", true));

        long t1 = tripService.createTrip("Italy", "Cool tour to italy", 2000, "Rome", new Date());
        long t2 = tripService.createTrip("USA", "Cool tour to usa", 4000,"California", new Date());
        long t3 = tripService.createTrip("United Kingdom", "Cool tour to usa", 1000, "London", new Date());
        long t4 = tripService.createTrip("Israel", "Cool tour to usa", 1500, "Tel a viv", new Date());

        attempt(() -> purchaseService.createPurchase(email, t1));
        attempt(() -> purchaseService.createPurchase(email, t3));

    }

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
