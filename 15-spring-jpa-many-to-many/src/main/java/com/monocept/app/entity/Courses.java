package com.monocept.app.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String title;

    
    @ManyToMany(mappedBy = "courses")
   // @JsonBackReference
    private Set<Students> students = new HashSet<>();
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

//    public void addS(Students student) {
//        if (!students.contains(student)) {
//            students.add(student);
//            student.getCourses().add(this);
//        }
//    }
//    public void remove(Students student) {
//		if (students.contains(student)) {
//            students.remove(student);
//            student.getCourses().remove(this);
//        }
//		
//	}
}
