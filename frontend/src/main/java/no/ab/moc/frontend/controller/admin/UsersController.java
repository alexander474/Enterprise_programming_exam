package no.ab.moc.frontend.controller.admin;

import no.ab.moc.entity.User;
import no.ab.moc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UsersController {

    @Autowired
    private UserService userService;

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public void changeEnabled(String email) {
        userService.changeEnabled(email);
    }

}
