package no.ab.moc.service;

import no.ab.moc.StubApplication;
import no.ab.moc.entity.Trip;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TripServiceTest extends ServiceTestBase {

    @Autowired
    private TripService tripService;

    @Test
    public void testCreateTrip(){
        String title = "testTitle";
        long id = tripService.createTrip(title, "description", 2000L, "location", new Date());
        assertEquals(1, tripService.getAllTrips().size());
        assertEquals(title, tripService.getTrip(id).getTitle());
    }

    @Test
    public void testListAllTrips(){

        List<Trip> list = tripService.getAllTrips();
        assertEquals(0, list.size());

        long id = tripService.createTrip("title", "description", 2000L,"location", new Date());
        List<Trip> list2 = tripService.getAllTrips();
        assertEquals(1, list2.size());
    }

    @Test
    public void testGetTrip(){
        String title = "testTitle";
        long id = tripService.createTrip(title, "description", 2000L,"location", new Date());
        Trip trip = tripService.getTrip(id);
        assertEquals(title, trip.getTitle());
    }


}
