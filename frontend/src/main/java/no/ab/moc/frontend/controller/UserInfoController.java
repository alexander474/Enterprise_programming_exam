package no.ab.moc.frontend.controller;

import no.ab.moc.entity.MatchStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import no.ab.moc.service.MatchStatsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private MatchStatsService matchStatsService;

    public String getUserName() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public MatchStats getStats() {
        return matchStatsService.getMatchStats(getUserName());
    }
}
