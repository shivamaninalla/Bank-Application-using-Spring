package com.monocept.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.monocept.app.dto.CoursesDTO;
import com.monocept.app.dto.StudentsDTO;
import com.monocept.app.entity.Courses;
import com.monocept.app.entity.Students;

public interface StudentService {

	List<StudentsDTO> getAllStudents();

	StudentsDTO addStudent(StudentsDTO studentDto);

	StudentsDTO getStudentById(int id);

	String deleteStudentById(int id);

	List<CoursesDTO> getAllCourses();

	CoursesDTO addCourse(CoursesDTO courseDto);

	CoursesDTO getCourseById(int id);

	String deleteCourseById(int id);

	StudentsDTO addStudentToCourse(int student_id, int course_id);

	StudentsDTO deleteStudentToCourse(int student_id, int course_id);

}
