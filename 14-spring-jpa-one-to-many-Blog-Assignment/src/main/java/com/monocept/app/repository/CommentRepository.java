package com.monocept.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.app.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
