package com.mega.project.utm.views.dashboard;

import com.mega.project.utm.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@PermitAll
@PageTitle("Dashboard")
// @RolesAllowed("ROLE_ADMIN")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        setSpacing(false);

        Image img = new Image("images/megaBG.jpg", "placeholder plant");
        img.setWidth("1366px");
        add(img);

        // Button btn = new Button("ini button");
        // add(btn);

        // TextArea tx = new TextArea();

        // HorizontalLayout hz = new HorizontalLayout();
        // hz.add(tx, btn);

        // add(hz);

        // H2 header = new H2("This place intentionally left empty");
        // header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        // add(header);
        // add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
