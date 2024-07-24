package com.mega.project.utm.views.Components;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mega.project.utm.services.JdbcQueryService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AmlaSummary {

    private JdbcQueryService jdbcQueryService;

    public AmlaSummary(JdbcQueryService jdbcQueryService) {
        this.jdbcQueryService = jdbcQueryService;
    }

    public FormLayout allDatas(String query) throws JSONException {
        FormLayout allDatas = new FormLayout();
        List<Map<String, Object>> listHasil3 = this.jdbcQueryService.executeCustomQuery(query);

        for (Map<String, Object> map : listHasil3) {
            JSONObject jsonObject = new JSONObject(map);
            // System.out.println(jsonObject);
            Boolean isApproved = null;
            try {
                isApproved = jsonObject.getBoolean("is_approved");

            } catch (Exception e) {
                // TODO: handle exception
            }
            Boolean isSent = false;
            try {
                isSent = jsonObject.getBoolean("is_sent");
            } catch (Exception e) {
                // TODO: handle exception
            }
            Span pendingx = new Span(createIcon(VaadinIcon.CLOCK),
                    new Span(isApproved == null
                            ? isSent ? "Pending" : "No Memo"
                            : isApproved ? "Approved" : "Denied"));
            pendingx.getElement().getThemeList().add(isApproved == null ? "badge"
                    : isApproved ? "badge success" : "badge error");

            TextField isi = new TextField(jsonObject.getString("triggered_rule"));
            isi.setHelperComponent(pendingx);
            // Div statusDiv = new Div(isi);
            isi.setValue(jsonObject.get("count").toString());
            isi.addClassNames(LumoUtility.Border.ALL, LumoUtility.Margin.Bottom.SMALL,
                    LumoUtility.BorderColor.PRIMARY);
            allDatas.add(isi);
        }
        return allDatas;
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

}
