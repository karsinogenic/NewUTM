package com.mega.project.utm.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mega.project.utm.Models.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByNrik(String nrik);

}
