package no.ab.moc.frontend.controller;


import no.ab.moc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

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

}
