package com.mega.project.utm.views.AMLA;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("AMLA Approval")
@Route(value = "amla/approval", layout = MainLayout.class)
public class ApprovalAMLAView extends VerticalLayout {

    private CustomRule customRule;
    private String Id;
    private String AmlaId;
    private String RuleCode;
    private AmlaRuleResultRepository amlaRuleResultRepository;
    private MyNotification myNotification;

    public ApprovalAMLAView(CustomRule customRule, AmlaRuleResultRepository amlaRuleResultRepository,
            MyNotification myNotification) {
        this.customRule = customRule;
        this.amlaRuleResultRepository = amlaRuleResultRepository;
        this.myNotification = myNotification;
        String current_user = VaadinSession.getCurrent().getAttribute("username").toString();

        Button detail = new Button("Detail");
        GridCrud<AmlaRuleResult> crud = new GridCrud<>(AmlaRuleResult.class);
        detail.addClickListener(event -> {
            String base64 = Base64.getEncoder().encodeToString((RuleCode + "_" + AmlaId + "_" + Id).getBytes());
            Notification newNotification = new Notification();
            AmlaRuleResult reOptional = this.amlaRuleResultRepository.findById(Id).get();
            // if (reOptional.getLockedBy() == null) {
            // reOptional.setLockedBy(current_user);
            // this.amlaRuleResultRepository.save(reOptional);
            // UI.getCurrent().navigate("amla/detail/" + base64);
            // } else {
            // if (reOptional.getLockedBy().equals(current_user)) {
            UI.getCurrent().navigate("amla/detail/" + base64);

            // } else {
            // newNotification = myNotification
            // .error("data sedang dikerjakan oleh user " + reOptional.getLockedBy());
            // newNotification.open();

            // }
            // }
        });

        List<AmlaRuleResult> ruleResults = this.amlaRuleResultRepository
                .findByIsSent(true);
        for (int i = 0; i < ruleResults.size(); i++) {
            ruleResults.get(i).setHiddenId((long) (i + 1));
        }
        crud.setFindAllOperation(() -> ruleResults);

        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(detail);
        detail.setEnabled(false);

        crud.getGrid().addCellFocusListener(event -> {
            try {
                Id = event.getItem().get().getId();
                AmlaId = event.getItem().get().getAmlaId();
                RuleCode = event.getItem().get().getTriggeredRule();

                // System.out.println("new Id: " + Id);
                detail.setEnabled(true);
            } catch (Exception e) {
                Id = null;
                RuleCode = null;
                AmlaId = null;
                detail.setEnabled(false);
            }
            // System.out.println(event.getItem().get().getMerchant_id());
        });

        // crud.getGrid().addC

        crud.getGrid().removeAllColumns();
        // crud.getGrid().("Me\rchant Id", "Card Number", "Total Transactions",
        // "Date", "Rule", "Memo");
        // crud.getGrid().addColumn(hasil ->
        // hasil.getId()).setHeader("Id").setKey("id");
        // crud.getGrid().addColumn(hasil ->
        // hasil.getId()).setHeader("Id").setKey("id1");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        crud.getGrid().addColumn(hasil -> hasil.getHiddenId()).setHeader("Nomer").setKey("nomer1");
        // crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant
        // Id").setKey("mid1");
        // crud.getGrid().addColumn(hasil -> hasil.getCardNum()).setHeader("Card
        // Number").setKey("cardnum1");
        // crud.getGrid().addColumn(hasil -> hasil.getCount()).setHeader("Total
        // Transactions").setKey("totalTrx1");
        // crud.getGrid().addColumn(hasil ->
        // numberFormat.format(hasil.getSum()).toString())
        // .setHeader("Total Transactions Amount")
        // .setKey("totalTrxAmount");
        crud.getGrid().addColumn(hasil -> hasil.getPostDate()).setHeader("Date").setKey("date1");
        crud.getGrid().addColumn(hasil -> hasil.getTriggeredRule()).setHeader("Rule").setKey("rule1");
        crud.getGrid().addComponentColumn(new ValueProvider<AmlaRuleResult, Component>() {

            @Override
            public Component apply(AmlaRuleResult source) {
                Span pendingx = new Span(createIcon(VaadinIcon.CLOCK),
                        new Span(source.getIsApproved() == null ? source.getIsSent() ? "Pending" : "No Memo"
                                : source.getIsApproved() ? "Approved" : "Denied"));
                pendingx.getElement().getThemeList().add(source.getIsApproved() == null ? "badge"
                        : source.getIsApproved() ? "badge success" : "badge error");
                return pendingx;
            }

        }).setHeader("Approval Status");
        // crud.getGrid().addColumn(hasil ->
        // hasil.getMemo()).setHeader("Memo").setKey("memo1");
        // crud.getGrid().addColumn(hasil -> hasil.getReviewBy()).setHeader("Review
        // By").setKey("review1");

        // crud.getGrid().removeColumnByKey("MId");

        // crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant
        // Id").setKey("key");
        // crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant
        // Id").setKey("key");
        // crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant
        // Id").setKey("key");
        // crud.getGrid().setColumns("MId", "cardNum", "count", "date", "rule", "memo");
        // crud.getGrid().set

        add(crud);
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

}
