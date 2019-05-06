package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Item;
import no.ab.moc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FilterController filterController;

    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    public List<Item> getAllItemsSortedByScore(){
        if(!filterController.getDropDownSelect().equals("ALL")) {
            return itemService.getAllItemsSortedByScore(false,filterController.getDropDownSelect());
        }
        return itemService.getAllItemsSortedByScore(true,null);
    }



}

