// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.entity.Student;

public interface StudentService {
   List<Student> findAll();

   Student findById(Long id);

   Student save(Student student);

   List<Student> saveAll(List<Student> studentList);

   void deleteById(Long id);
}
