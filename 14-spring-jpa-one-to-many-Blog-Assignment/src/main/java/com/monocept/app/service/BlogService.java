package com.monocept.app.service;

import com.monocept.app.dto.BlogDTO;
import com.monocept.app.dto.CommentDTO;
import com.monocept.app.entity.Blog;
import com.monocept.app.entity.Comment;
import com.monocept.app.util.PagedResponse;

import jakarta.validation.Valid;

public interface BlogService {

	Blog addBlog(@Valid Blog blog);

	PagedResponse<BlogDTO> getAllBlogs(int page, int size, String sortBy, String direction);

	Comment addComment(@Valid Comment comment);

	PagedResponse<CommentDTO> getAllComments(int page, int size, String sortBy, String direction);

	BlogDTO getBlogById(int id);


	BlogDTO updateBlog(@Valid BlogDTO blogDto);

	BlogDTO addCommentToBlog(int blogId, @Valid CommentDTO commentDto);

	

	BlogDTO addAndUpdateBlog(@Valid BlogDTO blogDto);

	String deleteBlog(int blogId);

	BlogDTO removeComment(int blog_id, int comment_id);

}
