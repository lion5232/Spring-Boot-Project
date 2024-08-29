package com.example.demo.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {
	//이 REVIEWFORM객체를 연결해줘야 쓸수 있는데 컨트롤러에서 인자를 통해 전달 한다. 
	// 이러면 Model 객체 없이도 html에 전달 가능하다.

	//NULL, 공백 => X
	@NotEmpty (message="리뷰는 필수 항목입니다.")//jakarata.vaildation이 여기서 쓴다. hibrate는 DB에서 쓴다.
	@Size(max=100)
	private String content;
	
}
