package com.monocept.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Please enter the title")
	@Size(min = 2, max = 50, message = "please check the size of the title")
	private String title;

	@NotBlank(message = "Please enter the category")
	@Size(min = 2, max = 50, message = "please check the size of category")
	private String category;

	@NotBlank(message = "Please enter the data")
	@Size(min = 2, max = 50, message = "please check the size of data")
	private String data;

	@NotNull(message = "Please enter the published date")
	@FutureOrPresent(message = "The published date must be in the present or future")
	private LocalDateTime publishedDate=LocalDateTime.now();

	private boolean published;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "blog")
	@JsonManagedReference
	private List<Comment> comment;






	public void addComment(Comment comment2) {
		comment.add(comment2);
		
	}

	public void removeComment(Comment comment2) {
		if(comment.contains(comment2)) {
			comment.remove(comment2);
		}
		
	}
	
	
	


	
	
	

}
