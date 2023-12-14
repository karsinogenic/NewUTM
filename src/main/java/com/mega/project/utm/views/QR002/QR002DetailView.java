package com.mega.project.utm.views.QR002;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Models.HistoryMerchTrans;
import com.mega.project.utm.Models.Merchant;
import com.mega.project.utm.Models.QR002;
import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.Repositories.MerchantRepository;
import com.mega.project.utm.Repositories.RuleResultRepository;
import com.mega.project.utm.services.CustomRule;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.Card;
import com.mega.project.utm.views.Components.MyNotification;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("Detail")
@Route(value = "detail", layout = MainLayout.class)
public class QR002DetailView extends VerticalLayout implements HasUrlParameter<String> {

    private List<Long> amounts = new ArrayList<>();
    private CustomRule customRule;
    private String merchantId;
    private MerchantRepository merchantRepository;
    private RuleResultRepository ruleResultRepository;
    private MyNotification myNotification;
    private HistoryMemoRepository historyMemoRepository;
    Button memo;
    TextField memoField = new TextField();
    TextField statusField = new TextField();
    TextField reviewerField = new TextField();
    TextArea input = new TextArea();

    GridCrud<HistoryMerchTrans> crud = new GridCrud<>(HistoryMerchTrans.class);
    GridCrud<HistoryMemo> crudMemo = new GridCrud<>(HistoryMemo.class);

    ComboBox<String> comboBox = new ComboBox<>("Status");

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        // this.merchantId = event.getRouteParameters().getWildcard(0);
        // add(mer)
        H5 coba = new H5("COBA DULU YAK");
        coba.setClassName("card-title");

        RuleResult ruleResult = this.ruleResultRepository.findById(parameter).get();
        Merchant merchant = this.merchantRepository.findByMerchNum(ruleResult.getMId());
        H3 merch_name = new H3(merchant == null ? "NON GROUP" : merchant.getMerchName());
        merch_name.setClassName("card-title");

        HorizontalLayout hl = new HorizontalLayout();
        TextField city = new TextField("City");
        city.setReadOnly(true);
        TextField cat_visa = new TextField("Category Visa");
        cat_visa.setReadOnly(true);

        if (merchant != null) {
            cat_visa.setValue(merchant.getCategoryVisa());
            // cat_visa.setEnabled(false);

            city.setValue(merchant.getMerchCity());
        }
        memoField.setReadOnly(true);
        reviewerField.setReadOnly(true);
        statusField.setReadOnly(true);
        VerticalLayout dialogLayout = createDialogLayout(null);

        if (ruleResult.getMemo() != null) {
            memoField.setValue(ruleResult.getMemo());
            reviewerField.setValue(ruleResult.getReviewBy() == null ? "" : ruleResult.getReviewBy());
            statusField.setValue(ruleResult.getStatus() == null ? "" : ruleResult.getStatus());
            dialogLayout = createDialogLayout(ruleResult.getMemo());
        }

        Dialog dialog = new Dialog();

