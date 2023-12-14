package com.mega.project.utm.views.QR002;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mega.project.utm.services.JdbcQueryService;
import com.mega.project.utm.views.MainLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("QR")
@Route(value = "qr", layout = MainLayout.class)
public class QRMainView extends VerticalLayout {

    private JdbcQueryService jdbcQueryService;

    public QRMainView(JdbcQueryService jdbcQueryService) {
        this.jdbcQueryService = jdbcQueryService;

        FormLayout todayDatas = new FormLayout();
        FormLayout todayDatas1 = new FormLayout();

        LocalDate ldt = LocalDate.now().minusDays(1);
        String query = "select count(*),a.rule from \"RULE_RESULT\" a where \"date\" = \'"
                + ldt.toString() + "\' group by a.rule";
        System.out.println(query);
        List<Map<String, Object>> listHasil = this.jdbcQueryService.executeCustomQuery(query);
        HorizontalLayout hz = new HorizontalLayout();

        for (int i = 0; i < 6; i++) {
            for (Map<String, Object> map : listHasil) {
                JSONObject jsonObject = new JSONObject(map);
                TextField isi = new TextField("TRM00" + (i + 1));
                TextField isi2 = new TextField("TRM00" + (i + 7));
                isi.setValue(jsonObject.get("count").toString());
                isi2.setValue(jsonObject.get("count").toString());
                todayDatas.add(isi);
                todayDatas1.add(isi2);
                // System.out.println(jsonObject);
            }
        }

        todayDatas.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("200px", 1));
        todayDatas.setWidth("40%");
        todayDatas1.setWidth("40%");

        hz.add(todayDatas, todayDatas1);
        hz.setWidth("90%");
        hz.setJustifyContentMode(JustifyContentMode.CENTER);
        hz.setMargin(true);

        setAlignItems(FlexComponent.Alignment.CENTER);
        add(hz);
    }

}
