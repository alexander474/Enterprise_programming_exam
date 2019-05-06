package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Item;
import no.ab.moc.entity.Rank;
import no.ab.moc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.OptionalDouble;

@Named
@ApplicationScoped
public class MainController implements Serializable {

    @Autowired
    ItemService itemService;

    @Autowired
    UserInfoController userInfoController;


    private Item item;
    private Rank userRank;

    public String toItemDetailPage(Item item){
        setItem(item);
        return "/item-detail.jsf&faces-redirect=true";
    }

    public Boolean userHasRankedItem(){
        if(itemService.userHasRankedItem(userInfoController.getUserEmail(), item.getId())) {
            setUserRank(itemService.getItemUserRank(userInfoController.getUserEmail(), item.getId()));
            return true;
        }
        return false;
    }

    public Rank getUserRank(){
        return this.userRank;
    }

    public Double getRankAverage(){
        return itemService.getItemRankAverage(item.getId());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setUserRank(Rank userRank) {
        this.userRank = userRank;
    }
}
