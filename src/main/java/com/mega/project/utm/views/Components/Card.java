package com.mega.project.utm.views.Components;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;

public class Card extends Composite<Div> {

    private Div cardbody;
    private Div cardheader;
    private H5 h5;
    private Component[] header_components;
    private Component[] body_components;

    public Card(Component[] header_components, Component[] body_components) {
        this.header_components = header_components;
        this.body_components = body_components;
        this.addClassName("card");
        // this.getElement().setAttribute("style", "display: flex;justify-content:
        // center;align-items: center;");

        cardbody = new Div();
        cardheader = new Div();

        cardheader.setClassName("card-header");
        cardheader.getElement().setAttribute("style", "text-align: center;");
        cardbody.setClassName("card-body");
        cardbody.getElement().setAttribute("style", "align-self:center;");
        // h5.setText("coba dulu yak");
        // h5.setClassName("card-title");
        cardheader.add(header_components);
        cardbody.add(body_components);
        getContent().add(cardheader, cardbody);
    }

}
