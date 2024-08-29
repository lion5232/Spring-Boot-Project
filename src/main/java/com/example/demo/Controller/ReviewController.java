package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.ReviewForm;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.Review;
import com.example.demo.Service.PostService;
import com.example.demo.Service.ReviewService;

 
import jakarta.validation.Valid;

@RequestMapping("/review")
@Controller
public class ReviewController {
		@Autowired
		private ReviewService reviewService;
	   @Autowired
	    private PostService postService;
	   /**
	     * 리뷰 생성 함수
	     * @param id
	     * @param content
	     * @return
	     */
	    // 리뷰 작성
	   //#1-2 : /review/create/{id}를 매핑하는 메소드구현
	   @PostMapping("/create/{id}")
	    public String create(Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult,
	    		@PathVariable("id") Integer id,  @RequestParam(value="content") String content) {
	        Post post = this.postService.getOnePost(id);
	        // 오류 검사 추가
	        if(bindingResult.hasErrors()) {
	        	model.addAttribute("post",post);
	        	return "post_detail" ;
	        }
	        // TODO #1-3 : 서비스를 통해서 Review 엔티티 생성하여 디비에 저장 (서비스 생성, 레퍼지토리사용, 엔티티사용)
	        // 1-3 구현, 실습 2분
	        // 1. ReviewService 의존성 주입 -> 맴버 변수 자리에서 진행
	        // 2. Post 엔티티 객체 획득
	        // 3. ReviewService.create(Post객체, content) 함수 호출
	        this.reviewService.create(post , reviewForm.getContent());

	        // TODO #1-4 : ~/post/detail/{id} 페이지를 요청한다 -> 서버에서 요청 : 리다이렉트
	    return "redirect:/Acco/detail/" + id;
	    }
	   
	   // 리뷰 삭제, 보안적으로 중요하면 post변경
		  @GetMapping("/delete/{id}")
		  public String delete(@PathVariable("id") Integer id) {
			  //리뷰 ID => 리뷰 엔티티 획득
			  Review review = this.reviewService.selectOneReview(id);
			  this.reviewService.delete(review);
			  return "redirect:/Acco/detail/" + review.getPost().getId();
		  }
		  
		  // 리뷰수정
		  @GetMapping("/modify/{id}") 
		  public String modify(ReviewForm reviewForm, @PathVariable("id") Integer id) {
			  Review review = this.reviewService.selectOneReview(id);
			  reviewForm.setContent(review.getContent());
			  return "review_form";
		  }
		  
		  @PostMapping("/modify/{id}")
		  public String modify( @Valid ReviewForm reviewForm, BindingResult bindingResult  ,
				  							  @PathVariable("id") Integer id ) { 
				 // 검증 결과 문제 존재하는가?
			  if(bindingResult.hasErrors() ) {
				  //더이상 진행하지 않고, 입력화면으로 이동, postForm 객체, bindingResult에러는 타임리프로 전달 랜더링 
				  return "review_form";
			  }
			 Review review= this.reviewService.selectOneReview(id); // 원본 post데이터 획득(엔티티)
			 review.setContent(reviewForm.getContent());
			 this.reviewService.modify(review);
			  return "redirect:/Acco/detail/" + review.getPost().getId();
		  }

}
