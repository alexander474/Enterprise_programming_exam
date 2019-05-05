package no.ab.moc.service;


import no.ab.moc.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase {

    @Autowired
    UserService userService;

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
}
