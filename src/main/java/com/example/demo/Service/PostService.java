package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Post;
import com.example.demo.Repository.PostRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	
	//페이징 계산 수
	private final	int PAGE_SIZE = 10;
	/**
	 * 입력: 특정 페이지 번호
	 * 결과: 해당페이지에 속한 게시물 데이터(DTO 제공, 페이징 객체를 활용 제공)
	 * 
	 */
	public Page<Post> getList(int page) {
		//내부적으로 특정 위치에서 특정 개수 만큼 jpa를 통해 데이터를  획득하여 Pageable 형태로 반환
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		return postRepository.findAll( pageable );
	}

	public Page<Post> getList2(int page, String keyword) {
		List<Sort.Order>sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		//내부적으로 특정 위치에서 특정 개수 만큼 jpa를 통해 데이터를  획득하여 Pageable 형태로 반환
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by( sorts));
		
		Specification<Post> sf = complexSearch( keyword);
		return postRepository.findAll( sf, pageable );
	}
	
	/**
	 * 
	 * @param keyword : 검색어
	 * @return : 복잡한 쿼리문을 대변하는 Specification<NaverReviews> 객체
	 */
	private Specification<Post> complexSearch( String keyword ) {
		return new Specification<>() {
			
			/**
			 * 
			 * @param root : 기준이 되는 엔티티 객체
			 * @param query
			 * @param criteriaBuilder
			 * @return
			 */
			@Override
			public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 중복제거 => distinct
				query.distinct(true);
				
				// 조인이 필요하면, NaverReviews와 ChatbotSheet간 left 조인 수행, 
				// left 조인 결과 : label 컬럼값이 일치하는 ChatbotSheet 데이터 + NaverReviews 모든 데이터
//				Join<NaverReviews, ChatbotSheet> r2 = root.join("label", JoinType.LEFT);
				
				// 해당 컬럼에 검색어가 존재만 하면 다 가져온다
				// like => %검색어, 검색어%, %검색어%
				return criteriaBuilder.like(root.get("subject"), "%" + keyword + "%")
						;
//				return criteriaBuilder.or(
//							criteriaBuilsder.like(root.get("document"), "%" + keyword + "%"),
//							criteriaBuilder.like(r2.get("question"), "%" + keyword + "%"),
//							criteriaBuilder.like(r2.get("answer"), "%" + keyword + "%")
//						);
			}

			 
			
		};
	}
	
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
	
	public void create(String subject, String content) {
		// Post 엔티티 생성
		Post p = new Post();
		// Post 엔티티에 데이터 세팅
		p.setSubject(subject);
		p.setContent(content);
		p.setCreateDate(LocalDateTime.now());
		// PostRepository에 save() => 디비에 Post 테이블에 row데이터 1개 저장
		this.postRepository.save(p);
    }
	
	//post 수정하기 기능
	public void modify(Post post) {
		this.postRepository.save(post);
	}

	public void delete(Post post) {
		this.postRepository.delete(post);
		
	}
	
}
