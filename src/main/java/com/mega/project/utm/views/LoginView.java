package com.mega.project.utm.views;

import java.util.List;
import java.util.Map;

import com.mega.project.utm.services.RoleService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginI18n.ErrorMessage;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | MegaPay Parameter")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();

	public LoginView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		H1 title = new H1("UTM");
		title.setClassName("title-mega");
		add(title, login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		// System.out.println(beforeEnterEvent.get);
		LoginI18n loginI18n = LoginI18n.createDefault();
		// LoginI18n.Form i18nForm = loginI18n.getForm();

		ErrorMessage errorMessage = loginI18n.getErrorMessage();
		Map<String, List<String>> listParam = beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters();
		if (listParam.containsKey("error")) {
			List<String> listError = listParam.get("error");
			if (listError.contains("timeout")) {
				errorMessage.setMessage("Api validasi timeout");
				errorMessage.setTitle("Timeout");
				// loginI18n.setErrorMessage(errorMessage);
			}
			if (listError.contains("expired")) {
				errorMessage.setMessage("User expired, hubungi admin IT");
				errorMessage.setTitle("Expired");
			}
			loginI18n.setErrorMessage(errorMessage);
			login.setError(true);
			login.setI18n(loginI18n);
		}
		// if (beforeEnterEvent.getLocation()
		// .getQueryParameters()
		// .getParameters()
		// .containsKey("error=password")) {
		// errorMessage.setMessage("Username atau Password salah");
		// errorMessage.setTitle("Error");
		// loginI18n.setErrorMessage(errorMessage);
		// login.setError(true);
		// }

	}
}