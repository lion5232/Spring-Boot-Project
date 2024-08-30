package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Dto.UserForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping ("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/signup")
	public String signup(UserForm userForm) {
		return "signup_form"; 

	}
	/**
	 * 회원 가입 실제 처리
	 */
	@PostMapping("/signup")
	public String signup(@Valid UserForm userForm, BindingResult bindingResult) {
		//1. 입력폼 오류처리
		if(bindingResult.hasErrors() ) {
			
			return "signup_form";
		}
		//2. 2개의 패스워드가 일치하는가?
		if(!userForm.getPassword1().equals(userForm.getPassword2())) {
			// 커스텀 오류메시지를 구성해서 bindingResult를 통해서 클라이언트에게 전달
			// 메시지들은 차후 공통적으로 관리 필요 => 소스 밖에서 관리
			bindingResult.rejectValue("password2", "passwordInCorrect","패스워드가 서로 일치하지 않습니다.");
			return "signup_form";
		}
		
		return "signup_form";
	}
 
	
}
