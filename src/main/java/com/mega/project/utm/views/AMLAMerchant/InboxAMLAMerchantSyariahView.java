package com.mega.project.utm.views.AMLAMerchant;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Models.AMLA.CombinedAmlaMerchant;
import com.mega.project.utm.Repositories.AmlaMerchantRuleResultRepository;
import com.mega.project.utm.Repositories.AmlaMerchantRuleResultRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
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
@PageTitle("AMLA Inbox")
@Route(value = "amla-merchant-syariah/inbox", layout = MainLayout.class)
public class InboxAMLAMerchantSyariahView extends VerticalLayout {

    private CustomRule customRule;
    private String Id;
    private String AmlaId;
    private String RuleCode;
    private AmlaMerchantRuleResultRepository amlaMerchantRuleResultRepository;
    private MyNotification myNotification;

    public InboxAMLAMerchantSyariahView(CustomRule customRule,
            AmlaMerchantRuleResultRepository amlaMerchantRuleResultRepository,
            MyNotification myNotification) {
        this.customRule = customRule;
        this.amlaMerchantRuleResultRepository = amlaMerchantRuleResultRepository;
        this.myNotification = myNotification;
        String current_user = VaadinSession.getCurrent().getAttribute("username").toString();

        Button detail = new Button("Detail");
        GridCrud<AmlaMerchantRuleResult> crud = new GridCrud<>(AmlaMerchantRuleResult.class);
        detail.addClickListener(event -> {
            String base64 = Base64.getEncoder().encodeToString((RuleCode + "_" + AmlaId + "_" + Id).getBytes());
            Notification newNotification = new Notification();
            AmlaMerchantRuleResult reOptional = this.amlaMerchantRuleResultRepository.findById(Id).get();
            if (reOptional.getLockedBy() == null) {
                reOptional.setLockedBy(current_user);
                this.amlaMerchantRuleResultRepository.save(reOptional);
                UI.getCurrent().navigate("amla-merchant/detail/" + base64);
            } else {
                if (reOptional.getLockedBy().equals(current_user)) {
                    UI.getCurrent().navigate("amla-merchant/detail/" + base64);

                } else {
                    newNotification = myNotification
                            .error("data sedang dikerjakan oleh user " + reOptional.getLockedBy());
                    newNotification.open();

                }
            }
        });

        List<AmlaMerchantRuleResult> ruleResults = this.amlaMerchantRuleResultRepository
                .findByIsSentAndIsApprovedIsNull(false);
        // List<CombinedAmlaMerchant> combinedAmlaMerchants =
        for (int i = 0; i < ruleResults.size(); i++) {
            ruleResults.get(i).setHiddenId((long) (i + 1));
            // System.out.println(ruleResults.get(i).getMerchLocalName());
        }
        crud.setFindAllOperation(() -> ruleResults);

        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.getCrudLayout().addToolbarComponent(detail);
        detail.setEnabled(false);

        crud.getGrid().addItemClickListener(event -> {
            try {
                if (Id != event.getItem().getId()) {
                    detail.setEnabled(Id != event.getItem().getId());
                    Id = event.getItem().getId();
                    AmlaId = event.getItem().getAmlaId();
                    RuleCode = event.getItem().getTriggeredRule();
                } else {
                    detail.setEnabled(Id != event.getItem().getId());
                    Id = null;
                    RuleCode = null;
                    AmlaId = null;

                }

                // System.out.println("new Id: " + Id);
            } catch (Exception e) {
                Id = null;
                RuleCode = null;
                AmlaId = null;
                detail.setEnabled(false);
            }
            // System.out.println(event.getItem().getMerchant_id());
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

        crud.getGrid().addColumn(hasil -> hasil.getHiddenId()).setHeader("Nomer").setKey("nomer1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getPostDate()).setHeader("Date").setKey("date1").setSortable(true)
                .setAutoWidth(true);
        crud.getGrid().addColumn(hasil -> hasil.getTriggeredRule()).setHeader("Rule").setKey("rule1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getMerchNumber()).setHeader("MID").setKey("mid1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getMerchLocalName()).setHeader("Merchant Name").setKey("merchname1")
                .setSortable(true).setAutoWidth(true);
        crud.getGrid().addColumn(hasil -> hasil.getOwnerMemo()).setHeader("Owner Flag").setKey("ownermemo1")
                .setSortable(true).setAutoWidth(true);
        crud.getGrid().addColumn(hasil -> NumberFormat.getNumberInstance(Locale.US).format(hasil.getTotalTrx()))
                .setHeader("Total Trx").setKey("tottrx1")
                .setSortable(true).setAutoWidth(true);
        crud.getGrid().addColumn(hasil -> hasil.getFrequency()).setHeader("Freq").setKey("freq1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getMcc()).setHeader("MCC").setKey("mcc1").setSortable(true);
        // crud.getGrid().addColumn(hasil ->
        // hasil.getFrequency()).setHeader("Freq").setKey("tottrx1").setSortable(true);

        crud.getGrid().addComponentColumn(new ValueProvider<AmlaMerchantRuleResult, Component>() {

            @Override
            public Component apply(AmlaMerchantRuleResult source) {
                Span pendingx = new Span(createIcon(VaadinIcon.CLOCK),
                        new Span(source.getIsApproved() == null ? source.getIsSent() ? "Pending" : "No Memo"
                                : source.getIsApproved() ? "Approved" : "Denied"));
                pendingx.getElement().getThemeList().add(source.getIsApproved() == null ? "badge"
                        : source.getIsApproved() ? "badge success" : "badge error");
                return pendingx;
            }

        }).setHeader("Approval Status").setSortable(true).setAutoWidth(true);
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
        addAndExpand(crud);
        setSizeFull();
        // crud.getCrudLayout().

    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }
}
