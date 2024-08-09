package com.monocept.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BlogDTO {
	
	
	
	
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
	
	private List<CommentDTO> comment;

	
	
	
	

}
