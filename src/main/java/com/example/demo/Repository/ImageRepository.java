package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	// 게시글 ID에 연결된 모든 이미지 삭제
	void deleteByPostId(Integer postId);
}
