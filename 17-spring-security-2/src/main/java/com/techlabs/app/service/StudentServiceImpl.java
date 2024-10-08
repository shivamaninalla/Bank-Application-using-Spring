// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techlabs.app.entity.Student;
import com.techlabs.app.exception.StudentApiException;
import com.techlabs.app.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
   @Autowired
   private StudentRepository repository;

   public StudentServiceImpl() {
   }

   public List<Student> findAll() {
      return this.repository.findAll();
   }

   public Student findById(Long id) {
      Optional<Student> optional = this.repository.findById(id);
      if (optional.isPresent()) {
         return (Student)optional.get();
      } else {
         throw new StudentApiException(HttpStatus.NOT_FOUND, "Student with id was not found" + String.valueOf(id));
      }
   }

   @Transactional
   public Student save(Student student) {
      return (Student)this.repository.save(student);
   }

   @Transactional
   public void deleteById(Long id) {
      this.repository.deleteById(id);
   }

   public List<Student> saveAll(List<Student> studentList) {
      return this.repository.saveAll(studentList);
   }
}
