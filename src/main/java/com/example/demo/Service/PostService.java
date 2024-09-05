package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Image;
import com.example.demo.Entity.Post;
import com.example.demo.Repository.ImageRepository;
import com.example.demo.Repository.PostRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	private final ImageRepository imageRepository;
	
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

	// post 검색 기능
	public Page<Post> getList2(int page, String keyword) {
		List<Sort.Order>sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		//내부적으로 특정 위치에서 특정 개수 만큼 jpa를 통해 데이터를  획득하여 Pageable 형태로 반환
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by( sorts));
		
		Specification<Post> sf = complexSearch( keyword);
		return postRepository.findAll( sf, pageable );
	}
	
	/**
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
				// 해당 컬럼에 검색어가 존재만 하면 다 가져온다
				// like => %검색어, 검색어%, %검색어%
				return criteriaBuilder.like(root.get("subject"), "%" + keyword + "%");
			}
		};
	}
	
	//post의 모든 요소 가져오기
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
	
	// post create 기능
//	public void create(String subject, String content , List<String> filePaths) {
////		// Post 엔티티 생성
////		Post post = new Post();
////		// Post 엔티티에 데이터 세팅
////		post.setSubject(subject);
////		post.setContent(content);
//		
//		//파일 경로 저장
//		for(String filePath: filePaths) {
//				saveFilePath(post.getId(), filePath);
//		}
//		post.setCreateDate(LocalDateTime.now());
//		// PostRepository에 save() => 디비에 Post 테이블에 row데이터 1개 저장
//		this.postRepository.save(post);
//    }
	
	@Transactional
	public void create(Post post, List<String> filePaths) {
	    // Post 엔티티 저장
	    Post savedPost = this.postRepository.save(post); 
	    // 파일 경로 저장
	    for (String filePath : filePaths) {
	        saveFilePath(savedPost.getId(), filePath);
	    }
	}
	
	//post 수정하기 기능
	public void modify(Post post) {
		this.postRepository.save(post);
	}

	//post 삭제하기 기능
	public void delete(Post post) {
		this.postRepository.delete(post);
	}
	
//	 // 파일 경로를 데이터베이스에 저장하는 로직
//	 @Transactional
//	public void saveFilePaths(String subject, List<String> filePaths) {
//		 //Optional<Post> post = postRepository.findBySubject(subject);
//		 Post post = postRepository.findBySubject(subject);
//		 if (post != null) {
//                // 파일 경로를 게시글에 추가 (예: List<String> imagePaths 필드가 있다고 가정)
//            	//post.setFilePaths(filePaths);
//	            postRepository.save(post); // 수정된 게시글 저장
//	        }
//		 }

	
	 // 이미지 업로드 및 경로 저장 
    public void saveFilePath(int postId, String filePath) {

        Image imagePath = new Image(); 
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found")); // 게시글이 없을 경우 예외 처리
        imagePath.setPost(post); // Post 객체를 설정
        imagePath.setFilePath("/pictures/"+filePath);
        imageRepository.save(imagePath);
     }
    
 
 


}
	
	
//	public void savefilePath(String subject, String fileName) {
//		 // 제목으로 게시글을 찾기
//		 Optional<Post> optionalPost = postRepository.findBySubject(subject);
//		    if (optionalPost.isPresent()) {
//		        Post post = optionalPost.get();
//		        // 파일 경로 설정
//		        post.setFilePath(fileName);
//		        // 수정된 게시글 저장
//		        postRepository.save(post);
//		    } else {
//		        throw new DataNotFoundException("Post not found with subject: " + subject);
//		    }
//	}

	
	
