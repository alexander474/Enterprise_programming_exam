package no.ab.moc.frontend.controller;

import no.ab.moc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class FilterController implements Serializable {

    @Autowired
    ItemService itemService;

    private String dropDownSelect = "ALL";
    private List<String> categories = new ArrayList<>();

    public List<String> getAllCategories(){
        List<String> list = itemService.getAllCategories();
        list.add(0,"ALL");
        setCategories(list);
        return list;
    }

    public String getDropDownSelect() {
        return dropDownSelect;
    }

    public void setDropDownSelect(String dropDownSelect) {
        this.dropDownSelect = dropDownSelect;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
