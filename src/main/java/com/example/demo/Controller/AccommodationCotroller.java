package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Post;
import com.example.demo.Service.PostService;

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
	
	@GetMapping("/list")
	public String list (Model model) {
		List<Post> post = this.postService.getAllPost();
		model.addAttribute("post", post);
		return "list";
	}
	
//	@GetMapping(value = "/post/detail/{id}")
//	public String detail(Model model, @PathVariable("id") Integer id) {
//		return "post_detail";
//	}
	
	@GetMapping("/detail/{id}")
	  public String detail(Model model, @PathVariable("id") Integer id) {
		 Post p = this.postService.getOnePost(id);
		 model.addAttribute("post", p);
	      return "post_detail";
	  } 
	  @PostMapping("/create/{id}")
	    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
	        Post post = this.postService.getOnePost(id);
	        // 답변을 저장한다.
	        this.postService.create(post, content);
	        
	        return String.format("redirect:/detail/%s", id);
	    }
	
	  //Post 목록 글쓰기
	  @GetMapping("/create") // 글작성 넘기기
	  public String create() {
		  return "post_form";
	  }
	  @PostMapping("/create") //글 작성 db로 넘겨 처리
	  public String create(@RequestParam(value="subject") String subject,
			  							@RequestParam(value="content") String content) {
		  //post새글 ->db저장
		  //post목록으로 이동
		  return "redirect:/Acco/list";
	  }
	  @GetMapping("/modify/{id}") //post 글 수정
	  public String modify(Model model, @PathVariable("id") Integer id) {
		  
		  return "post_form";
	  }	 
	  @PostMapping("/modify/{id}") //post 글 수정
	  public String modify(Model model, @PathVariable("id") Integer id,
			  							 @RequestParam(value="subject") String subject,
			  							 @RequestParam(value="content") String content) {
		  
		  return "redirect:/Acco/list";
	  }
	  // 글 삭제, 보안적으로 중요하면 post변경
	  @GetMapping("/delete/{id}")
	  public String delete(@PathVariable("id") Integer id) {
		  return "redirect:/Acco/list";
	  }
	
}
