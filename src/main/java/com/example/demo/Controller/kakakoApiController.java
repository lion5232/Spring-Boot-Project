package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class kakakoApiController {
	@GetMapping("Acco/list/map")
	public String map() {
		return "kakaomap";
	}
}
