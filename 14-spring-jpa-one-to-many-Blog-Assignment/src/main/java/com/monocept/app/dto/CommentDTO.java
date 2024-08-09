package com.monocept.app.dto;

import jakarta.validation.constraints.NotBlank;
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

public class CommentDTO {

    private int id;

    @NotBlank(message = "Please enter the description")
    @Size(min = 2, max = 50, message = "Please check the size of description")
    private String description;

    private int blogId; 

  

}
