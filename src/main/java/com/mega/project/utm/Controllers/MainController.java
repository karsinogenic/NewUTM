package com.mega.project.utm.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mega.project.utm.Models.HistoryMemo;
import com.mega.project.utm.Repositories.HistoryMemoRepository;
import com.mega.project.utm.services.SecurityService;
import com.mega.project.utm.views.LoginView;
import com.vaadin.flow.component.UI;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    private HistoryMemoRepository historyMemoRepository;

    @Autowired
    private SecurityService securityService;

    @GetMapping("session-end")
    public String login(HttpServletResponse response) throws IOException {
        System.out.println("timeout maybe");
        return "redirect:/login";
        // securityService.logout();
    }

}
