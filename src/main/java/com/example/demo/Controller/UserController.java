package com.example.demo.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Dto.UserForm;
import com.example.demo.Service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping ("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
	//DI
	private final UserService userService;
	
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
		//3. 정상적으로 입력됐는지 체크, 됐다면 DB 입력(JPA의 회원 관련 서비스)
		// 가입될 필요한 데이터 : 회원 아이디, 비밀번호 1개 , 이메일
		// 서비스 사용 => DI (UserService)
		//아이디 중복 처리 포함 코드
		try {
			//DB 엑세스하는 코드 => try 처리한다. => I/O는 잠재적으로 무조건 오류발생 소지가 있다.
			this.userService.create(userForm.getUsername(),
												userForm.getPassword1(), userForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			// username 혹은 email 중에서 하나라도 중복이 나면 발생
			bindingResult.reject("signupError", "이미 사용중인 아이디나 이메일입니다.");
			return "signup_form";
		} catch (Exception e) {
			// 일반적은 모든 오류를 잡는 구간
			e.printStackTrace();
			bindingResult.reject("signupError", e.getMessage());
			return "signup_form";
		}
		
		//4. 정상적으로 회원 가입후 이동될 페이지 : 리다이렉트
		//향후 이동-> 로그인 화면 (단, 자동 로그인 X상황에서), 기타 메인 사이트 가능
		return "redirect:/";
	}
 
	
}
