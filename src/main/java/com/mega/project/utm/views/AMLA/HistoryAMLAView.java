package com.mega.project.utm.views.AMLA;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;
import com.mega.project.utm.Repositories.AmlaRuleResultRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@PermitAll
@PageTitle("AMLA History")
@Route(value = "amla/history", layout = MainLayout.class)
public class HistoryAMLAView extends VerticalLayout {

    private AmlaRuleResultRepository amlaRuleResultRepository;
    private EntityManager entityManager;
    private String Id;
    private String AmlaId;
    private String RuleCode;
    private MyNotification myNotification;

    GridCrud<AmlaRuleResult> crud = new GridCrud<>(AmlaRuleResult.class);

    public HistoryAMLAView(AmlaRuleResultRepository amlaRuleResultRepository, EntityManager entityManager,
            MyNotification myNotification) {
        this.amlaRuleResultRepository = amlaRuleResultRepository;
        this.entityManager = entityManager;
        this.myNotification = myNotification;

        List<AmlaRuleResult> ruleResults = this.amlaRuleResultRepository
                .findByPostDateApproved(LocalDate.now().minusDays(1));
        for (int i = 0; i < ruleResults.size(); i++) {
            ruleResults.get(i).setHiddenId(Long.valueOf(i + 1));
        }

        String current_user = VaadinSession.getCurrent().getAttribute("username").toString();

        H2 textH2 = new H2("Search History");

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.setWidth("100%");

        DatePicker datePicker = new DatePicker("Tanggal Awal");
        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("yyyy-MM-dd");
        datePicker.setI18n(singleFormatI18n);

        DatePicker datePickerEnd = new DatePicker("Tanggal Akhir");
        DatePicker.DatePickerI18n singleFormatI18nEnd = new DatePicker.DatePickerI18n();
        singleFormatI18nEnd.setDateFormat("yyyy-MM-dd");
        datePicker.setI18n(singleFormatI18nEnd);

        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems("Consider Genuine", "Genuine", "Suspect", "Fraud");

        ComboBox<String> rule = new ComboBox<>("Rule");
        rule.setItems("TRM001", "TRM002", "TRM003", "TRM004", "TRM005");

        TextField cardnum = new TextField("Card Number");
        TextField mid = new TextField("Merchant Id");
        Button btn = new Button(new Icon(VaadinIcon.SEARCH));
        Button detail = new Button("Detail");

        btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        hl1.add(datePicker, status, rule, btn);
        hl1.setFlexGrow(1, datePicker, status, rule);
        hl1.setAlignItems(FlexComponent.Alignment.END);

        btn.addClickListener(event -> searchData(event, datePicker.getValue(),
                status.getValue(), rule.getValue(),
                cardnum.getValue()));

        crud.setFindAllOperation(() -> ruleResults);

        crud.getGrid().removeAllColumns();
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
        crud.getGrid().addColumn(hasil -> hasil.getMemo()).setHeader("Memo").setKey("memo1");
        crud.getGrid().addColumn(hasil -> hasil.getApprovedBy()).setHeader("Approved By").setKey("approve1");
        crud.getGrid().addColumn(hasil -> hasil.getReviewBy()).setHeader("Review By").setKey("review1");
        // crud.getGrid()
        // .addColumn(hasil -> hasil.getIsApproved() == null ? "Pending"
        // : hasil.getIsApproved() == true ? "Approved" : "Denied")
        // .setHeader("Approval Status").setKey("status1");
        // crud.getGrid().addColumn(hasil ->
        // hasil.getMemo()).setHeader("Memo").setKey("memo1");
        // crud.getGrid().addColumn(hasil -> hasil.getReviewBy()).setHeader("Review
        // By").setKey("review1");

        // crud.getGrid()
        // .addColumn(hasil -> hasil.getIsApproved() == null ? pending1
        // : hasil.getIsApproved() == true ? confirmed1 : denied1)
        // .setHeader("Approval Status").setKey("status1");
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

        detail.addClickListener(event -> {
            String base64 = Base64.getEncoder().encodeToString((RuleCode + "_" + AmlaId + "_" + Id).getBytes());
            Notification newNotification = new Notification();
            AmlaRuleResult reOptional = this.amlaRuleResultRepository.findById(Id).get();
            if (reOptional.getLockedBy() == null) {
                reOptional.setLockedBy(current_user);
                this.amlaRuleResultRepository.save(reOptional);
                UI.getCurrent().navigate("amla/detail/" + base64);
            } else {
                if (reOptional.getLockedBy().equals(current_user)) {
                    UI.getCurrent().navigate("amla/detail/" + base64);

                } else {
                    newNotification = myNotification
                            .error("data sedang dikerjakan oleh user " + reOptional.getLockedBy());
                    newNotification.open();

                }
            }
        });

        // hl2.add(rule, cardnum);
        add(hl1, crud);

    }

    private void searchData(ClickEvent<Button> event, LocalDate localDate, String status, String rule,
            String cardnum) {
        StringBuilder jpql = new StringBuilder("SELECT rr FROM AmlaRuleResult rr");
        List<String> queryList = new ArrayList<>();

        if (localDate != null) {
            queryList.add(" rr.postDate = '" + localDate + "'");
        } else if (status != null && !status.isEmpty()) {
            queryList.add(" rr.status = '" + status + "'");
        } else if (rule != null && !rule.isEmpty()) {
            queryList.add(" rr.rule = '" + rule + "'");
        }
        // if (cardnum != null && !cardnum.isEmpty()) {
        // queryList.add(" rr.cardnum = '" + cardnum + "'");
        // }

        if (queryList.size() > 0) {
            jpql.append(" where");
            for (int i = 0; i < queryList.size(); i++) {
                jpql.append(queryList.get(i));
                if (i != queryList.size() - 1) {
                    jpql.append(" and");
                }
            }
        } else {
            queryList.add(" limit 100");

        }

        System.out.println("new query: " + jpql.toString());

        TypedQuery<AmlaRuleResult> query = entityManager.createQuery(jpql.toString(), AmlaRuleResult.class);

        List<AmlaRuleResult> resultList = query.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            resultList.get(i).setHiddenId((long) (i + 1));
        }
        crud.setFindAllOperation(() -> resultList);
        crud.refreshGrid();
    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        return icon;
    }

}
