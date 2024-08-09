package com.monocept.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.monocept.app.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao{
	
	EntityManager entitymanager;
	

	public StudentDaoImpl(EntityManager entitymanager) {
		super();
		this.entitymanager = entitymanager;
	}


	@Override
	@Transactional
	public Student save(Student student) {
		
		return this.entitymanager.merge(student);
	}


	@Override
	public List<Student> getAllStudents() {
		//TypedQuery<Student> query = entitymanager.createQuery("select s from Student s",Student.class);
		String sql = "SELECT id, first_name, last_name, email FROM Student"; // Include all fields mapped to Student entity
	    Query query = entitymanager.createNativeQuery(sql, Student.class);
	    List<Student> studentList = query.getResultList();
		//List<Student> studentList = query.getResultList();
		return studentList;
	}


	@Override
	public List<String> getFirstNames() {
//		String sql="select first_name from student";
//		Query nativeQuery = entitymanager.createNativeQuery(sql,String.class);
//		List<String> list=nativeQuery.getResultList();
		TypedQuery<String> query = entitymanager.createQuery("select s.first_name from Student s",String.class);
		List<String> resultList = query.getResultList();
		return resultList;
	}


	@Override
	public Student getStudentById(int id) {
		Student student = entitymanager.find(Student.class, id);
		return student;
	}


	@Override
	public List<Student> getStudentByFirstName(String first_name) {
		TypedQuery<Student> query = entitymanager.createQuery("select s from Student s where first_name=:first",Student.class);
		query.setParameter("first", first_name);
		
		return query.getResultList();
	}


	@Override
	public List<Student> getStudentsByFirstNameLastName(String first_name, String last_name) {
		TypedQuery<Student> query = entitymanager.createQuery("select s from Student s where first_name=:first and last_name=:last",Student.class);
		query.setParameter("first", first_name);
		query.setParameter("last", last_name);
		return query.getResultList();
		
	}


	@Override
	@Transactional
	public void updateStudent(Student student) {
		Student student2 = entitymanager.find(Student.class, student.getId());
		if(student2!=null) {
			this.entitymanager.merge(student);
		}
		else {
			System.out.println("student id does not found: "+student.getId());
		}
		
	}


	@Override
	@Transactional
	public void deleteStudent(int id) {
		Student student = entitymanager.find(Student.class, id);
		if(student!=null) {
			this.entitymanager.remove(student);
		}
		else {
			System.out.println("student with id does not exist: "+id);
		}
		
	}


	@Override
	@Transactional
	public void updateStudentWithoutMerge(Student student) {
	    Query query = entitymanager.createQuery("update Student s set s.first_name=?1,s.last_name=?2,s.email=?3");
		query.setParameter(1, student.getFirst_name());
		query.setParameter(2, student.getLast_name());
		query.setParameter(3, student.getEmail());
		int res = query.executeUpdate();
		System.out.println(res);
	}


	@Override
	@Transactional
	public void deleteAllStudentsLessThanThree(int i) {
	//    Query query = entitymanager.createQuery("delete from Student s where s.id<?1");
		Query nativeQuery = entitymanager.createNativeQuery("delete from student where id<?1");
	   nativeQuery.setParameter(1, i);
	   int executeUpdate = nativeQuery.executeUpdate();
	   System.out.println(executeUpdate);
//		query.setParameter(1, i);
//	    int executeUpdate = query.executeUpdate();
	    
	    
	    

		
	}


	

//	@Override
//	public List<Tuple> getIdMail() {
//	TypedQuery<Tuple> query = entitymanager.createQuery("select s.email,s.id from Student s",Tuple.class);
//	List<Tuple> studentList = query.getResultList();
//	return studentList;
//		
//	}

}
