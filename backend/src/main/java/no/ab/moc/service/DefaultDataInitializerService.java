package no.ab.moc.service;

import no.ab.moc.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {


    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;


    @Autowired
    private RankService rankService;


    @PostConstruct
    public void initialize() {
        String email = "foo@bar.com";
        String adminEmail = "admin@admin.com";

        attempt(() -> userService.createUser(email, "foo", "bar", "a", false));
        attempt(() -> userService.createUser(adminEmail, "admin", "admin", "a", true));

        Long itemOneId = itemService.createItem("title", "description", "action");
        Long RankOne = rankService.createRank(email, itemOneId, "comment", 2);


    }

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
