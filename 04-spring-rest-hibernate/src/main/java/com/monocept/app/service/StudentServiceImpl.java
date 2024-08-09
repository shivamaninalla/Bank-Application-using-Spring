package com.monocept.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monocept.app.dao.StudentDao;
import com.monocept.app.entity.Student;


@Service
public class StudentServiceImpl implements StudentService{

	private StudentDao studentDao;
	
	
	public StudentServiceImpl(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	@Override
	public Student save(Student student) {
		// TODO Auto-generated method stub
		return studentDao.save(student);
	}

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentDao.getAllStudents();
	}

	@Override
	public List<String> getFirstNames() {
		// TODO Auto-generated method stub
		return studentDao.getFirstNames();
	}

	@Override
	public Student getStudentById(int i) {
		// TODO Auto-generated method stub
		return studentDao.getStudentById(i);
	}

	@Override
	public List<Student> getStudentByFirstName(String string) {
		// TODO Auto-generated method stub
		return studentDao.getStudentByFirstName(string);
	}

	@Override
	public List<Student> getStudentsByFirstNameLastName(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStudent(Student student) {
		studentDao.updateStudent(student);
		
	}

	@Override
	public void deleteStudent(int i) {
		studentDao.deleteStudent(i);
		
	}

	@Override
	public void updateStudentWithoutMerge(Student student) {
		studentDao.updateStudentWithoutMerge(student);
		
	}

	@Override
	public void deleteAllStudentsLessThanThree(int i) {
		studentDao.deleteAllStudentsLessThanThree(i);
		
	}

}
