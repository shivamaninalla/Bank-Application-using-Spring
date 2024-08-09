package com.monocept.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monocept.app.entity.Course;
import com.monocept.app.entity.Instructor;
import com.monocept.app.repository.CourseRepository;
import com.monocept.app.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService{

	private InstructorRepository instructorRepository;
	private CourseRepository courseRepository;
	
	
	public InstructorServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository) {
		super();
		this.instructorRepository = instructorRepository;
		this.courseRepository = courseRepository;
	}


	@Override
	public List<Instructor> getAllInstructors() {
		
		return instructorRepository.findAll();
	}


	@Override
	public Instructor saveInstructor(Instructor instructor) {
		
		return instructorRepository.save(instructor);
	}


	@Override
	public Instructor updateInstructor(Instructor instructor) {
		
		Instructor employeeById = findInstructorById(instructor.getId());
		if(employeeById!=null) {
			return instructorRepository.save(instructor);
		}
		return null;
	}


	@Override
	public Instructor findInstructorById(int id) {
		
		Optional<Instructor> byId = instructorRepository.findById(id);
		if(byId.isPresent()) {
			return byId.get();
		}
		return null;
	}


	@Override
	public void deleteInstructorByid(int id) {
		instructorRepository.deleteById(id);
		
	}


	@Override
	public List<Course> getAllCourses() {
		
		return courseRepository.findAll();
	}


	@Override
	public Course saveCourse(Course course) {
		
		return courseRepository.save(course);
	}


	@Override
	public Instructor addCourseToInstructor(int instructorId, int courseId) {
		Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
		if(instructor!=null) {
			Course course = courseRepository.findById(courseId).orElse(null);
			if(course!=null) {
				if(course.getInstructor()==null) {
					instructor.addCourse(course);
					course.setInstructor(instructor);
					instructorRepository.save(instructor);
					return instructor;
				}
				else {
					System.out.println("Instructor is already assigned to course");
				}
			}
		}
		return null;
	}


	@Override
	public Instructor removeCourseToInstructor(int instructorId, int courseId) {
		Instructor instructor = instructorRepository.findById(instructorId).orElse(null);
		if(instructor!=null)
		{
			  Course course = courseRepository.findById(courseId).orElse(null);
			  if(course!=null) {
				  instructor.removeCourseForInstructor(course);
				  courseRepository.save(course);
				 return instructorRepository.save(instructor);
				  
//				  Optional<Instructor> byId = instructorRepository.findById(instructorId);
//				  return byId.get();
			  }
			
			
			
		}
		return null;
	}
	
	
	

}
