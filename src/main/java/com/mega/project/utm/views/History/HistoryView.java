package com.mega.project.utm.views.History;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.HistoryMerchTrans;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@PermitAll
@PageTitle("QR History")
@Route(value = "qr/history", layout = MainLayout.class)
public class HistoryView extends VerticalLayout {

    private RuleResultRepository ruleResultRepository;
    private EntityManager entityManager;
    private String Id;
    private MyNotification myNotification;

    GridCrud<RuleResult> crud = new GridCrud<>(RuleResult.class);

    public HistoryView(RuleResultRepository ruleResultRepository, EntityManager entityManager,
            MyNotification myNotification) {
        this.ruleResultRepository = ruleResultRepository;
        this.entityManager = entityManager;
        this.myNotification = myNotification;

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
        datePickerEnd.setI18n(singleFormatI18nEnd);

        ComboBox<String> status = new ComboBox<>("Status");
        status.setItems("Consider Genuine", "Genuine", "Suspect", "Fraud");

        ComboBox<String> rule = new ComboBox<>("Rule");
        rule.setItems("QR001", "QR002", "QR003", "QR004");

        TextField cardnum = new TextField("Card Number");
        TextField mid = new TextField("Merchant Id");
        Button btn = new Button(new Icon(VaadinIcon.SEARCH));
        Button detail = new Button("Detail");

        btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        hl1.add(datePicker, datePickerEnd, status, rule, cardnum, btn);
        hl1.setFlexGrow(1, datePicker, datePickerEnd, status, rule, cardnum);
        hl1.setAlignItems(FlexComponent.Alignment.END);

        btn.addClickListener(event -> searchData(event, datePicker.getValue(), datePickerEnd.getValue(),
                status.getValue(), rule.getValue(),
                cardnum.getValue()));

        List<RuleResult> ruleResults = this.ruleResultRepository
                .findByDateAndMemoIsNotNull(LocalDate.now().minusDays(1));
        for (int i = 0; i < ruleResults.size(); i++) {
            ruleResults.get(i).setHiddenId(Long.valueOf(i + 1));
        }

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
                // System.out.println("new Id: " + Id);
                detail.setEnabled(true);
            } catch (Exception e) {
                Id = null;
                detail.setEnabled(false);
            }
            // System.out.println(event.getItem().get().getMerchant_id());
        });

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        crud.getGrid().addColumn(hasil -> hasil.getHiddenId()).setHeader("Nomer").setKey("nomer1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getMId()).setHeader("Merchant Id").setKey("mid1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getCardNum()).setHeader("Card Number").setKey("cardnum1")
                .setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getCount()).setHeader("Total Transactions").setKey("totalTrx1")
                .setSortable(true);
        crud.getGrid().addColumn(hasil -> numberFormat.format(hasil.getSum()).toString())
                .setHeader("Total Amount")
                .setKey("totalTrxAmount").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getDate()).setHeader("Date").setKey("date1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getRule()).setHeader("Rule").setKey("rule1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getMemo()).setHeader("Memo").setKey("memo1").setSortable(true);
        crud.getGrid().addColumn(hasil -> hasil.getReviewBy()).setHeader("Review By").setKey("review1")
                .setSortable(true);

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

        // hl2.add(rule, cardnum);
        add(hl1, crud);

    }

    private void searchData(ClickEvent<Button> event, LocalDate localDate, LocalDate localDate2, String status,
            String rule,
            String cardnum) {
        StringBuilder jpql = new StringBuilder("SELECT rr FROM RuleResult rr");
        List<String> queryList = new ArrayList<>();

        if (localDate != null && localDate2 != null) {
            queryList.add(" rr.date BETWEEN'" + localDate + "' AND '" + localDate2 + "'");
        }
        if (status != null && !status.isEmpty()) {
            queryList.add(" rr.status = '" + status + "'");
        }
        if (rule != null && !rule.isEmpty()) {
            queryList.add(" rr.rule = '" + rule + "'");
        }
        if (cardnum != null && !cardnum.isEmpty()) {
            queryList.add(" rr.cardnum = '" + cardnum + "'");
        }

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

        // System.out.println("new query: " + jpql.toString());

        TypedQuery<RuleResult> query = entityManager.createQuery(jpql.toString(), RuleResult.class);

        List<RuleResult> resultList = query.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            resultList.get(i).setHiddenId((long) (i + 1));
        }
        crud.setFindAllOperation(() -> resultList);
        crud.refreshGrid();
    }

}
