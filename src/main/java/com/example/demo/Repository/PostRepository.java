package com.example.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
		Page<Post> findAll(Pageable pageable);
	
}
