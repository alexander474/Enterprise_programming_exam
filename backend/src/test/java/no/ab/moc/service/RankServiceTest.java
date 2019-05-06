package no.ab.moc.service;


import no.ab.moc.StubApplication;
import no.ab.moc.entity.Rank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RankServiceTest extends ServiceTestBase{

    @Autowired
    RankService rankService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    public Long createItem(String title){
        return itemService.createItem(title, "testDesc", "action");
    }

    @Test
    public void testCreateRank(){
        String userEmail = "foo@bar.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = 2;
        boolean createdUser = createUser(userEmail);
        long createdItem = createItem(itemTitle);
        int sizeOfRanks = rankService.getAllRanks().size();
        long id = rankService.createRank(userEmail, createdItem, testComment, score);

        assertTrue(createdUser);
        assertNotNull(itemService.getItem(createdItem));
        assertNotNull(rankService.getRank(id));
        assertEquals(sizeOfRanks+1, rankService.getAllRanks().size());

    }

    @Test
    public void testGetAllRanks(){
        String userEmail = "foo@bar.com";
        String itemTitle = "testTitle";
        String userEmailTwo = "fooTwo@bar.com";
        String testComment = "testComment";
        int score = 2;
        int scoreTwo = 3;
        boolean createdUser = createUser(userEmail);
        boolean createdUserTwo = createUser(userEmailTwo);
        long createdItem = createItem(itemTitle);
        int sizeOfRanks = rankService.getAllRanks().size();
        long id = rankService.createRank(userEmail, createdItem, testComment, score);
        long idTwo = rankService.createRank(userEmailTwo, createdItem, testComment, scoreTwo);

        assertTrue(createdUser);
        assertTrue(createdUserTwo);
        assertNotNull(itemService.getItem(createdItem));
        assertNotNull(rankService.getRank(id));
        assertNotNull(rankService.getRank(idTwo));
        assertEquals(sizeOfRanks+2, rankService.getAllRanks().size());
    }

    @Test
    public void testGetRank(){
        String userEmail = "foo@bar.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = 2;
        boolean createdUser = createUser(userEmail);
        long createdItem = createItem(itemTitle);
        long id = rankService.createRank(userEmail, createdItem, testComment, score);

        assertTrue(createdUser);
        assertNotNull(itemService.getItem(createdItem));
        assertNotNull(rankService.getRank(id));
    }

    @Test
    public void testUpdateRank(){
        String userEmail = "foo@bar.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = 2;
        int newScore = 4;
        boolean createdUser = createUser(userEmail);
        long createdItem = createItem(itemTitle);
        long id = rankService.createRank(userEmail, createdItem, testComment, score);

        assertTrue(createdUser);
        assertNotNull(itemService.getItem(createdItem));
        assertEquals(score, rankService.getRank(id).getScore());

        Rank rank = rankService.getRank(id);
        rank.setScore(newScore);
        rankService.updateRank(rank);
        assertNotEquals(score, rankService.getRank(id).getScore());
        assertEquals(newScore, rankService.getRank(id).getScore());
    }

    @Test
    public void testCreateRankWithWrongUser(){
        String errorEmail = "error@error.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = 2;
        long createdItem = createItem(itemTitle);

        assertNotNull(itemService.getItem(createdItem));
        assertThrows(IllegalArgumentException.class ,() -> rankService.createRank(errorEmail, createdItem, testComment, score));
    }

    @Test
    public void testCreateRankWithWrongItem(){
        String email = "foo@bar.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = 2;
        Long errorItemId = -1L;
        boolean createdUser = createUser(email);

        assertTrue(createdUser);
        assertThrows(IllegalArgumentException.class ,() -> rankService.createRank(email, errorItemId, testComment, score));
    }

    @Test
    public void testCreateRankWithErrorScore(){
        String email = "foo@bar.com";
        String itemTitle = "testTitle";
        String testComment = "testComment";
        int score = -1;
        boolean createdUser = createUser(email);
        long createdItem = createItem(itemTitle);

        assertTrue(createdUser);
        assertNotNull(itemService.getItem(createdItem));
        assertThrows(IllegalArgumentException.class ,() -> rankService.createRank(email, createdItem, testComment, score));
    }
}
