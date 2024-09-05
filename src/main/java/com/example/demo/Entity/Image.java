package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id; // 이미지 ID

	    private String filePath; // 파일 경로
	    
	    @ManyToOne
	    @JoinColumn(name = "post_id")// 게시글 ID와의 관계 설정
	    private Post post;// 게시글과의 연관관계

}
