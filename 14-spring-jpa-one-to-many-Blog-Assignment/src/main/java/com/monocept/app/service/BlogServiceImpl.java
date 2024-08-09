package com.monocept.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.monocept.app.dto.BlogDTO;
import com.monocept.app.dto.CommentDTO;
import com.monocept.app.entity.Blog;
import com.monocept.app.entity.Comment;
import com.monocept.app.exception.BlogNotFoundException;
import com.monocept.app.exception.CommentNotFoundException;
import com.monocept.app.repository.BlogRepository;
import com.monocept.app.repository.CommentRepository;
import com.monocept.app.util.PagedResponse;

import jakarta.validation.Valid;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    public BlogServiceImpl(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    

    @Override
    public BlogDTO getBlogById(int id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            throw new BlogNotFoundException("Blog not found with the id: " + id);
        }
        return convertBlogToBlogDTO(blog);
    }

    @Override
    public PagedResponse<BlogDTO> getAllBlogs(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Blog> blogPage = blogRepository.findAll(pageable);
        List<BlogDTO> blogDTOs = convertBlogToBlogDTO(blogPage.getContent());
        return new PagedResponse<>(blogDTOs, blogPage.getNumber(), blogPage.getSize(), blogPage.getTotalElements(), blogPage.getTotalPages(), blogPage.isLast());
    }

    @Override
    public Comment addComment(@Valid Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public PagedResponse<CommentDTO> getAllComments(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Comment> pages = commentRepository.findAll(pageable);
        List<CommentDTO> commentDTOs = convertCommentToCommentDTO(pages.getContent());
        return new PagedResponse<>(commentDTOs, pages.getNumber(), pages.getSize(), pages.getTotalElements(), pages.getTotalPages(), pages.isLast());
    }

    // Conversion Methods

    private BlogDTO convertBlogToBlogDTO(Blog blog) {
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setCategory(blog.getCategory());
        blogDTO.setData(blog.getData());
        blogDTO.setPublishedDate(blog.getPublishedDate());
        blogDTO.setPublished(blog.isPublished());
        blogDTO.setComment(convertCommentToCommentDTO(blog.getComment()));
        return blogDTO;
    }

    private List<BlogDTO> convertBlogToBlogDTO(List<Blog> blogs) {
        List<BlogDTO> blogDTOs = new ArrayList<>();
        for (Blog blog : blogs) {
            blogDTOs.add(convertBlogToBlogDTO(blog));
        }
        return blogDTOs;
    }

    private Blog convertBlogDTO_to_Blog(BlogDTO blogDTO) {
        Blog blog = new Blog();
        blog.setId(blogDTO.getId());
        blog.setTitle(blogDTO.getTitle());
        blog.setCategory(blogDTO.getCategory());
        blog.setData(blogDTO.getData());
        blog.setPublishedDate(blogDTO.getPublishedDate());
        blog.setPublished(blogDTO.isPublished());
        blog.setComment(convertDTOToComment(blogDTO.getComment(), blog)); // Set comments with the blog reference
        return blog;
    }

    private List<CommentDTO> convertCommentToCommentDTO(List<Comment> comments) {
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setDescription(comment.getDescription());
            commentDTO.setBlogId(comment.getBlog().getId()); // Set the blog ID in DTO
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    private List<Comment> convertDTOToComment(List<CommentDTO> commentDTOs, Blog blog) {
        List<Comment> comments = new ArrayList<>();
        for (CommentDTO commentDTO : commentDTOs) {
            Comment comment = new Comment();
            comment.setId(commentDTO.getId());
            comment.setDescription(commentDTO.getDescription());
            comment.setBlog(blog); // Set the blog reference
            comments.add(comment);
        }
        return comments;
    }

	@Override
	public Blog addBlog(@Valid Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public BlogDTO updateBlog(@Valid BlogDTO blogDto) {
		 Blog blog = convertBlogDTO_to_Blog(blogDto);
		 int id = blog.getId();
		 if (id>0) {
	            
	            Blog existingBlog = blogRepository.findById(blog.getId()).orElse(null);
	            if (existingBlog != null) {
	                existingBlog.setTitle(blog.getTitle());
	                existingBlog.setCategory(blog.getCategory());
	                existingBlog.setData(blog.getData());
	                existingBlog.setPublishedDate(blog.getPublishedDate());
	                existingBlog.setPublished(blog.isPublished());
	                existingBlog.setComment(convertDTOToComment(blogDto.getComment(), existingBlog)); // Update comments
	                return convertBlogToBlogDTO(blogRepository.save(existingBlog));
	            }
	            
	        }
		 throw new BlogNotFoundException("Blog not found with the id: " + blogDto.getId());
	}



//	@Override
//	public BlogDTO addCommentToBlog(int blogId, int commentId) {
//		Blog blog = blogRepository.findById(blogId).orElse(null);
//	
//		if(blog!=null) {
//			Comment comment = commentRepository.findById(commentId).orElse(null);
//			if(comment!=null) {
//				if(comment.getDescription()==null) {
//					blog.addComment(comment);
//					comment.setBlog(blog);
//					blogRepository.save(blog);
//					BlogDTO blogDto = convertBlogToBlogDTO(blog);
//					return blogDto;
//				}
//				else {
//					System.out.println("Blog is already assigned to comment");
//				}
//			}
//		}
//		return null;
//	}
	public BlogDTO addCommentToBlog(int blogId, int commentId) {
	    Blog blog = blogRepository.findById(blogId)
	            .orElseThrow(() -> new BlogNotFoundException("Blog not found with the id: " + blogId));
	    Comment comment = commentRepository.findById(commentId)
	            .orElseThrow(() -> new CommentNotFoundException("Comment not found with the id: " + commentId));

	    // Set the blog reference in the comment
	    comment.setBlog(blog);

	    // Add the comment to the blog's comment list
	    blog.addComment(comment);

	    // Save the comment
	    commentRepository.save(comment);

	    // Optionally save the blog if the cascade type is not set to persist
	    // blogRepository.save(blog);

	    return convertBlogToBlogDTO(blog);
	}



	



	@Override
	public BlogDTO addAndUpdateBlog(@Valid BlogDTO blogDto) {
		
	        Blog blog = convertBlogDTO_to_Blog(blogDto);
	        if (blogDto.getId() == 0) {
	            return convertBlogToBlogDTO(blogRepository.save(blog));
	        } else {
	            Blog existingBlog = blogRepository.findById(blogDto.getId()).orElse(null);

	            if (existingBlog != null) {
	                existingBlog.setTitle(blog.getTitle());
	                existingBlog.setCategory(blog.getCategory());
	                existingBlog.setData(blog.getData());
	                existingBlog.setPublishedDate(blog.getPublishedDate());
	                existingBlog.setPublished(blog.isPublished());

	               
	                if (existingBlog.getComment() != null) {
	                    existingBlog.getComment().clear();
	                    if (blog.getComment() != null) {
	                        for (Comment comment : blog.getComment()) {
	                            comment.setBlog(existingBlog); 
	                            existingBlog.getComment().add(comment);
	                        }
	                    }
	                } else {
	                    existingBlog.setComment(blog.getComment());
	                }

	                return convertBlogToBlogDTO(blogRepository.save(existingBlog));
	            } else {
	                throw new BlogNotFoundException("Blog not found with id: " + blogDto.getId());
	            }
	    
	}
	}



	@Override
	public String deleteBlog(int blogId) {
		Blog blog = blogRepository.findById(blogId).orElse(null);
		if(blog==null) {
            throw new BlogNotFoundException("Blog not found with id: " + blogId);
		}
		blogRepository.deleteById(blogId);
		return "Deleted suuccesfully";
		
	}



	@Override
	public BlogDTO addCommentToBlog(int blogId, @Valid CommentDTO commentDto) {
		Blog blog = blogRepository.findById(blogId).orElse(null);
		if(blog!=null) {
			Comment comment=convertCommentDtoToComment(commentDto,blog);
			blog.addComment(comment);
			Blog save = blogRepository.save(blog);
			return convertBlogToBlogDTO(save);
		}
        throw new BlogNotFoundException("Blog not found with id: " + blogId);
	}



	private Comment convertCommentDtoToComment(@Valid CommentDTO commentDto, Blog blog) {
		Comment comment=new Comment();
		comment.setId(commentDto.getId());
		comment.setDescription(commentDto.getDescription());
		comment.setBlog(blog);
		return comment;
	}



	@Override
	public BlogDTO removeComment(int blog_id, int comment_id) {
		Blog blog = blogRepository.findById(blog_id).orElse(null);
		if(blog!=null) {
			Comment comment = commentRepository.findById(comment_id).orElse(null);
			if(comment!=null) {
				blog.removeComment(comment);
				Blog blog2 = blogRepository.save(blog);
				return convertBlogToBlogDTO(blog2);
			}
	        throw new CommentNotFoundException("Blog not found with id: " + comment_id);

		}
        throw new BlogNotFoundException("Blog not found with id: " + blog_id);

		
	}
	
	
	
	


	
}
