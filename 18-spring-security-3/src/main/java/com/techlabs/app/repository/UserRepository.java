// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.repository;

import com.techlabs.app.entity.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail(String email);

   Optional<User> findByUsernameOrEmail(String username, String email);

   Optional<User> findByUsername(String username);

   Boolean existsByUsername(String username);

   Boolean existsByEmail(String email);
}
