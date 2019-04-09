package no.ab.moc.service;

import no.ab.moc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean createUser(String email, String name, String surname, String password) {

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        em.persist(user);

        return true;
    }


    public boolean createAdmin(String email, String name, String surname, String password) {

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("ADMIN"));
        user.setEnabled(true);

        em.persist(user);

        return true;
    }

    public User getUser(String email){
        return em.find(User.class, email);
    }

    public List<User> getAllUsers(){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public String getUserFullName(String email){
        User user = em.find(User.class, email);
        return user.getName() + " " + user.getSurname();
    }


    public void changeEnabled(String email){
        User user = em.find(User.class, email);
        user.setEnabled(!user.getEnabled());
    }




}
