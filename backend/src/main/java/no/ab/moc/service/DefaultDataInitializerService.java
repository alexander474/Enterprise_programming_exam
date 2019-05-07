package no.ab.moc.service;

import no.ab.moc.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Supplier;


// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java
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
        String email2 = "foo2@bar.com";
        String email3 = "foo3@bar.com";
        String adminEmail = "admin@admin.com";

        attempt(() -> userService.createUser(email, "foo1", "bar", "a", false));
        attempt(() -> userService.createUser(email2, "foo2", "bar", "a", false));
        attempt(() -> userService.createUser(email3, "foo3", "bar", "a", false));
        attempt(() -> userService.createUser(adminEmail, "admin", "admin", "a", true));

        // information source https://www.imdb.com/search/title?groups=top_250&sort=user_rating
        Long item1Id = itemService.createItem("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", "CRIME");
        Long item2Id = itemService.createItem("The Dark Knight", "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham. The Dark Knight must accept one of the greatest psychological and physical tests of his ability to fight injustice.", "ACTION");
        Long item3Id = itemService.createItem("The Godfather: Part 2", "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.", "CRIME");
        Long item4Id = itemService.createItem("Avengers: Endgame", "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.", "ACTION");
        Long item5Id = itemService.createItem("The Lord of the Rings: The Return of the King", "description", "ADVENTURE");
        Long item6Id = itemService.createItem("Pulp Fiction", "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.", "CRIME");
        Long item7Id = itemService.createItem("Schindler's List", "n German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.", "DRAMA");
        Long item8Id = itemService.createItem("The Good, the Bad and the Ugly", "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.", "WESTERN");
        Long item9Id = itemService.createItem("12 Angry Men", "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.", "DRAMA");
        Long item10Id = itemService.createItem("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.", "ACTION");
        Long item11Id = itemService.createItem("Fight Club", "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.", "DRAMA");
        Long item12Id = itemService.createItem("The Lord of the Rings: The Fellowship of the Ring", "description", "ADVENTURE");
        Long item13Id = itemService.createItem("Forrest Gump", "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man with an IQ of 75.", "DRAMA");
        Long item14Id= itemService.createItem("The Lord of the Rings: The Two Towers", "While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron's new ally, Saruman, and his hordes of Isengard.", "ADVENTURE");
        Long item15Id= itemService.createItem("The Matrix", "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.", "SCI-FI");
        Long item16Id= itemService.createItem("Goodfellas", "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.", "BIOGRAPHY");
        Long item17Id= itemService.createItem("Star Wars: Episode V - The Empire Strikes Back", "After the Rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.", "ACTION");
        Long item18Id= itemService.createItem("Interstellar", "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.", "ADVENTURE");
        Long item19Id= itemService.createItem("City of God", "In the slums of Rio, two kids' paths diverge as one struggles to become a photographer and the other a kingpin.", "CRIME");
        Long item20Id= itemService.createItem("Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits, and where humans are changed into beasts.", "ANIMATION");
        rankService.createRank(email, item1Id, "Very good movie", 5);
        rankService.createRank(email2, item1Id, "Bad movie", 1);
        rankService.createRank(email3, item1Id, "OK", 2);
        rankService.createRank(email2, item2Id, "Good movie", 4);
        rankService.createRank(email3, item3Id, "OK+", 3);
        rankService.createRank(email, item4Id, "OK", 2);
        rankService.createRank(email2, item5Id, "BAD, BAD, BAD movie!", 1);


    }

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
