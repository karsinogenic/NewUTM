package com.mega.project.utm.views.QR002;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.vaadin.crudui.crud.CrudOperation;
// import org.hibernate.mapping.List;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.QR002;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.services.RoleService;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("QR Inbox")
@Route(value = "qr/inbox", layout = MainLayout.class)
public class QR002View extends VerticalLayout {

    private CustomRule customRule;
    private String Id;
    private RuleResultRepository ruleResultRepository;
    private MyNotification myNotification;

    public QR002View(CustomRule customRule, RuleResultRepository ruleResultRepository, MyNotification myNotification) {
        this.customRule = customRule;
        this.ruleResultRepository = ruleResultRepository;
        this.myNotification = myNotification;
        String current_user = VaadinSession.getCurrent().getAttribute("username").toString();

        Button detail = new Button("Detail");
        detail.addClickListener(event -> {
            Notification newNotification = new Notification();
            RuleResult reOptional = this.ruleResultRepository.findById(Id).get();
            if (reOptional.getLockedBy() == null) {
                reOptional.setLockedBy(current_user);
                this.ruleResultRepository.save(reOptional);
                UI.getCurrent().navigate("/detail/" + Id);
            } else {
                if (reOptional.getLockedBy().equals(current_user)) {
                    UI.getCurrent().navigate("/detail/" + Id);

                } else {
                    newNotification = myNotification
                            .error("data sedang dikerjakan oleh user " + reOptional.getLockedBy());
                    newNotification.open();

                }
            }
        });

        GridCrud<RuleResult> crud = new GridCrud<>(RuleResult.class);
        // List<RuleResult> ruleResults =
        // this.customRule.allRule(LocalDate.now().minusDays(1));
        // List<RuleResult> ruleResults = this.customRule.allRule(LocalDate.of(2023, 9,
        // 16));
        List<RuleResult> ruleResults = this.ruleResultRepository.findAllByMemoIsNull();
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
                // System.out.println("new Id: " + Id);
                detail.setEnabled(true);
            } catch (Exception e) {
                Id = null;
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
        crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant Id").setKey("mid1");
        crud.getGrid().addColumn(hasil -> hasil.getCardNum()).setHeader("Card Number").setKey("cardnum1");
        crud.getGrid().addColumn(hasil -> hasil.getCount()).setHeader("Total Transactions").setKey("totalTrx1");
        crud.getGrid().addColumn(hasil -> numberFormat.format(hasil.getSum()).toString())
                .setHeader("Total Transactions Amount")
                .setKey("totalTrxAmount");
        crud.getGrid().addColumn(hasil -> hasil.getDate()).setHeader("Date").setKey("date1");
        crud.getGrid().addColumn(hasil -> hasil.getRule()).setHeader("Rule").setKey("rule1");
        crud.getGrid().addColumn(hasil -> hasil.getMemo()).setHeader("Memo").setKey("memo1");
        crud.getGrid().addColumn(hasil -> hasil.getReviewBy()).setHeader("Review By").setKey("review1");

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
        // List<String> coba = new ArrayList<>();

    }

}
