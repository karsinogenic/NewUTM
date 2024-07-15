package com.mega.project.utm.views.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.json.JSONArray;
import org.json.JSONObject;
import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.CronosMenyimpangBDetailTrx6m;
import com.mega.project.utm.Models.AMLA.CronosSaldoKreditADetailTrx6m;
import com.mega.project.utm.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@PermitAll
@PageTitle("AMLA Txn Detail")
@Route(value = "amla/txndetail", layout = MainLayout.class)
public class AmlaDetailTxnView extends VerticalLayout implements HasUrlParameter<String> {

    // List<AmlaDetailTxn> people = DataService.getPeople();
    private EntityManager entityManager;
    private List<String> param;
    private String parameter;

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        add(createGrid(parameter));

    }

    public AmlaDetailTxnView(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Grid<?> createGrid(String parameter) {
        // System.out.println(parameter);
        // parameter = this.parameter;
        Class curr_class = CronosMenyimpangBDetailTrx6m.class;

        List<?> isi = new ArrayList<>();

        // List<Class<?>> isi = getAllData(curr_class, "2024-03-06",
        // "0000000000011021397",
        // "cronos_menyimpang_b_detail_trx_6m", "create_date", "olst_txn_acct_nbr");
        Grid<?> grid = new Grid<>();

        if (parameter.toUpperCase().contains("TRM001")) {

        } else if (parameter.toUpperCase().contains("TRM002")) {

        } else if (parameter.toUpperCase().contains("TRM003")) {

        } else if (parameter.toUpperCase().contains("TRM004")) {

        } else if (parameter.toUpperCase().contains("TRM005")) {

        } else if (parameter.toUpperCase().contains("TRM006")) {

        } else if (parameter.toUpperCase().contains("TRM007")) {
            isi = getAllData(CronosMenyimpangBDetailTrx6m.class, "2024-03-06", "0000000000011021397",
                    "cronos_menyimpang_b_detail_trx_6m", "create_date", "olst_txn_acct_nbr");
            Grid<CronosMenyimpangBDetailTrx6m> trm007Grid = new Grid<CronosMenyimpangBDetailTrx6m>(
                    CronosMenyimpangBDetailTrx6m.class);

            trm007Grid.setItems((List<CronosMenyimpangBDetailTrx6m>) isi);
            trm007Grid.removeAllColumns();
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstTxnAcctNumber).setHeader("Account Number")
                    .setAutoWidth(true).setSortable(true);
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstTxnCardNumber).setHeader("Card Number")
                    .setAutoWidth(true).setSortable(true);
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstMccCode).setHeader("MCC")
                    .setAutoWidth(true).setSortable(true);
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstMechNumber).setHeader("Merchant Number")
                    .setAutoWidth(true).setSortable(true);
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstPosEntryMode).setHeader("Pos Entry Mode")
                    .setAutoWidth(true).setSortable(true);
            trm007Grid.addColumn(CronosMenyimpangBDetailTrx6m::getOlstTxnPostingDate).setHeader("Posting Date")
                    .setAutoWidth(true).setSortable(true);
            grid = trm007Grid;
        } else if (parameter.toUpperCase().contains("SK1")) {
            isi = getAllData(CronosSaldoKreditADetailTrx6m.class, "2024-03-06", "0000000000010241847",
                    "cronos_saldo_kredit_a_detail_trx_6m", "create_date", "olst_txn_acct_nbr");
            Grid<CronosSaldoKreditADetailTrx6m> sk1Grid = new Grid<CronosSaldoKreditADetailTrx6m>(
                    CronosSaldoKreditADetailTrx6m.class);

            sk1Grid.setItems((List<CronosSaldoKreditADetailTrx6m>) isi);
            sk1Grid.removeAllColumns();
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstTxnAcctNumber).setHeader("Account Number")
                    .setAutoWidth(true).setSortable(true);
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstTxnCardNumber).setHeader("Card Number")
                    .setAutoWidth(true).setSortable(true);
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstMccCode).setHeader("MCC")
                    .setAutoWidth(true).setSortable(true);
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstMechNumber).setHeader("Merchant Number")
                    .setAutoWidth(true).setSortable(true);
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstPosEntryMode).setHeader("Pos Entry Mode")
                    .setAutoWidth(true).setSortable(true);
            sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::getOlstTxnPostingDate).setHeader("Posting Date")
                    .setAutoWidth(true).setSortable(true);
            // sk1Grid.addColumn(CronosSaldoKreditADetailTrx6m::get).setHeader("Pos Entry
            // Mode")
            // .setAutoWidth(true);
            grid = sk1Grid;

        } else if (parameter.toUpperCase().contains("SK2")) {

        } else if (parameter.toUpperCase().contains("SK3")) {

        } else {
            System.out.println("handle not found");
        }
        return grid;
    }

    private static Component createSubscriberHeader() {
        Span span = new Span("Subscriber");
        Icon icon = VaadinIcon.INFO_CIRCLE.create();
        icon.getElement().setAttribute("title",
                "Subscribers are paying customers");
        icon.getStyle().set("height", "var(--lumo-font-size-m)").set("color",
                "var(--lumo-contrast-70pct)");

        HorizontalLayout layout = new HorizontalLayout(span, icon);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(false);

        return layout;
    }

    private static String createSubscriberFooterText(List<CronosMenyimpangBDetailTrx6m> people) {
        // long subscriberCount =
        // people.stream().filter(CronosMenyimpangBDetailTrx6m::isSubscriber)
        // .count();

        return String.format("0 subscribers");
    }

    private static Component createMembershipHeader() {
        Span span = new Span("Membership");
        Icon icon = VaadinIcon.INFO_CIRCLE.create();
        icon.getElement().setAttribute("title",
                "Membership levels determines which features a client has access to");
        icon.getStyle().set("height", "var(--lumo-font-size-m)").set("color",
                "var(--lumo-contrast-70pct)");

        HorizontalLayout layout = new HorizontalLayout(span, icon);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(false);

        return layout;
    }

    private static String createMembershipFooterText(List<CronosMenyimpangBDetailTrx6m> people) {
        // long regularCount = people.stream()
        // .filter(person -> "Regular".equals(person.getMembership()))
        // .count();
        // long premiumCount = people.stream()
        // .filter(person -> "Premium".equals(person.getMembership()))
        // .count();
        // long vipCount = people.stream()
        // .filter(person -> "VIP".equals(person.getMembership())).count();

        return String.format("5 regular, 2 premium, 0 VIP");
    }

    private List<?> getAllData(Class<?> kelas, String date, String accnum, String table, String colDate,
            String colAccNum) {

        String queryString = String.format("select * from %s cm where Date(cm.%s)= Date('%s') and cm.%s = '%s'", table,
                colDate, date, colAccNum, accnum);

        Query query = entityManager.createNativeQuery(queryString, kelas);
        List<?> yow = new ArrayList<>();
        System.out.println("kelas1: " + kelas.getName());
        if (kelas.getName().contains("CronosMenyimpangBDetail")) {
            yow = (List<CronosMenyimpangBDetailTrx6m>) query.getResultList();
        } else if (kelas.getName().contains("CronosSaldoKreditADetail")) {
            yow = (List<CronosSaldoKreditADetailTrx6m>) query.getResultList();
        }
        // System.out.println("kelas: " + yow.get(0).getClass());
        JSONArray object = new JSONArray(yow);
        return yow;
    }

}
