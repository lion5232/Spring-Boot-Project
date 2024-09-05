package com.example.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Dto.PostForm;
import com.example.demo.Dto.ReviewForm;
import com.example.demo.Entity.Image;
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

// -----------------------------------------------------------------------------------------------------------
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

// -----------------------------------------------------------------------------------------------------------
	//세부 화면으로 이동
	@GetMapping("/detail/{id}")
	  public String detail(Model model, @PathVariable("id") Integer id, 
			  //폼 객체를 인자로 전달해서 바인딩 하는것만으로도 타임리프에 전달된다.
			  ReviewForm reviewForm) {
		 Post post = this.postService.getOnePost(id);
		 model.addAttribute("post", post);
		 
 
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

// -----------------------------------------------------------------------------------------------------------
	  //Post 목록 글쓰기
	  @GetMapping("/create") // POST 글작성 넘기기
	 // 매개변수로 바인딩한 PostForm 객체는 Model 객체없이 html(템플릿에 전달하여 사용 가능하다)
	  public String create(PostForm postForm,  Model model) { 
		  model.addAttribute("pageType", "write");
		  return "post_form";
	  }
	  @PostMapping("/create") //글 작성 db로 넘겨 처리 
	  // postForm을 통해서 데이터 전달 비교 , 누락시 오류 처리 추가
	  // @Vaild :  전달된 데이터를   PostForm에 세팅되어서 검사(길이, 공백, null체크) 진행 => 스프링부트가 바인딩 처리함
	  // BindingResult : 검증된 결과를 돌려주는 =>  어딘가에 세팅되어서
	  public String create( @ModelAttribute @Valid PostForm postForm ,  BindingResult bindingResult , @RequestParam("uploadFile") List<MultipartFile> files ) {
		
		  if(bindingResult.hasErrors() ) {// 검증 결과 문제 존재하는가? 
			  //더이상 진행하지 않고, 입력화면으로 이동, postForm 객체, bindingResult에러는 타임리프로 전달 랜더링 
			  return "post_form";
		  }  
		  
		  // 문제 없으면 DB에 저장
		  //post새글 ->db저장 게시글 저장
		  //this.postService.createAndGetID(postForm.getSubject(), postForm.getContent(), new ArrayList<>()); // 빈 리스트로 초기화
		  //int post_id = this.postService.createAndGetId(post); // 빈 리스트로 초기화
		  //int post_id = this.postService.create(postForm.getSubject(), postForm.getContent(), fileNames);
		    
		  // 게시글 생성
		    Post post = new Post();
		    post.setSubject(postForm.getSubject());
		    post.setContent(postForm.getContent());
		    post.setCreateDate(LocalDateTime.now());
		    
		  // 파일 업로드 처리 
	        //String uploadDir = "C:/workspace_2024_springboot_reboot_24_08_19/Study-1/src/main/resources/static/pictures";
		    String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/pictures/";
	        System.out.println(uploadDir);
	        List<String> fileNames = new ArrayList<>(); // 저장할 파일 이름 리스트
	        
	        // 파일 업로드 루프
	        for (MultipartFile file : files) {
	            if (!file.isEmpty() && file != null) {
	                // 파일 이름 생성 (중복 방지를 위해 uuid 랜덤)
	            	UUID uuid = UUID.randomUUID();
	                String fileName = uuid + "_" + file.getOriginalFilename();
	                File uploadDirFile = new File(uploadDir);
	                
	                // 디렉토리가 없으면 생성
	                if (!uploadDirFile.exists()) {
	                    uploadDirFile.mkdirs();
	                } 
	                try {
	                    // 파일 저장
	                    File dest = new File(uploadDir, fileName);
	                    file.transferTo(dest);
	                    fileNames.add(fileName);
	                    // 파일 경로를 데이터베이스에 저장하는 로직
	                    /**
	                     *  게시글을 먼저 저장한 후, 그 게시글의 ID를 사용하여 saveFilePath를 호출해야 합니다.
	                     *  saveFilePath 메서드는 게시글 ID와 파일 경로를 인자로 받으므로, 컨트롤러에서 호출할 때는 게시글 ID를 넘겨야 합니다.
	                     */
//	                    this.postService.saveFilePath(post_id , fileName);  
	                     
	                } catch (IOException e) {
	                    e.printStackTrace(); // 예외 처리
	                    bindingResult.reject("fileUploadError", "파일 업로드 중 오류가 발생했습니다."); // 에러 메시지 추가
	                    return "redirect:/Acco/create"; // 오류 발생 시 입력 화면으로 리다이렉트
	                }
	            }
	        }   
	        
	        // 게시글 저장 및 파일 경로 저장
	        this.postService.create(post, fileNames);
		  //post목록으로 이동
		  return "redirect:/Acco/list";
	  }

// -----------------------------------------------------------------------------------------------------------	  
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
	  
	  
 // -----------------------------------------------------------------------------------------------------------
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


 
/*
 *  		  // 파일 업로드 처리 	    
		    //파일 업로드 루프
		    for (MultipartFile file1 : file) {
		        if (!file.isEmpty()) {
		            // 파일 저장 로직 (예: 로컬 파일 시스템, S3 등)
		            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); //중복 방지를 위해 파일 이름 현재시간_파일이름으로 변경
		            // 파일 저장 경로 설정 : 프로젝트 내에 저장 
		            String uploadDir =  "C:/workspace_2024_springboot_reboot_24_08_19/Study-1/src/main/resources/static/pictures";
		            // 실제 경로로 변경=> 사용자 사진 폴더로 지정
		            File uploadDirFile = new File(uploadDir);
		            if (!uploadDirFile.exists()) {
		                uploadDirFile.mkdirs(); // 디렉토리가 없으면 생성
		            }
		            try {
		                File dest = new File(uploadDir + "/" + fileName);
		                file.transferTo(dest); // 파일 저장 
		                // 파일 경로를 데이터베이스에 저장하는 로직 추가 (예: postService에 메서드 추가)
		                this.postService.savefilePath(postForm.getSubject(), fileName); // 예시 메서드 
		            } catch (IOException e) {
		                e.printStackTrace(); // 예외 처리
		                bindingResult.reject("fileUploadError", "파일 업로드 중 오류가 발생했습니다."); // 에러 메시지 추가
		                return "redirect:/Acco/create"; // 오류 발생 시 입력 화면으로 리다이렉트
		                // 에러 처리 로직 추가 (예: 에러 메시지 추가 후 입력 화면으로 리다이렉트)
		            }
		        }
		    }
 * */
