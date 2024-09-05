package com.example.demo.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
// your an Entity 넌 엔티티이다.
@Entity
@ToString
public class Post {
	// 기본키 지정
	// 기본키는 중복될 수 없으며, 각 엔티티 인스턴스를 유일하게 구분할 수 있습니다.
	@Id	
	// @GeneratedValue : 엔티티의 기본키 값이 자동으로 생성되도록 설정.
	// strategy 속성 :기본키 값을 생성하는 전략을 정의.
	//GenerationType.IDENTITY: 데이터베이스의 자동 증가(AUTO_INCREMENT) 기능을 사용합니다.
	// 즉, 새로운 엔티티가 추가될 때마다 id 값이 자동으로 증가하여 고유하게 유지됩니다.
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 컬럼임을 지정 + 문자열의 길이를 직접 지정
    // 한글 200자? ASCII2 200자? 테스트 필요
    @Column(length = 2048)
    private String subject;
	
	 // 컬럼임을 지정 + 텍스트 입력의 크기를 지정, 텍스트를 넣을수 있다 (글자 수 제한 없음) (제약 조건 NOT NULL 값이 반드시 벗겨..입력되야 합니다.)
	@Column(columnDefinition = "TEXT")
	private String content;
	
	// 자동으로 컬럼으로 인식
	// createDate => create_date로 컬럼명이 변경됨(스네이크 스타일)
	private LocalDateTime createDate;
	
//	private String filePath; // 파일 경로를 저장할 필드  (getter랑 setter는 자동으로 생성) 	 
	
//	//아직 미구현
//	private String fileName; // 파일 이름를 저장할 필드  (getter랑 setter는 자동으로 생성) 
//	private Long fileSize; // 파일 크기를 저장할 필드
//	
	
	//지도 
//	   private double latitude;  // 위도 필드 추가
//	   private double longitude; // 경도 필드 추가
	
	
    @Override
	public String toString() {
		return "Post [id=" + id + ", subject=" + subject + ", content=" + content + ", createDate=" + createDate + "]";
	} //필드 값을 포함하여 객체의 상태를 문자열로 표현합니다.
	  // mappedBy : Review의 어떤 속성을 참고하여 매핑하는가?
    // ERD 기준 post 문자열(이름) 사용 =>  Review에 실제 구성되어야 함
    // Post가 삭제되면(글이 삭제되면) 연결된 리뷰들도 모두 삭제한다
    // 실제는 노출되지 않게 한다
    @OneToMany(mappedBy = "post", cascade= CascadeType.ALL , orphanRemoval = true)
    private List<Review> reviewList;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Image> images;
	
	 // 특징
    // 클레스의 맴버로만 표기해도 테이블의 컬럼이 된다
    // 기능적으로 필요한 속성, 컬럼으로 사용하기 싶지 않다
    // @Transient => 클레스 속성으로만 존재
}
