package com.example.demo.Controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Dto.PostForm;
import com.example.demo.Dto.ReviewForm;
import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor

@Controller
@RequestMapping("/Acco")
public class AccommodationCotroller {
	
	private final PostService postService;
	
//	@GetMapping("/list")
//	public String Acco() {
//		return "list";
//	}
//	
////	@PostMapping("/list")
////	public String list() {
////		return "Acco_list";
////	}
	
	// ~/Acco/list?page=1 이런 방식으로 호출
//	@GetMapping("/list2")
//	public String list (Model model , @RequestParam(value="page", defaultValue = "0") int page) {
//		List<Post> post = this.postService.getAllPost();
//		model.addAttribute("post", post);
//		Page<Post> paging = this.postService.getList(page);
//		model.addAttribute("paging", paging);
//		return "list";
//	}
	
	//검색어 획득, 검색 작업 실제 진행(서비스-레퍼지토리 처리), 타임리프 전달내용(검색어, 페이징 번호)
	@GetMapping("/list")
	public String list2(Model model , @RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="keyword", defaultValue= "") String keyword) {
		//1. 특정 페이지에 해당되는 페이징 데이터 획득
		Page<Post> paging = this.postService.getList2(page, keyword);	
		
		 
		model.addAttribute("keyword", keyword);
		model.addAttribute("paging", paging);
		
		return "list";
	}
	
//	@GetMapping(value = "/post/detail/{id}")
//	public String detail(Model model, @PathVariable("id") Integer id) {
//		return "post_detail";
//	}
	
	@GetMapping("/detail/{id}")
	  public String detail(Model model, @PathVariable("id") Integer id, 
			  //폼 객체를 인자로 전달해서 바인딩 하는것만으로도 타임리프에 전달된다.
			  ReviewForm reviewForm) {
		 Post p = this.postService.getOnePost(id);
		 model.addAttribute("post", p);
	      return "post_detail";
	  } 
//	  @PostMapping("/create/{id}")
//	    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
//	        Post post = this.postService.getOnePost(id);
//	        // 답변을 저장한다.
//	        this.postService.create(post, content);
//	        
//	        return String.format("redirect:/detail/%s", id);
//	    }
	
	  //Post 목록 글쓰기
	  @GetMapping("/create") // POST 글작성 넘기기
	 // 매개변수로 바인딩한 PostForm 객체는 Model 객체없이 html(템플릿에 전달하여 사용 가능하다)
	  public String create(PostForm postForm,  Model model) {
		  model.addAttribute("pageType", "write");
		  return "post_form";
	  }
	  @PostMapping("/create") //글 작성 db로 넘겨 처리
//	  public String create(@RequestParam(value="subject") String subject,
//			  							@RequestParam(value="content") String content) {
	  // postForm을 통해서 데이터 전달 비교 , 누락시 오류 처리 추가
	  // @Vaild :  전달된 데이터를   PostForm에 세팅되어서 검사(길이, 공백, null체크) 진행 => 스프링부트가 바인딩 처리함
	  // BindingResult : 검증된 결과를 돌려주는 =>  어딘가에 세팅되어서
	  public String create(@Valid PostForm postForm, BindingResult bindingResult  ) {
		 // 검증 결과 문제 존재하는가?
		  if(bindingResult.hasErrors() ) {
			  //더이상 진행하지 않고, 입력화면으로 이동, postForm 객체, bindingResult에러는 타임리프로 전달 랜더링 
			  return "post_form";
		  }
		  
		  // 문제 없으면 DB에 저장
		  //post새글 ->db저장
		  //this.postService.create(subject, content);
		  this.postService.create(postForm.getSubject(), postForm.getContent());
		  //post목록으로 이동
		  return "redirect:/Acco/list";
	  }
	  @GetMapping("/modify/{id}") //post 글 수정
	  public String modify(PostForm postForm, @PathVariable("id") Integer id ,Model model) {
		 Post post = this.postService.getOnePost(id);
		 postForm.setSubject(post.getSubject());
		 postForm.setContent(post.getContent()); 
		 model.addAttribute("post", post);
		 model.addAttribute("pageType", "detail");
		  return "post_form";
	  }	 
	  @PostMapping("/modify/{id}") //post 글 수정
	  public String modify( @Valid PostForm postForm, BindingResult bindingResult  ,
			  							  @PathVariable("id") Integer id ) { 
			 // 검증 결과 문제 존재하는가?
		  if(bindingResult.hasErrors() ) {
			  //더이상 진행하지 않고, 입력화면으로 이동, postForm 객체, bindingResult에러는 타임리프로 전달 랜더링 
			  return "post_form";
		  }
		 Post post= this.postService.getOnePost(id); // 원본 post데이터 획득(엔티티)
		 post.setSubject(postForm.getSubject());
		 post.setContent(postForm.getContent());
		 this.postService.modify(post);
		  return "redirect:/Acco/detail/" + id;
	  }
	  // 글 삭제, 보안적으로 중요하면 post변경
	  @GetMapping("/delete/{id}")
	  public String delete(@PathVariable("id") Integer id) {
		  
		  // 1. post 객체 획득
			 Post post= this.postService.getOnePost(id); // 원본 post데이터 획득(엔티티)
		  // 2. 삭제 -> 서비스가 담당
			 this.postService.delete(post);
			 
		  return "redirect:/Acco/list";
	  }
	
}
