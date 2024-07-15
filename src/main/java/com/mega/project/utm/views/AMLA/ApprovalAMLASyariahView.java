package com.mega.project.utm.views.AMLA;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

// import org.vaadin.crudui.crud.impl.Grid;

import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({ "ROLE_APPROVER", "ROLE_ADMIN" })
@PageTitle("AMLA Approval")
@Route(value = "amla-syariah/approval", layout = MainLayout.class)
public class ApprovalAMLASyariahView extends VerticalLayout {

        private CustomRule customRule;
        private String Id;
        private String AmlaId;
        private String RuleCode;
        private AmlaRuleResultRepository amlaRuleResultRepository;
        private MyNotification myNotification;

        public ApprovalAMLASyariahView(CustomRule customRule, AmlaRuleResultRepository amlaRuleResultRepository,
                        MyNotification myNotification) {
                this.customRule = customRule;
                this.amlaRuleResultRepository = amlaRuleResultRepository;
                this.myNotification = myNotification;
                String current_user = VaadinSession.getCurrent().getAttribute("username").toString();

                Button detail = new Button("Detail");
                Grid<AmlaRuleResult> crud = new Grid<>(AmlaRuleResult.class);
                detail.addClickListener(event -> {
                        String base64 = Base64.getEncoder()
                                        .encodeToString((RuleCode + "_" + AmlaId + "_" + Id).getBytes());
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
                                .findByIsSentSyariah(true);
                for (int i = 0; i < ruleResults.size(); i++) {
                        ruleResults.get(i).setHiddenId((long) (i + 1));
                }
                crud.setItems(ruleResults);

                // crud.setAddOperationVisible(false);
                // crud.setDeleteOperationVisible(false);
                // crud.setUpdateOperationVisible(false);
                // crud.getCrudLayout().addToolbarComponent(detail);
                detail.setEnabled(false);

                crud.addItemClickListener(event -> {
                        try {
                                detail.setEnabled(!detail.isEnabled());
                                if (detail.isEnabled()) {
                                        Id = event.getItem().getId();
                                        AmlaId = event.getItem().getAmlaId();
                                        RuleCode = event.getItem().getTriggeredRule();
                                } else {
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

                // crud.addC

                crud.removeAllColumns();
                // crud.("Me\rchant Id", "Card Number", "Total Transactions",
                // "Date", "Rule", "Memo");
                // crud.addColumn(hasil ->
                // hasil.getId()).setHeader("Id").setKey("id");
                // crud.addColumn(hasil ->
                // hasil.getId()).setHeader("Id").setKey("id1");
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

                crud.addColumn(hasil -> hasil.getHiddenId()).setHeader("Nomer").setKey("nomer1")
                                .setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getAccNumber()).setHeader("Acc Number").setKey("accnum")
                                .setSortable(true).setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getChName()).setHeader("CH Name").setKey("chname")
                                .setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getCompanyName()).setHeader("Comp Name").setKey("compname")
                                .setSortable(true).setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getApuppt()).setHeader("APUPPT").setKey("apuppt")
                                .setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getBlockAcc()).setHeader("Acc Block").setKey("accblock")
                                .setSortable(true).setAutoWidth(true);

                crud.addColumn(hasil -> hasil.getPostDate()).setHeader("Date").setKey("date1")
                                .setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getTriggeredRule()).setHeader("Rule").setKey("rule1")
                                .setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getMemo()).setHeader("Memo").setKey("memo1").setSortable(true)
                                .setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getBankCode() == 426 ? "Mega" : "Mega Syariah")
                                .setHeader("Bank")
                                .setKey("bank1")
                                .setSortable(true).setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getApprovedBy()).setHeader("Approved By").setKey("approve1")
                                .setSortable(true).setAutoWidth(true);
                crud.addColumn(hasil -> hasil.getReviewBy()).setHeader("Review By").setKey("review1")
                                .setSortable(true).setAutoWidth(true);
                // crud
                // .addColumn(hasil -> hasil.getIsApproved() == null ? "Pending"
                // : hasil.getIsApproved() == true ? "Approved" : "Denied")
                // .setHeader("Approval Status").setKey("status1");
                // crud.addColumn(hasil ->
                // hasil.getMemo()).setHeader("Memo").setKey("memo1");
                // crud.addColumn(hasil -> hasil.getReviewBy()).setHeader("Review
                // By").setKey("review1");

                // crud
                // .addColumn(hasil -> hasil.getIsApproved() == null ? pending1
                // : hasil.getIsApproved() == true ? confirmed1 : denied1)
                // .setHeader("Approval Status").setKey("status1");
                crud.addColumn(hasil -> hasil.getStatus()).setHeader("Status").setSortable(true)
                                .setAutoWidth(true);
                crud.addComponentColumn(new ValueProvider<AmlaRuleResult, Component>() {

                        @Override
                        public Component apply(AmlaRuleResult source) {
                                Span pendingx = new Span(createIcon(VaadinIcon.CLOCK),
                                                new Span(source.getIsApproved() == null
                                                                ? source.getIsSent() ? "Pending" : "No Memo"
                                                                : source.getIsApproved() ? "Approved" : "Denied"));
                                pendingx.getElement().getThemeList().add(source.getIsApproved() == null ? "badge"
                                                : source.getIsApproved() ? "badge success" : "badge error");
                                return pendingx;
                        }

                }).setHeader("Approval Status").setSortable(true).setAutoWidth(true);
                // crud.addColumn(hasil ->
                // hasil.getMemo()).setHeader("Memo").setKey("memo1");
                // crud.addColumn(hasil -> hasil.getReviewBy()).setHeader("Review
                // By").setKey("review1");

                // crud.removeColumnByKey("MId");

                // crud.addColumn(hasil -> hasil.getMId()).setHeader("Merchant
                // Id").setKey("key");
                // crud.addColumn(hasil -> hasil.getMId()).setHeader("Merchant
                // Id").setKey("key");
                // crud.addColumn(hasil -> hasil.getMId()).setHeader("Merchant
                // Id").setKey("key");
                // crud.setColumns("MId", "cardNum", "count", "date", "rule", "memo");
                // crud.set

                add(new VerticalLayout(detail, crud));
        }

        private Icon createIcon(VaadinIcon vaadinIcon) {
                Icon icon = vaadinIcon.create();
                icon.getStyle().set("padding", "var(--lumo-space-xs");
                return icon;
        }

}
