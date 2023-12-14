package com.mega.project.utm.Models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String nrik;

    private String username;

    private String password;

    private String role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "expired_on")
    private LocalDateTime expiredOn;

}
