package com.example.demo;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Entity.Post;
import com.example.demo.Repository.PostRepository;

@SpringBootTest
public class ApplicationTest {

	
		@Autowired
		private PostRepository postRepository;
		
		@Test
		void testJpa() {
			Post p1 = new Post();
			p1.setSubject("sbb가 무엇인가요?");
			p1.setContent("sbb에 대해서 알고 싶습니다.");
			p1.setCreateDate(LocalDateTime.now());
			this.postRepository.save(p1);
			
			Post p2 = new Post();
			p2.setSubject("스프링 부트 모델 질문입니다.");
			p2.setContent("id는 자동으로 생성되나요?");
			p2.setCreateDate(LocalDateTime.now());
			this.postRepository.save(p2);
			
		}
}
