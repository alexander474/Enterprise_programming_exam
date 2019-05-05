package no.ab.moc.service;

import no.ab.moc.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PurchaseServiceTest extends ServiceTestBase{

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TripService tripService;

    public boolean createUser(String email){
        return userService.createUser(email, "foo", "bar", "1234", false);
    }

    public Long createTrip(String tripTitle){
        return tripService.createTrip(tripTitle, "description", 2000, "Oslo", new Date());
    }


    @Test
    public void testCreatePurchase(){
        String email = "foo@bar.com";
        String tripTitle = "testTitle";

        boolean createdUser = createUser(email);
        long tripId = createTrip(tripTitle);
        long purchaseId = purchaseService.createPurchase(email, tripId);

        assertTrue(createdUser);
        assertNotNull(tripId);
        assertNotNull(purchaseId);
    }

    @Test
    public void testGetAllPurchases(){
        int purchaseInitSize = purchaseService.getAllPurchases().size();
        String mailOne = "m1@m.com";
        String mailTwo = "m2@m.com";
        String tripTitleOne = "t1";
        String tripTitleTwo = "t2";

        boolean userOne = createUser(mailOne);
        boolean userTwo = createUser(mailTwo);
        long tripOne = createTrip(tripTitleOne);
        long tripTwo = createTrip(tripTitleTwo);

        long purchaseOne = purchaseService.createPurchase(mailOne, tripOne);
        long purchaseTwo = purchaseService.createPurchase(mailTwo, tripTwo);

        assertTrue(userOne);
        assertTrue(userTwo);
        assertEquals(purchaseInitSize+2, purchaseService.getAllPurchases().size());
        assertEquals(mailOne, purchaseService.getPurchase(purchaseOne).getUser().getEmail());
        assertEquals(mailTwo, purchaseService.getPurchase(purchaseTwo).getUser().getEmail());
        assertEquals(tripTitleOne, purchaseService.getPurchase(purchaseOne).getTrip().getTitle());
        assertEquals(tripTitleTwo, purchaseService.getPurchase(purchaseTwo).getTrip().getTitle());
    }

    @Test
    public void testGetPurchase(){
        String email = "foo@bar.com";
        String tripTitle = "testTitle";

        boolean created = createUser(email);
        long tripId = createTrip(tripTitle);
        long purchaseId = purchaseService.createPurchase(email, tripId);

        assertTrue(created);
        assertEquals(email, purchaseService.getPurchase(purchaseId).getUser().getEmail());
        assertEquals(tripTitle, purchaseService.getPurchase(purchaseId).getTrip().getTitle());
    }

    @Test
    public void testCreatePurchaseWithWrongUser(){
        String email = "error@email.com";
        String tripTitle = "testTitle";

        long tripId = createTrip(tripTitle);
        assertThrows(IllegalArgumentException.class ,() -> purchaseService.createPurchase(email, tripId));

    }

    @Test
    public void testCreatePurchaseWithWrongTrip(){
        String email = "foo@bar.com";
        long errorTripId = 201238921;

        createUser(email);
        assertThrows(IllegalArgumentException.class ,() -> purchaseService.createPurchase(email, errorTripId));

    }
}
