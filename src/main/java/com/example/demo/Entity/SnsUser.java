/**
 *  회원 정보 엔티티
 *  id -> 관리번호, 자동 증가, Long형
 *  username -> 사용자 아이디 관리, 문자열, 고유한값
 *  password -> 비밀번호, 문자열, 암호화 처리
 *  email -> 이메일, 문자열, 고유한값
 *  createDate -> 가입일, 리뷰시간에..
 */

package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SnsUser {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator3")
    @SequenceGenerator(name = "sequence_generator3", sequenceName = "sequence_name3", allocationSize = 1)
	private Long id;
	
	@Column(unique = true) // 고유한값
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String email;
	
	// FIXME #REFACT: 가입일 추가
	private LocalDateTime regDate;
}
