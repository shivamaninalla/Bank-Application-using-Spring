package com.monocept.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocept.app.entity.Course;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
