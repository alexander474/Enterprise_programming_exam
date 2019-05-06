package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Rank;
import no.ab.moc.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RankingController {

    @Autowired
    RankService rankService;

    @Autowired
    UserInfoController userInfoController;

    @Autowired
    MainController mainController;

    private String comment = "";
    private int score = 0;

    public String createRanking(){
        if(comment.isEmpty() || score < 0 || score > 5){
            return "/item-detail.jsf&faces-redirect=true&error=true";
        }
        rankService.createRank(userInfoController.getUserEmail(), mainController.getItem().getId(), getComment(), getScore());
        return "/item-detail.jsf&faces-redirect=true";
    }

    public String updateRanking(Rank rank){
        if(comment.isEmpty() || score<0 || score>5){
            return "/item-detail.jsf&faces-redirect=true&error=true";
        }
        rankService.updateRank(rank);
        return "/item-detail.jsf&faces-redirect=true";
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
