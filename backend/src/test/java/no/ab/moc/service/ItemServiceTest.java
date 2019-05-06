package no.ab.moc.service;

import no.ab.moc.StubApplication;
import no.ab.moc.entity.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;

    @Autowired
    private RankService rankService;

    @Autowired
    private UserService userService;

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    @Test
    public void testCreateItem(){
        int sizeOfItems = itemService.getAllItems().size();
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        assertEquals(sizeOfItems+1, itemService.getAllItems().size());
        assertEquals(title, itemService.getItem(id).getTitle());
    }

    @Test
    public void testListAllItems(){
        int sizeOfItems = itemService.getAllItems().size();
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        List<Item> list = itemService.getAllItems();
        assertEquals(sizeOfItems+1, list.size());
    }

    @Test
    public void testGetItem(){
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        Item item = itemService.getItem(id);
        assertEquals(title, item.getTitle());
    }

    @Test
    public void testGetItemRanks(){
        String title = "testTitle";
        String email = "foo@bar.com";
        String comment = "testComment";
        int score = 4;
        boolean createdUser = createUser(email);
        long id = itemService.createItem(title, "testDesc", "action");
        int sizeOfRanks = itemService.getItem(id).getRanks().size();
        long rankId = rankService.createRank(email, id, comment, score);
        Item item = itemService.getItem(id);
        assertEquals(title, item.getTitle());
        assertEquals(sizeOfRanks+1, itemService.getItem(id).getRanks().size());

    }


}
