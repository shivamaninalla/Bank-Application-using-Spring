// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.repository;

import com.techlabs.app.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName(String name);
}
