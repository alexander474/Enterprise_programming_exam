package no.ab.moc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 1, max = 4096)
    private String text;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Item item;
}
