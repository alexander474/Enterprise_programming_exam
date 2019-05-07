package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Item;
import no.ab.moc.entity.Rank;
import no.ab.moc.service.ItemService;
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

    @Autowired
    ItemService itemService;

    private String comment = "";
    private int score = 1;

    public String createRanking(){
        if(comment.isEmpty() || score <= 0 || score > 5){
            return mainController.toItemDetailPageError();
        }
        long id = rankService.createRank(userInfoController.getUserEmail(), mainController.getItem().getId(), getComment(), getScore());
        rankService.getRank(id).getItem().getRanks().add(rankService.getRank(id));
        return mainController.toItemDetailPageSuccess(rankService.getRank(id).getItem());
    }

    public String updateRanking(){
        if(comment.isEmpty() || score <= 0 || score > 5 || !mainController.userHasRankedItem()){
            return mainController.toItemDetailPageError();
        }
        Rank rank = mainController.getUserRank();
        rank.setComment(comment);
        rank.setScore(score);
        rankService.updateRank(rank);
        return mainController.toItemDetailPageSuccess(rankService.getRank(rank.getId()).getItem());
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
