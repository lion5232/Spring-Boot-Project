package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Post;
import com.example.demo.Repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	
	public List<Post> getAllPost() {
		return this.postRepository.findAll();
	}
	
	//id를 넣어서 일치하는 Post 엔티티 객체를 리턴
	public Post getOnePost(Integer id) {
		Optional<Post> oPost = this.postRepository.findById(id);
		if(oPost.isPresent()) {
			return oPost.get();
		}
		// 커스텀 예외 상황
		throw new DataNotFoundException("post not Found");
	}
	
	public void create(Post post, String content) {
		// Post 엔티티 생성
		Post p = new Post();
		// Post 엔티티에 데이터 세팅
		//p.setSubject(subject);
		p.setContent(content);
		p.setCreateDate(LocalDateTime.now());
		// PostRepository에 save() => 디비에 Post 테이블에 row데이터 1개 저장
		this.postRepository.save(p);
    }
	
}
