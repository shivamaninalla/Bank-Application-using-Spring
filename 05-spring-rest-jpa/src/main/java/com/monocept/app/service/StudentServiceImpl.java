package com.monocept.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.monocept.app.entity.Student;
import com.monocept.app.repository.StudentRepository;


@Service
public class StudentServiceImpl implements StudentService{

	private StudentRepository studentRepository;
	
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		super();
		this.studentRepository = studentRepository;
	}

	@Override
	public Student save(Student student) {
		// TODO Auto-generated method stub
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentRepository.findAll();
	}

	@Override
	public List<String> getFirstNames() {
		// TODO Auto-generated method stub
		return studentRepository.getFirstNames();
	}

	@Override
	public Student getStudentById(int i) {
		// TODO Auto-generated method stub
		//studentRepository.findById(i).orElse(null);
		Optional<Student> byId = studentRepository.findById(i);
		if(byId.isPresent()) {
			return byId.get();
		}
		
		return null;
	}
	
	

	@Override
	public List<Student> getStudentByFirstName(String string) {
		// TODO Auto-generated method stub
		return studentRepository.getStudentByFirstName(string);
	}

	@Override
	public List<Student> getStudentsByFirstNameLastName(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStudent(Student student) {
		studentRepository.save(student);
		
	}

	@Override
	public void deleteStudent(int i) {
		studentRepository.deleteById(i);
		
	}

	@Override
	public void updateStudentWithoutMerge(Student student) {
		studentRepository.updateStudentWithoutMerge(student);
		
	}

	@Override
	public void deleteAllStudentsLessThanThree(int i) {
		studentRepository.deleteAllStudentsLessThanThree(i);
		
	}

}
