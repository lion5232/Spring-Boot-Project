package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Entity.Post;
import com.example.demo.Repository.PostRepository;
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
	
	@GetMapping("/post/detail/{id}")
	  public String detail(Model model, @PathVariable("id") Integer id) {
		 Post p = this.postService.getOnePost(id);
		 model.addAttribute("post", p);
	      return "post_detail";
	  }
	
}
