package no.ab.moc.frontend.controller.admin;

import no.ab.moc.entity.Item;
import no.ab.moc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AdminController {

    @Autowired
    private ItemService itemService;


    private String title, description, category;


    private boolean itemExsists(String title){
        boolean exist = false;
        for(Item i : itemService.getAllItems()){
            if(i.getTitle().equalsIgnoreCase(title)) exist = true;
        }
        return exist;
    }


    public String createItem(){
        if(title.isEmpty()||description.isEmpty()||category.isEmpty()||itemExsists(title)){
            return "/admin/create-item.jsf?faces-redirect=true&error=true";
        }

        itemService.createItem(title, description, category.toUpperCase());

        return "/admin/create-item.jsf?faces-redirect=true&success=true";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
