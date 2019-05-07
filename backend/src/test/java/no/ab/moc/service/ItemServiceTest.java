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

    @Test
    public void testGetItemAverage(){
        String title = "testTitle";
        String email = "foo@bar.com";
        String email2 = "foo2@bar.com";
        String email3 = "foo3@bar.com";
        String comment = "testComment";
        int score = 2;
        int score2 = 2;
        int score3 = 4;
        createUser(email);
        createUser(email2);
        createUser(email3);
        long id = itemService.createItem(title, "testDesc", "action");
        int sizeOfRanks = itemService.getItem(id).getRanks().size();
        long rankId = rankService.createRank(email, id, comment, score);
        long rankId2 = rankService.createRank(email2, id, comment, score3);
        long rankId3 = rankService.createRank(email3, id, comment, score2);
        Item item = itemService.getItem(id);

        assertEquals(title, item.getTitle());
        assertEquals(sizeOfRanks+3, itemService.getItem(id).getRanks().size());
        assertEquals(2.6666666666666665,itemService.getItemRankAverage(item.getId()).doubleValue());
    }

    @Test
    public void testGetAllCategories() {
        String[] list = new String[]{"ACTION", "ADVENTURE", "CRIME", "DRAMA", "WESTERN"};

        for (String s : list) {
            itemService.createItem("title", "desc", s);
        }

        for (int i = 0; i < itemService.getAllCategories().size(); i++) {
            assertTrue(itemService.getAllCategories().contains(list[i]));
        }

    }

    @Test
    public void testGetUserItemRank(){
        String title = "testTitle";
        String email = "foo@bar.com";
        String email2 = "foo2@bar.com";
        String email3 = "foo3@bar.com";
        String comment = "testComment";
        int score = 2;
        int score2 = 2;
        int score3 = 4;
        createUser(email);
        createUser(email2);
        createUser(email3);
        long id = itemService.createItem(title, "testDesc", "action");
        int sizeOfRanks = itemService.getItem(id).getRanks().size();
        long rankId = rankService.createRank(email, id, comment, score);
        long rankId2 = rankService.createRank(email2, id, comment, score3);
        long rankId3 = rankService.createRank(email3, id, comment, score2);
        Item item = itemService.getItem(id);

        assertEquals(title, item.getTitle());
        assertEquals(sizeOfRanks+3, itemService.getItem(id).getRanks().size());
        assertTrue(itemService.userHasRankedItem(email, item.getId()));
        assertTrue(itemService.userHasRankedItem(email2, item.getId()));
        assertTrue(itemService.userHasRankedItem(email3, item.getId()));
        assertEquals(rankService.getRank(rankId).getId(), itemService.getItemUserRank(email, item.getId()).getId());
        assertEquals(rankService.getRank(rankId2).getId(), itemService.getItemUserRank(email2, item.getId()).getId());
        assertEquals(rankService.getRank(rankId3).getId(), itemService.getItemUserRank(email3, item.getId()).getId());
    }

    @Test
    public void testGetUserItemRankWithErrorUser(){
        String title = "testTitle";
        String email = "error@error.com";
        String comment = "testComment";
        int score = 2;
        long id = itemService.createItem(title, "testDesc", "action");
        int sizeOfRanks = itemService.getItem(id).getRanks().size();
        assertThrows(IllegalArgumentException.class ,() -> rankService.createRank(email, id, comment, score));
        Item item = itemService.getItem(id);

        assertEquals(title, item.getTitle());
        assertEquals(sizeOfRanks, itemService.getItem(id).getRanks().size());

        assertThrows(IllegalArgumentException.class ,() -> itemService.userHasRankedItem(email, item.getId()));
        assertThrows(IllegalArgumentException.class ,() -> itemService.getItemUserRank(email, item.getId()));
    }

    @Test
    public void testGetUserItemRankWithErrorItem(){
        String email = "foo@bar.com";
        createUser(email);

        assertThrows(IllegalArgumentException.class ,() -> itemService.userHasRankedItem(email, -1L));
        assertThrows(IllegalArgumentException.class ,() -> itemService.getItemUserRank(email, -1L));
    }

    @Test
    public void testGetAllItemsSortedByScore(){
        String title = "testTitle";
        String email = "foo@bar.com";
        String email2 = "foo2@bar.com";
        String email3 = "foo3@bar.com";
        String comment = "testComment";
        int score = 2;
        int score2 = 3;
        int score3 = 4;

        createUser(email);
        createUser(email2);
        createUser(email3);
        long id = itemService.createItem(title, "testDesc", "action");
        long id2 = itemService.createItem(title, "testDesc", "adventure");
        long id3 = itemService.createItem(title, "testDesc", "drama");
        int sizeOfRanks = itemService.getItem(id).getRanks().size();
        long rankId = rankService.createRank(email, id, comment, score);
        long rankId2 = rankService.createRank(email2, id, comment, score);
        long rankId3 = rankService.createRank(email3, id, comment, score);
        long rankId4 = rankService.createRank(email, id2, comment, score2);
        long rankId5 = rankService.createRank(email2, id2, comment, score2);
        long rankId6 = rankService.createRank(email3, id2, comment, score2);
        long rankId7 = rankService.createRank(email, id3, comment, score3);
        long rankId8 = rankService.createRank(email2, id3, comment, score3);
        long rankId9 = rankService.createRank(email3, id3, comment, score3);
        Item item = itemService.getItem(id);
        Item item2 = itemService.getItem(id2);
        Item item3 = itemService.getItem(id3);

        List<Item> list = itemService.getAllItemsSortedByScore(true, null);
        List<Item> listFiltered = itemService.getAllItemsSortedByScore(false, "action");

        for(int i = 0; i<list.size()-1; i++){
            assertNotNull(list.get(i));
            assertTrue(itemService.getItemRankAverage(list.get(i).getId()) >= itemService.getItemRankAverage(list.get(i + 1).getId()));
        }

        for(Item i : listFiltered){
            assertTrue(i.getCategory().equalsIgnoreCase("action"));
        }

        assertEquals(title, item.getTitle());
        assertEquals(sizeOfRanks+3, itemService.getItem(id).getRanks().size());
    }






}
