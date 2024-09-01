package com.techlabs.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Administrator;
import com.techlabs.app.entity.User;

@Repository
public interface AdminRepository extends JpaRepository<Administrator, Long> {
    // Find an administrator by their associated user
    Optional<Administrator> findByUser(User user);

    // Find an administrator by their name
    Optional<Administrator> findByName(String name);

    // Additional custom queries can be added here
}
