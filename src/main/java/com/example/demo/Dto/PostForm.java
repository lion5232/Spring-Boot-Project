package com.example.demo.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
	//이 POSTFORM객체를 연결해줘야 쓸수 있는데 컨트롤러에서 인자를 통해 전달 한다. 
	// 이러면 Model 객체 없이도 html에 전달 가능하다.
	@NotEmpty(message = "제목 내용은 반드시 입력해야 하는 필수 항목입니다.")
	// 디비상 테이블의 해당 컬럼의 크기와 같이 연동, 100Byte이내 작성가능
	@Size(max=100)
	private String subject;
	
	//NULL, 공백 => X
	@NotEmpty (message="본문 내용을 반드시 입력해야 하는 필수 항목입니다.")//jakarata.vaildation이 여기서 쓴다. hibrate는 DB에서 쓴다.
	private String content;
}
