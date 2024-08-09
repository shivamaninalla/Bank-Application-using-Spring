package com.monocept.hibernate.dao;

import java.util.List;

import com.monocept.hibernate.entity.Student;

public interface StudentDao {
	
	public void save(Student student);

	public List<Student> getAllStudents();

	public List<String> getFirstNames();

	//public List<Tuple> getIdMail();

	public Student getStudentById(int i);

	public List<Student> getStudentByFirstName(String string);

	

	public List<Student> getStudentsByFirstNameLastName(String string, String string2);

	public void updateStudent(Student student);

	public void deleteStudent(int i);

	public void updateStudentWithoutMerge(Student student);

	public void deleteAllStudentsLessThanThree(int i);

}