        dialog.setHeaderTitle("Tambah Memo");

        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog, ruleResult);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.setWidth("50%");

        memo.addClickListener(e -> dialog.open());

        hl.add(cat_visa, city);

        Component[] header_com = { merch_name };
        Component[] body_com = { hl };
        Card new_Card = new Card(header_com, body_com);
        new_Card.getElement().setAttribute("style",
                "align-self:center;width: 80%;");

        System.out.println("param: " + parameter);
        crud.setFindAllOperation(() -> customRule.NewDetailQR002(parameter));
        crudMemo.setFindAllOperation(() -> this.historyMemoRepository.findByRuleId(parameter));
        customRule.NewDetailQR002(parameter);
        // amounts = ruleResult.getSum();
        Long amount = ruleResult.getSum();

        HorizontalLayout hz = new HorizontalLayout();
        hz.setWidth("100%");
        hz.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        H4 total_amount = new H4("Total Amount: ");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedNumber = numberFormat.format(amount).toString();
        H4 total_amount1 = new H4("Rp. " + formattedNumber);
        hz.add(total_amount, total_amount1);
        add(new_Card, crud, hz, dialog, crudMemo);
    }

    private Button createSaveButton(Dialog dialog, RuleResult ruleResult) {
        Button save = new Button("Save");
        HistoryMemo historyMemo = new HistoryMemo();
        save.addClickListener(e -> {
            ruleResult.setReviewBy(ruleResult.getLockedBy());
            historyMemo.setReviewBy(ruleResult.getLockedBy());
            historyMemo.setMemo(input.getValue());
            historyMemo.setRuleId(ruleResult.getId());
            historyMemo.setReviewDateTime(LocalDateTime.now());
            historyMemo.setStatus(comboBox.getValue());

            Notification notif = new Notification();
            try {
                ruleResult.setMemo(input.getValue());
                ruleResult.setStatus(comboBox.getValue());
                this.ruleResultRepository.save(ruleResult);
                this.historyMemoRepository.save(historyMemo);
                notif = this.myNotification.success("Berhasil menambahkan memo");
                notif.open();
                dialog.close();
                UI.getCurrent().getPage().reload();
            } catch (Exception ez) {
                notif = this.myNotification.error("Gagal menambahkan memo");
                notif.open();
                dialog.close();
            }
        });
        return save;
    }

    private VerticalLayout createDialogLayout(String oldMemo) {
        comboBox.setItems("Consider Genuine", "Genuine", "Suspect", "Fraud");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("100%");
        input.setWidth("100%");
        comboBox.setWidth("100%");
        input.setLabel("Memo");
        // if (oldMemo != null) {
        // input.setValue(oldMemo);
        // }
        verticalLayout.add(input, comboBox);
        return verticalLayout;
    }

    public QR002DetailView(CustomRule customRule, MerchantRepository merchantRepository,
            RuleResultRepository ruleResultRepository, MyNotification myNotification,
            HistoryMemoRepository historyMemoRepository) {
        this.customRule = customRule;
        this.merchantRepository = merchantRepository;
        this.ruleResultRepository = ruleResultRepository;
        this.myNotification = myNotification;
        this.historyMemoRepository = historyMemoRepository;
        // System.out.println(merchantId);

        // Merchant merchant = this.merchantRepository.findByMerchNum(merchantId);
        // // if (!opt_merchant.isEmpty()) {
        // H1 merch_nama = new H1(merchant.getMerchName());

        // hl.add(merch_nama);
        // }
        memo = new Button("Tambah Memo");

        HorizontalLayout memoCombo = new HorizontalLayout();
        Label statusLabel = new Label("Latest Status");
        Label reviewerLabel = new Label("Latest Reviewer");
        Label memoLabel = new Label("Latest Memo:");

        memoCombo.add(memoLabel, memoField, statusLabel, statusField, reviewerLabel, reviewerField);
        memoCombo.setAlignItems(FlexComponent.Alignment.CENTER);

        memoCombo.setWidth("70vw");
        memoField.setWidth("20vw");

        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);
        crud.getGrid().setColumns("keyNo", "cardNumber",
                "paymentDate", "paymentTime");

        crudMemo.setAddOperationVisible(false);
        crudMemo.setDeleteOperationVisible(false);
        crudMemo.setUpdateOperationVisible(false);
        crudMemo.getGrid().setColumns("memo", "reviewBy", "status");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        crud.getGrid().addColumn(hasil -> numberFormat.format(hasil.getAmount()).toString())
                .setHeader("Amount")
                .setKey("amount1");

        crudMemo.getGrid().addColumn(
                hasil -> hasil.getReviewDateTime().format(DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss")))
                .setHeader("Amount")
                .setKey("amount1");

        crud.getCrudLayout().addToolbarComponent(memo);
        crud.getCrudLayout().addToolbarComponent(memoCombo);
        // System.out.println(merchantId);

    }

}
