// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.repository;

import com.techlabs.app.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
