package no.ab.moc.frontend.controller;


import no.ab.moc.entity.Rank;
import no.ab.moc.entity.User;
import no.ab.moc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private UserService userService;

    public String getUserEmail(){
        String email = "";
        try{
            email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        } catch (ClassCastException e){
            // There is nothing to do if this happens
        }
        return email;
    }

    public String getCurrentUserFullName() {
        return userService.getUserFullName(getUserEmail());
    }

    public String getFullNameByEmail(String email) {
        return userService.getUserFullName(email);
    }

    public User getUser(){
        return userService.getUser(getUserEmail());
    }

    public List<Rank> getUserRanks(){
        return userService.getUserRanks(getUserEmail());
    }

}
