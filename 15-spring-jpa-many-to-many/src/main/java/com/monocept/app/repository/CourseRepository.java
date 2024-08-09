package com.monocept.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocept.app.entity.Courses;

@Repository
public interface CourseRepository extends JpaRepository<Courses, Integer>{

}
