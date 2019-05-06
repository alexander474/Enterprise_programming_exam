package no.ab.moc.frontend.controller;

import no.ab.moc.entity.Item;
import no.ab.moc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ItemController {

    @Autowired
    private ItemService itemService;

    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    public List<Item> getAllitemsSortedByScore(String category){
        return itemService.getAllItemsSortedByScore(category);
    }

}

