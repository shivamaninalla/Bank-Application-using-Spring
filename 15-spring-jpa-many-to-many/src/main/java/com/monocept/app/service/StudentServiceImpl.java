package com.monocept.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.monocept.app.dto.CoursesDTO;
import com.monocept.app.dto.StudentsDTO;
import com.monocept.app.entity.Courses;
import com.monocept.app.entity.Students;
import com.monocept.app.repository.CourseRepository;
import com.monocept.app.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<StudentsDTO> getAllStudents() {

		List<Students> list = studentRepository.findAll();
		List<StudentsDTO> list2 = convertStudentsToStudentsDtoList(list);

		return list2;
	}

	private List<StudentsDTO> convertStudentsToStudentsDtoList(List<Students> list) {
		List<StudentsDTO> l = new ArrayList<>();
		for (Students s : list) {
			l.add(convertStudentsToStudentsDto(s));
		}
		return l;
	}

	private StudentsDTO convertStudentsToStudentsDto(Students s) {
		StudentsDTO studentsDto = new StudentsDTO();
		studentsDto.setName(s.getName());
		studentsDto.setId(s.getId());

		List<CoursesDTO> courseList = new ArrayList<>();
		if (s.getCourses() != null) {
			for (Courses c : s.getCourses()) {
				CoursesDTO courseDto = new CoursesDTO();
				courseDto.setId(c.getId());
				courseDto.setTitle(c.getTitle());
				courseList.add(courseDto);
			}
			studentsDto.setCourses(courseList);
		}
		return studentsDto;
	}

	@Override
	public StudentsDTO addStudent(StudentsDTO studentDto) {
		Students student = convertStudentDtoToStudent(studentDto);
		Students savedStudent = studentRepository.save(student);
		return convertStudentsToStudentsDto(savedStudent);
	}

//	private StudentsDTO convertStudentDtoToStudent(StudentsDTO studentDto) {
//		List<StudentsDTO> l=new ArrayList<>();
//		for(StudentsDTO s:studentDto) {
//			l.add(convertStudentDtoToStudent(s));
//		}
//		return l;
//	}

	private Students convertStudentDtoToStudent(StudentsDTO studentDto) {
		Students students = new Students();
		students.setName(studentDto.getName());
		students.setId(studentDto.getId());
		Set<Courses> courses = new HashSet<>();
		if (studentDto.getCourses() != null) {

			for (CoursesDTO c : studentDto.getCourses()) {
				Courses existingCourse = courseRepository.findById(c.getId()).orElse(null);
				if (existingCourse != null) {
					courses.add(existingCourse);
				}
//	        else {
//	            Courses newCourse = new Courses();
//	            newCourse.setId(c.getId());
//	            newCourse.setTitle(c.getTitle());
//	            courses.add(newCourse);
//	        }
			}
			students.setCourses(courses);
		}
		return students;
	}

	@Override
	public StudentsDTO getStudentById(int id) {

		Students byId = studentRepository.findById(id).orElse(null);
		return convertStudentsToStudentsDto(byId);
	}

	@Override
	public String deleteStudentById(int id) {
		studentRepository.deleteById(id);
		return "deleted successfully";

	}

	@Override
	public List<CoursesDTO> getAllCourses() {

		return convertCoursesToCourseDTO(courseRepository.findAll());
	}

	private List<CoursesDTO> convertCoursesToCourseDTO(List<Courses> list) {
		List<CoursesDTO> courseDto = new ArrayList<>();
		for (Courses l : list) {
			courseDto.add(convertCoursesToCourseDTO(l));
		}
		return courseDto;

	}

	private CoursesDTO convertCoursesToCourseDTO(Courses l) {
		CoursesDTO coursesDto = new CoursesDTO();
		coursesDto.setId(l.getId());
		coursesDto.setTitle(l.getTitle());

		List<StudentsDTO> studentList = new ArrayList<>();
		if (l.getStudents() != null) {
			for (Students k : l.getStudents()) {
				StudentsDTO studentsDto = new StudentsDTO();
				studentsDto.setName(k.getName());
				studentsDto.setId(k.getId());
				studentList.add(studentsDto);
			}
			coursesDto.setStudents(studentList);
		}
		return coursesDto;
	}

	@Override
	public CoursesDTO addCourse(CoursesDTO courseDto) {
		Courses course = convertCourseDtoToCourse(courseDto);
		Courses savedCourse = courseRepository.save(course);
		return convertCoursesToCourseDTO(savedCourse);
	}

	private Courses convertCourseDtoToCourse(CoursesDTO c) {
		Courses courses = new Courses();
		courses.setId(c.getId());
		courses.setTitle(c.getTitle());
		Set<Students> studentsSet = new HashSet<>();
		if (c.getStudents() != null) {
			for (StudentsDTO studentDto : c.getStudents()) {
				Students students = studentRepository.findById(studentDto.getId()).orElse(null);
				if (students != null) {
					studentsSet.add(students);
				}
//	        else {
//	            Students s = new Students();
//	            s.setId(studentDto.getId());
//	            s.setName(studentDto.getName());
//	            studentsSet.add(s);
//	        }
			}
			courses.setStudents(studentsSet);
		}
		return courses;
	}

	@Override
	public CoursesDTO getCourseById(int id) {

		Courses courses = courseRepository.findById(id).orElse(null);
		CoursesDTO dto = convertCoursesToCourseDTO(courses);
		return dto;
	}

//	@Override
//	public String deleteCourseById(int id) {
//		courseRepository.deleteById(id);
//		return "Course Deleted successfully";
//	}
	@Override
	public String deleteCourseById(int id) {
		Courses course = courseRepository.findById(id).orElse(null);
		if (course != null) {

			for (Students student : course.getStudents()) {
				student.getCourses().remove(course);
				studentRepository.save(student);
			}
			courseRepository.deleteById(id);
			return "Course deleted successfully";
		} else {
			return "Course not found";
		}
	}

	@Override
	public StudentsDTO addStudentToCourse(int student_id, int course_id) {

		Courses course = courseRepository.findById(course_id).orElse(null);
		if (course != null) {
			Students student = studentRepository.findById(student_id).orElse(null);
			if (student != null) {
				// course.add(student);
				student.add(course);
				StudentsDTO dto = convertStudentsToStudentsDto(studentRepository.save(student));

				// return courseRepository.save(course);
				return dto;
			}
		}

		return null;
	}

	@Override
	public StudentsDTO deleteStudentToCourse(int student_id, int course_id) {
		Courses course = courseRepository.findById(course_id).orElse(null);
		if (course != null) {
			Students student = studentRepository.findById(student_id).orElse(null);
			if (student != null) {
				// course.add(student);
				student.remove(course);

				StudentsDTO dto = convertStudentsToStudentsDto(studentRepository.save(student));
				// return courseRepository.save(course);
				return dto;
			}
		}

		return null;
	}

}
