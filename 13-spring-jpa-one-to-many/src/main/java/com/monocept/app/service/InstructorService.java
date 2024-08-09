package com.monocept.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monocept.app.entity.Course;
import com.monocept.app.entity.Instructor;

@Service
public interface InstructorService {

	List<Instructor> getAllInstructors();

	Instructor saveInstructor(Instructor instructor);

	Instructor updateInstructor(Instructor instructor);

	Instructor findInstructorById(int id);

	void deleteInstructorByid(int id);

	List<Course> getAllCourses();

	Course saveCourse(Course course);

	Instructor addCourseToInstructor(int instructorId, int courseId);

	Instructor removeCourseToInstructor(int instructorId, int courseId);

}
