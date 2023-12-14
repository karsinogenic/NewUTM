package com.mega.project.utm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mega.project.utm.Repositories.MerchantRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SpringBootApplication
@Theme(value = "myapp", variant = Lumo.LIGHT)
public class UtmApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(UtmApplication.class, args);
		// System.out.println();
	}

}
