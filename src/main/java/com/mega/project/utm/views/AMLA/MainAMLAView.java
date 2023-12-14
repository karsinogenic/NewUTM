package com.mega.project.utm.views.AMLA;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mega.project.utm.services.JdbcQueryService;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.AmlaSummary;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("AMLA")
@Route(value = "amla", layout = MainLayout.class)
public class MainAMLAView extends VerticalLayout {

    private JdbcQueryService jdbcQueryService;
    private AmlaSummary amlaSummary;

    public MainAMLAView(JdbcQueryService jdbcQueryService) {
        this.jdbcQueryService = jdbcQueryService;
        this.amlaSummary = new AmlaSummary(this.jdbcQueryService);

        FormLayout todayDatas = new FormLayout();
        FormLayout allDatas = new FormLayout();
        FormLayout todayDatasApproved = new FormLayout();
        FormLayout allDatasApproved = new FormLayout();

        LocalDate ldt = LocalDate.now().minusDays(1);
        String query = "select count(*),a.triggered_rule from \"AMLA_RULE_RESULT\" a where \"post_date\" = \'"
                + ldt.toString() + "\' group by a.triggered_rule";
        String query1 = "select count(*),a.triggered_rule,a.is_approved,a.is_sent from \"AMLA_RULE_RESULT\" a where \"post_date\" = \'"
                + ldt.toString()
                + "\' group by a.triggered_rule,a.is_approved,a.is_sent order by a.triggered_rule,a.is_approved desc";

        String query2 = "select count(*),a.triggered_rule,a.is_approved,a.is_sent from \"AMLA_RULE_RESULT\" a group by a.triggered_rule,a.is_approved,a.is_sent order by a.triggered_rule,a.is_approved desc";

        String query3 = "select count(*),a.triggered_rule from \"AMLA_RULE_RESULT\" a group by a.triggered_rule";

        todayDatas = this.amlaSummary.allDatas(query);
        todayDatasApproved = this.amlaSummary.allDatas(query1);
        allDatasApproved = this.amlaSummary.allDatas(query2);
        allDatas = this.amlaSummary.allDatas(query3);

        todayDatas.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("200px", 4));
        todayDatasApproved.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("200px", 4));
        allDatasApproved.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("200px", 4));
        allDatas.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("200px", 4));

        H1 todayDatasApprovedH1 = new H1("Today Summary");
        H1 todayDatasH1 = new H1("Today Total");
        H1 allDatasApprovedH1 = new H1("All Summary");
        H1 allDatasH1 = new H1("All Total");
        Div todayDatasApprovedDiv = new Div(todayDatasApprovedH1, todayDatasApproved);
        todayDatasApprovedDiv.setWidth("90%");
        Div todayDatasDiv = new Div(todayDatasH1, todayDatas);
        todayDatasDiv.setWidth("90%");
        Div allDatasApproveDiv = new Div(allDatasApprovedH1, allDatasApproved);
        allDatasApproveDiv.setWidth("90%");
        Div allDatasDiv = new Div(allDatasH1, allDatas);
        allDatasDiv.setWidth("90%");

        add(todayDatasDiv, todayDatasApprovedDiv, allDatasDiv, allDatasApproveDiv);
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

}
