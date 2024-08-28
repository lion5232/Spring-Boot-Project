package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Review {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

 @Column(columnDefinition = "TEXT")
 private String content;

 private LocalDateTime createDate;
 
 // 리뷰와 연관을 맺는( 부모-자식 관계) post 추가
 // FK 개념으로 컬럼이 생성되고, 속성명_PK명 으로 추가
 // post_id로 구성됨
 @ManyToOne
 private Post post;
}
