package com.example.demo.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.SnsUser;
import com.example.demo.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //의존성 주입 생성자를 통해서...
@Service
public class UserService {
	//DI
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	// 회원가입처리
	//입력: 사용자ID, 비밀번호, 이메일
	public void create(String username, String password, String email) {
		//DB에 데이터 저장
		//엔티티 생성 (user)
		SnsUser user = new SnsUser();
		//데이터 엔티티 세팅
		user.setUsername(username);
		// 암호화 모듈을 가져와서 원재료(사용자 암호) 입력 => 암호화된 결과값을 세팅
		// 암호화 모듈 => 어플리케이션 상에서 유일한 객체(싱글톤) => Bean으로 구성해라 => DI 처리해라. 항상 똑같은 얘를 쓴다.
		//user.setPassword(password); //암호화 안한 버전
		user.setPassword(this.passwordEncoder.encode(password) );
		user.setEmail(email);
		//save
		this.userRepository.save(user);
	}
	
}
