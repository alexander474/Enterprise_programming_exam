package no.ab.moc.service;

import no.ab.moc.StubApplication;
import no.ab.moc.entity.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;

    @Test
    public void testCreateItem(){
        int sizeOfItems = itemService.getAllItems().size();
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        assertEquals(sizeOfItems+1, itemService.getAllItems().size());
        assertEquals(title, itemService.getItem(id).getTitle());
    }

    @Test
    public void testListAllItems(){
        int sizeOfItems = itemService.getAllItems().size();
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        List<Item> list = itemService.getAllItems();
        assertEquals(sizeOfItems+1, list.size());
    }

    @Test void testGetItem(){
        String title = "testTitle";
        long id = itemService.createItem(title, "testDesc", "action");
        Item item = itemService.getItem(id);
        assertEquals(title, item.getTitle());
    }
}
