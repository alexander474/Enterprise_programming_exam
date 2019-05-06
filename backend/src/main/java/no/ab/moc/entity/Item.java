package no.ab.moc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1, max = 4096)
    private String title;

    @NotBlank
    @Size(min = 1, max = 4096)
    private String description;

    @NotBlank
    @Size(min = 1, max = 4096)
    private String category;

    @ManyToOne
    private List<Comment> comments = new ArrayList<>();

}
