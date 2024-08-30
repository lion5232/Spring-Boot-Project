package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //의존성 주입 생성자를 통해서...
@Service
public class UserService {
	//DI
	private final UserRepository userRepository;
	
	// 회원가입처리
	
}
