package no.ab.moc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {


    @Autowired
    private UserService userService;


    @PostConstruct
    public void initialize() {
        String email = "foo@bar.com";
        String adminEmail = "admin@admin.com";

        attempt(() -> userService.createUser(email, "foo", "bar", "a", false));
        attempt(() -> userService.createUser(adminEmail, "admin", "admin", "a", true));


    }

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
