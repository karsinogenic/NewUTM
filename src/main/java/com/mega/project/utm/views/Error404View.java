package com.mega.project.utm.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(layout = MainLayout.class)
public class Error404View extends VerticalLayout {

    private String error;

    public Error404View() {
        H1 title = new H1("404 NOT FOUND");
        title.setClassName(LumoUtility.TextColor.ERROR);
        setAlignItems(Alignment.CENTER);
        add(title);
    }

}
