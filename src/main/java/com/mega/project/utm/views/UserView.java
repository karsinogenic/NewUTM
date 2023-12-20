package com.mega.project.utm.views;

import java.time.LocalDateTime;

import org.vaadin.crudui.crud.impl.GridCrud;

import com.mega.project.utm.Models.RuleResult;
import com.mega.project.utm.Models.User;
import com.mega.project.utm.Repositories.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({ "ROLE_ADMIN" })
@PageTitle("User")
@Route(value = "user", layout = MainLayout.class)
public class UserView extends VerticalLayout {

    GridCrud<User> crud = new GridCrud<>(User.class);
    private UserRepository userRepository;
    Long Id = null;
    FormLayout form = createForm(null);
    Dialog formDialog = new Dialog();
    Button addButton = new Button("Add");
    Button updateButton = new Button("Update");
    Button deleteButton = new Button("Delete");

    public UserView(UserRepository userRepository) {
        this.userRepository = userRepository;

        crud.setFindAllOperation(() -> this.userRepository.findAll());
        crud.getGrid().removeAllColumns();
        crud.setAddOperationVisible(false);
        crud.setDeleteOperationVisible(false);
        crud.setUpdateOperationVisible(false);

        crud.getGrid().addColumn(hasil -> hasil.getNrik()).setHeader("NRIK");
        crud.getGrid().addColumn(hasil -> hasil.getRole()).setHeader("Role");
        crud.getGrid().addColumn(hasil -> hasil.getUsername()).setHeader("Username");
        crud.getGrid().addColumn(hasil -> hasil.getExpiredOn()).setHeader("Expired On");
        crud.getGrid().addColumn(hasil -> hasil.getLastLogin()).setHeader("Last Login");

        // Button

        HorizontalLayout comboButton = new HorizontalLayout();

        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
        comboButton.add(addButton, updateButton, deleteButton);
        comboButton.setWidth("100%");
        comboButton.setClassName(LumoUtility.JustifyContent.BETWEEN);
        crud.getCrudLayout().addToolbarComponent(comboButton);

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        // Form

        // Event
        cellFocusEvent();

        addButton.addClickListener(event -> {
            form = createForm(null);
            formDialog = new Dialog(form);
            formDialog.open();
        });

        updateButton.addClickListener(event -> {
            form = createForm(Id);
            formDialog = new Dialog(form);
            formDialog.open();

        });

        add(crud, formDialog);
    }

    public FormLayout createForm(Long Id) {
        FormLayout form = new FormLayout();
        User newUser = new User();

        TextField nrik = new TextField("Nrik");
        ComboBox<String> role = new ComboBox<>("Role");
        role.setItems("APPROVER", "ANALYST", "ADMIN");
        TextField username = new TextField("Username");

        if (Id != null) {
            // System.out.println("masol");
            User user = this.userRepository.findById(Id).get();
            newUser.setId(user.getId());
            nrik.setValue(user.getNrik());
            role.setValue(user.getRole());
            username.setValue(user.getUsername());
        }
        form.setColspan(username, 2);

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickListener(event -> {
            newUser.setExpiredOn(LocalDateTime.now().plusMonths(3));
            newUser.setNrik(nrik.getValue());
            newUser.setRole(role.getValue());
            newUser.setUsername(username.getValue());
            this.userRepository.save(newUser);
            UI.getCurrent().getPage().reload();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(event -> {
            formDialog.close();
        });

        form.add(nrik, role, username, saveButton, cancelButton);
        return form;
    }

    public void cellFocusEvent() {
        crud.getGrid().addSelectionListener(event -> {
            try {
                // System.out.println("select " + event.getFirstSelectedItem().get().getId());
                if (event.getFirstSelectedItem().isPresent()) {
                    Id = event.getFirstSelectedItem().get().getId();
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    Id = null;
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    // form = createForm(Id);
                }
            } catch (Exception e) {
                Id = null;
                updateButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }

            form = createForm(Id);

        });

    }

}
