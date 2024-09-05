package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Post;
 

public interface PostRepository extends JpaRepository<Post, Integer>{
		Page<Post> findAll(Pageable pageable);
		
		Page<Post> findAll(Specification<Post> sf, Pageable pageable);
		
		 Post findBySubject(String subject); // 제목으로 게시글 찾기
		//Optional<Post> findBySubject(String subject); // 제목으로 게시글 찾기
	
}
