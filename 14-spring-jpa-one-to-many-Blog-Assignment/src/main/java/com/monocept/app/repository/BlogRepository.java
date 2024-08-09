package com.monocept.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.app.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

}
