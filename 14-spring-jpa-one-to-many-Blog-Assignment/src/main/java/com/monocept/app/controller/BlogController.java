package com.monocept.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.app.dto.BlogDTO;
import com.monocept.app.dto.CommentDTO;
import com.monocept.app.entity.Blog;
import com.monocept.app.entity.Comment;
import com.monocept.app.service.BlogService;
import com.monocept.app.util.PagedResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class BlogController {

	private BlogService blogService;

	public BlogController(BlogService blogService) {
		super();
		this.blogService = blogService;
	}

	@GetMapping()
	public ResponseEntity<PagedResponse<BlogDTO>> getAllBlogs(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "sort", defaultValue = "id") String sortBy,
			@RequestParam(name = "direction", defaultValue = "id") String direction) {
		System.out.println(page);
		PagedResponse<BlogDTO> blogs = blogService.getAllBlogs(page, size, sortBy, direction);

		return new ResponseEntity<PagedResponse<BlogDTO>>(blogs, HttpStatus.OK);

	}

	@PostMapping()
	public ResponseEntity<BlogDTO> createABlog(@Valid @RequestBody BlogDTO blogDTO) {
		return new ResponseEntity<BlogDTO>(blogService.addAndUpdateBlog(blogDTO), HttpStatus.ACCEPTED);

	}

//	@GetMapping("getcomments")
//	public ResponseEntity<PagedResponse<CommentDTO>> getAllComments(
//			@RequestParam(name = "page", defaultValue = "0") int page,
//			@RequestParam(name = "size", defaultValue = "5") int size,
//			@RequestParam(name = "sort", defaultValue = "id") String sortBy,
//			@RequestParam(name = "direction", defaultValue = "id") String direction) {
//		System.out.println(page);
//		PagedResponse<CommentDTO> comments = blogService.getAllComments(page, size, sortBy, direction);
//
//		return new ResponseEntity<PagedResponse<CommentDTO>>(comments, HttpStatus.OK);
//
//	}

	@GetMapping("/{id}")
	public ResponseEntity<BlogDTO> getBlogId(@PathVariable(name = "id") int id) {
		BlogDTO blog = blogService.getBlogById(id);
		return new ResponseEntity<BlogDTO>(blog, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<BlogDTO> updateBlog(@Valid @RequestBody BlogDTO blogDto) {

		BlogDTO updateBlog = blogService.addAndUpdateBlog(blogDto);
		return new ResponseEntity<BlogDTO>(updateBlog, HttpStatus.OK);
	}

	@PutMapping("/{blog_id}")
	public ResponseEntity<BlogDTO> addCommentToBlog(@Valid @RequestBody CommentDTO commentDto,
			@PathVariable(name = "blog_id") int id) {
		BlogDTO message = blogService.addCommentToBlog(id, commentDto);
		return new ResponseEntity<BlogDTO>(message, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBlog(@PathVariable(name = "id") int id) {
		String message = blogService.deleteBlog(id);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@DeleteMapping("remove/{blog_id}/{comment_id}")
	public ResponseEntity<BlogDTO> removeComment(@PathVariable(name = "blog_id") int blog_id,
			@PathVariable(name = "comment_id") int comment_id) {
		BlogDTO message = blogService.removeComment(blog_id, comment_id);
		return new ResponseEntity<BlogDTO>(message, HttpStatus.OK);
	}

}
