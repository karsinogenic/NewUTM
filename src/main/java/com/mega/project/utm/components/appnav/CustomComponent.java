package com.mega.project.utm.components.appnav;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CustomComponent {
    @Value("${authentication.url}")
    private String authUrl;

    @Value("${authentication.enable}")
    private Boolean useAuth;

    @Value("${file.save.path}")
    private String filePath;
}
