package com.monocept.hibernate;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.monocept.hibernate.dao.StudentDao;
import com.monocept.hibernate.entity.Student;

import jakarta.persistence.Tuple;

@SpringBootApplication
public class Application implements CommandLineRunner{

	private StudentDao studentDao;
	
	public Application(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//addStudent();
	//	getAllStudents();
	//	getFirstNames();
		//getIdMail();
	//	getStudentById();
	//	getStudentByFirstName();
	//	getStudentsByFirstNameAndLastName();
	//	updateStudent();
	//	updateStudentWithoutMerge();
	//	deleteStudent();
		deleteAllStudentsLessThanThree();
	}

	private void deleteAllStudentsLessThanThree() {
		studentDao.deleteAllStudentsLessThanThree(3);
		
	}

	private void updateStudentWithoutMerge() {
		Student student=new Student(1,"sai@gmail.com","sai","nalla");
		studentDao.updateStudentWithoutMerge(student);
		
	}

	private void deleteStudent() {
		studentDao.deleteStudent(2);
		
	}

	private void updateStudent() {
		Student student=new Student(1,"shivamani@gmail.com","shivamani","hero");
		studentDao.updateStudent(student);
		
	}

	private void getStudentsByFirstNameAndLastName() {
		List<Student> student=studentDao.getStudentsByFirstNameLastName("shivamani","nalla");
		if(student!=null) {
			for(Student name:student) {
				System.out.println(name);
			}
		}
		else {
			System.out.println("Student not found");
		}
		
	}

	private void getStudentByFirstName() {
		List<Student> student=studentDao.getStudentByFirstName("shivamani");
		for(Student first_name:student) {
			System.out.println(first_name);
		}
		
	}

	private void getStudentById() {
		Student student=studentDao.getStudentById(25);
		if(student!=null) {
			System.out.println(student);
		}
		else {
			System.out.println("Student not found");
		}
		
		
	}

	private void getFirstNames() {
		List<String> f=studentDao.getFirstNames();
		for(String first_name:f) {
			System.out.println(first_name);
		}
	}
	
//	private void getIdMail() {
//		List<Tuple> f=studentDao.getIdMail();
//		for(Student idmail:f) {
//			System.out.println(idmail);
//		}
//	}
	

	private void getAllStudents() {
		List<Student> studentList=studentDao.getAllStudents();
		for(Student s: studentList) {
			System.out.println(s);
		}
		
	}
	

	private void addStudent() {
		Student student=new Student( "cherry@gmail.com","cherry","nalla");
		studentDao.save(student);
	}

}
