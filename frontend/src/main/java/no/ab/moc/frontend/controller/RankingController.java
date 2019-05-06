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

    private String comment;
    private int score;

    public void createRanking(){
        rankService.createRank(userInfoController.getUserEmail(), mainController.getItem().getId(), getComment(), getScore());
    }

    public void updateRanking(Rank rank){
        rankService.updateRank(rank);
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
