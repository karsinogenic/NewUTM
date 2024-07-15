package com.mega.project.utm.views.test;

import org.vaadin.crudui.crud.impl.GridCrud;

// import com.github.appreciated.card.Card;
// import com.github.appreciated.card.content.IconItem;
// import com.github.appreciated.card.content.Item;
// import com.github.appreciated.card.label.PrimaryLabel;
// import com.github.appreciated.card.label.SecondaryLabel;
// import com.github.appreciated.card.label.TitleLabel;
import com.mega.project.utm.views.MainLayout;
import com.mega.project.utm.views.Components.Card;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("TEST")
@Route(value = "test", layout = MainLayout.class)
public class TestView extends VerticalLayout {

  public TestView() {
    String html = """
          <div class="card" style="width: 18rem;">
          <div class="card-body">
            <h5 class="card-title">cardtitle</h5>
            <p class="card-text">cardtxt</p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
          </div>
        </div>
              """;

    H5 coba = new H5("COBA DULU YAK");
    coba.setClassName("card-title");
    Paragraph p = new Paragraph("Aayayyayayya");
    p.setClassName("card-text");
    Anchor anchor = new Anchor("/", "balik");
    anchor.setClassName("btn btn-primary");
    Component[] header_com = { coba };
    Component[] body_com = { p, anchor };
    Card new_Card = new Card(header_com, body_com);

    Div carosel = carousel();

    Button enableScrollingButton = new Button("Enable body scrolling");
    enableScrollingButton.addClassNames("btn", "btn-primary");
    enableScrollingButton.getElement().setAttribute("type", "button");
    enableScrollingButton.getElement().setAttribute("data-bs-toggle", "offcanvas");
    enableScrollingButton.getElement().setAttribute("data-bs-target", "#offcanvasScrolling");
    enableScrollingButton.getElement().setAttribute("aria-controls", "offcanvasScrolling");

    add(new_Card, carosel, enableScrollingButton, offCanvas(), createSpinner("warning"));

  }

  public HtmlComponent buttonPopOver() {
    HtmlComponent btnNew = new HtmlComponent("button");
    // Button btn = new Button("AYAYAY");
    btnNew.setClassName("btn btn-lg btn-danger");
    btnNew.getElement().setAttribute("type", "button");
    btnNew.getElement().setAttribute("data-bs-title", "Popover title");
    btnNew.getElement().setAttribute("data-bs-toggle", "popover");
    btnNew.getElement().setAttribute("data-bs-content", "And here's some amazing content. It's very engaging. Right?");

    return btnNew;
  }

  public Div carousel() {
    Div carousel = new Div();
    carousel.setId("carouselExampleControls");
    carousel.addClassNames("carousel", "slide");
    carousel.getElement().setAttribute("data-bs-ride", "carousel");

    // Carousel inner container
    Div innerContainer = new Div();
    innerContainer.addClassNames("carousel-inner");

    // Carousel items
    Div item1 = createCarouselItem(true, "Item 1");
    Div item2 = createCarouselItem(false, "Item 2");
    Div item3 = createCarouselItem(false, "Item 3");

    // Adding items to inner container
    innerContainer.add(item1, item2, item3);

    // Previous button
    HtmlContainer prevButton = createControlButton("carousel-control-prev", "Previous", "prev");
    prevButton.getElement().setAttribute("data-bs-target", "#carouselExampleControls");

    // Next button
    HtmlContainer nextButton = createControlButton("carousel-control-next", "Next", "next");
    nextButton.getElement().setAttribute("data-bs-target", "#carouselExampleControls");

    // Adding items to carousel
    carousel.add(innerContainer, prevButton, nextButton);

    return carousel;
  }

  private Div createCarouselItem(Boolean active, String altText) {
    Div item = new Div();
    item.addClassNames("carousel-item " + (active ? "active" : ""));

    Image image = new Image("https://via.placeholder.com/600x400", "");
    // image.addClassNames("d-block", "w-100", "h-100");

    item.add(image);
    return item;
  }

  private HtmlContainer createControlButton(String className, String ariaLabel, String slideDirection) {
    HtmlContainer button = new HtmlContainer("button");
    button.addClassNames(className);
    button.getElement().setAttribute("type", "button");
    button.getElement().setAttribute("data-bs-slide", slideDirection);

    Span icon = new Span();
    icon.addClassNames("carousel-control-" + slideDirection + "-icon", "carousel-control-icon");
    icon.getElement().setAttribute("aria-hidden", "true");

    Span label = new Span(ariaLabel);
    label.addClassNames("visually-hidden");

    button.add(icon, label);

    return button;
  }

  private Div offCanvas() {
    // Offcanvas dialog
    Div offcanvasContainer = new Div();
    offcanvasContainer.setId("offcanvasScrolling");
    offcanvasContainer.addClassNames("offcanvas", "offcanvas-start");
    offcanvasContainer.getElement().setAttribute("data-bs-scroll", "true");
    offcanvasContainer.getElement().setAttribute("data-bs-backdrop", "false");
    offcanvasContainer.getElement().setAttribute("tabindex", "-1");

    // Offcanvas header
    Div offcanvasHeader = new Div();
    offcanvasHeader.addClassNames("offcanvas-header");

    H5 offcanvasTitle = new H5("Offcanvas with body scrolling");
    offcanvasTitle.addClassNames("offcanvas-title");

    Button closeButton = new Button();
    closeButton.addClassNames("btn-close");
    closeButton.getElement().setAttribute("type", "button");
    closeButton.getElement().setAttribute("data-bs-dismiss", "offcanvas");
    closeButton.getElement().setAttribute("aria-label", "Close");

    offcanvasHeader.add(offcanvasTitle, closeButton);

    // Offcanvas body
    Div offcanvasBody = new Div();
    offcanvasBody.addClassNames("offcanvas-body");
    offcanvasBody.setText("Try scrolling the rest of the page to see this option in action.");

    // Adding components to offcanvas container
    offcanvasContainer.add(offcanvasHeader, offcanvasBody);

    // Adding components to main layout
    return offcanvasContainer;
  }

  private Div createSpinner(String type) {
    Div spinnerDiv = new Div();
    spinnerDiv.addClassNames("spinner-border", "text-" + type);
    spinnerDiv.getElement().setAttribute("role", "status");

    Div visuallyHiddenSpan = new Div();
    // Label span_label = new Label("Loading...");
    // visuallyHiddenSpan.add(span_label);
    visuallyHiddenSpan.addClassNames("visually-hidden");
    spinnerDiv.add(visuallyHiddenSpan);

    return spinnerDiv;
  }
}
