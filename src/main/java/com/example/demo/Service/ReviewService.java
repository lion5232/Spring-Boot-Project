package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Post;
import com.example.demo.Entity.Review;
import com.example.demo.Repository.ReviewRepository;

@Service
public class ReviewService {
	// 리뷰 레파지토리 DI -> @Autowired, Setter, constructor
	@Autowired
	private ReviewRepository reviewRepository;
	
	public void create(Post post, String content) {
		// Review 엔티티 생성
		Review review = new Review();
		// Review 엔티티에 데이터 세팅
		review.setContent(content);
		review.setCreateDate(LocalDateTime.now());
	    review.setPost(post);
	    
	    //리뷰 엔티티 저장 -> insert SQL 작동
	    this.reviewRepository.save(review);
    }

	public Review selectOneReview(Integer id) {
		Optional<Review> oReview = this.reviewRepository.findById(id);
		if(oReview.isPresent()) {
			return oReview.get();
		}
		//커스텀 예외 상황
		throw new DataNotFoundException("Review not Found");
	}

	public void delete(Review review) {
		this.reviewRepository.delete(review);
	}
	
	public void modify(Review review) {
		this.reviewRepository.save(review);
	}
}
