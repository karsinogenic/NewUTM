package com.mega.project.utm.config;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mega.project.utm.views.Error404View;
import com.mega.project.utm.views.ErrorView;
import com.mega.project.utm.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.internal.DefaultErrorHandler;
import com.vaadin.flow.server.HttpStatusCode;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
@AnonymousAllowed
@ParentLayout(MainLayout.class)

public class AccessDeniedExceptionHandler
        extends Component
        implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
            ErrorParameter<NotFoundException> parameter) {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(
                    parameter.hasCustomMessage() ? parameter.getCustomMessage()
                            : "Route is not found",
                    parameter.getCaughtException());
            Map<String, String> param = new HashMap<>();
            // param.put("exception", "URL NOT FOUND");
            // RouteParameters routeParameters = new RouteParameters(param);
            // event.rerouteTo(ErrorView.class);

        }
        String path = event.getLocation().getPath();
        String additionalInfo = "";
        if (parameter.hasCustomMessage()) {
            additionalInfo = parameter.getCustomMessage();
            // ErrorView errorView = new ErrorView(additionalInfo);
            Map<String, String> param = new HashMap<>();
            param.put("exception", "cok");
            RouteParameters routeParameters = new RouteParameters("exception", "cok");
            event.rerouteTo("error");

        } else {
            event.rerouteTo(Error404View.class);
        }
        path = Jsoup.clean(path, Safelist.none());
        additionalInfo = Jsoup.clean(additionalInfo, Safelist.none());
        System.out.println("additional: " + additionalInfo);

        // boolean productionMode = event.getUI().getSession().getConfiguration()
        // .isProductionMode();

        // String template = "";
        // {{routes}} should be replaced first so that it's not possible to
        // insert {{routes}} snippet via other template values which may result
        // in the listing of all available routes when this shouldn't not happen
        // if (template.contains("{{routes}}")) {
        // template = template.replace("{{routes}}", getRoutes(event));
        // }
        // template = template.replace("{{additionalInfo}}", additionalInfo);
        // template = template.replace("{{path}}", path);

        // getElement().setChild(0, new Html(template).getElement());
        return HttpStatusCode.NOT_FOUND.getCode();
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(AccessDeniedExceptionHandler.class);
    }

    // @Override
    // public int setErrorParameter(BeforeEnterEvent event,
    // ErrorParameter<CustomAccessDeniedException> parameter) {
    // // System.out.println(event.ge);
    // getElement().setText("Could not navigate to '"
    // + event.getLocation().getPath()
    // + "'");
    // return HttpServletResponse.SC_NOT_FOUND;
    // }
}
