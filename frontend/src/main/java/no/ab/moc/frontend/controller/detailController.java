package no.ab.moc.frontend.controller;

import org.hibernate.mapping.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.OptionalDouble;

@Named
@RequestScoped
public class detailController {

    @Autowired
    private MainController mainController;

}
