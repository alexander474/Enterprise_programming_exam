package no.ab.moc.service;


import no.ab.moc.StubApplication;
import no.ab.moc.entity.Rank;
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
public class UserServiceTest extends ServiceTestBase {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    RankService rankService;

    public Long createItem(String title){
        return itemService.createItem(title, "testDesc", "action");
    }

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    public boolean createAdmin(String email){
        return userService.createUser(email, "foo", "bar", "1234", true);
    }


    @Test
    public void testCreateUser(){
        int sizeOfUsers = userService.getAllUsers().size();
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);
        assertEquals(email, userService.getUser(email).getEmail());
        assertEquals(sizeOfUsers+1, userService.getAllUsers().size());
    }

    @Test
    public void testCreateAdmin(){
        int sizeOfUsers = userService.getAllUsers().size();
        String email = "foo@bar.com";
        boolean created = createAdmin(email);

        assertTrue(created);
        assertEquals(email, userService.getUser(email).getEmail());
        assertEquals(sizeOfUsers+1, userService.getAllUsers().size());
    }

    @Test
    public void testChangeEnabled(){
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);
        boolean enabled = userService.getUser(email).getEnabled();
        userService.changeEnabled(email);
        assertEquals(enabled, !userService.getUser(email).getEnabled());
    }

    @Test
    public void testGetUserFullName(){
        String name = "foo";
        String surname = "bar";
        String email = "foo@bar.com";
        boolean created = userService.createUser(email, name, surname, "1234", false);

        assertTrue(created);

        String recievedFullName = userService.getUserFullName(email);

        assertTrue(recievedFullName.contains(name));
        assertTrue(recievedFullName.contains(surname));
    }

    @Test
    public void testCreateTwoUsersWithSameEmail(){
        String email = "foo@bar.com";
        boolean created = createUser(email);

        assertTrue(created);

        boolean createdWithSameEmail = createUser(email);

        assertFalse(createdWithSameEmail);
    }

    @Test
    public void testCreateTwoAdminWithSameEmail(){
        String email = "foo@bar.com";
        boolean created = createAdmin(email);

        assertTrue(created);

        boolean createdWithSameEmail = createAdmin(email);

        assertFalse(createdWithSameEmail);
    }

    @Test
    public void testGetUserRanks(){
        String email = "foo@bar.com";
        String itemTitle = "randomTestTitle";
        String itemTitle2 = "randomTestTitle2";
        String itemTitle3 = "randomTestTitle3";
        String rankComment = "randomTestComment";
        String rankComment2 = "randomTestComment2";
        String rankComment3 = "randomTestComment3";
        int rankScore = 2;

        boolean created = createUser(email);
        assertTrue(created);

        List<Rank> userRanksBfore = userService.getUserRanks(email);

        long itemId = createItem(itemTitle);
        long itemId2 = createItem(itemTitle2);
        long itemId3 = createItem(itemTitle3);
        long rankId = rankService.createRank(email, itemId, rankComment, rankScore);
        long rankId2 = rankService.createRank(email, itemId2, rankComment2, rankScore);
        long rankId3 = rankService.createRank(email, itemId3, rankComment3, rankScore);

        List<Rank> userRanksAfter = userService.getUserRanks(email);

        assertTrue(userRanksAfter.size() > userRanksBfore.size());
        assertEquals(userRanksBfore.size()+3, userRanksAfter.size());

    }
}
