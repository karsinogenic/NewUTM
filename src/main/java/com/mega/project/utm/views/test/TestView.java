package com.mega.project.utm.views.test;

import org.vaadin.crudui.crud.impl.GridCrud;

// import com.github.appreciated.card.Card;
// import com.github.appreciated.card.content.IconItem;
// import com.github.appreciated.card.content.Item;
// import com.github.appreciated.card.label.PrimaryLabel;
// import com.github.appreciated.card.label.SecondaryLabel;
// import com.github.appreciated.card.label.TitleLabel;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("TEST")
@Route(value = "test", layout = MainLayout.class)
public class TestView extends VerticalLayout {

    public TestView() {
        String html = """
                  <div class="card" style="width: 18rem;">
                  <div class="card-body">
                    <h5 class="card-title">cardtitle</h5>
                    <p class="card-text">cardtxt</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                  </div>
                </div>
                      """;

        H5 coba = new H5("COBA DULU YAK");
        coba.setClassName("card-title");
        Paragraph p = new Paragraph("Aayayyayayya");
        p.setClassName("card-text");
        Anchor anchor = new Anchor("/", "balik");
        anchor.setClassName("btn btn-primary");
        Component[] header_com = { coba };
        Component[] body_com = { p, anchor };
        Card new_Card = new Card(header_com, body_com);

        add(new_Card);

    }

}
