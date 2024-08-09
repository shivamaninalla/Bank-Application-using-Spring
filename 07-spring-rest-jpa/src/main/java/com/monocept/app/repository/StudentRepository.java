package com.monocept.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.app.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
