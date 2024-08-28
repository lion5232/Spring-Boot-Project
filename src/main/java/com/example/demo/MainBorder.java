/**
 *  홈페이지
 */
package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
 

@Controller
public class MainBorder {

 
	@GetMapping("/")
	public String main() {
		return "index";
	}
	@GetMapping("/main")
	public String main1() {
		return "index2";
	}
 
//	@GetMapping("/map")
//	//@RequestMapping("/")
//	//	@ResponseBody
//	public String list() {
//		return "kakaomap";
//	}
	

}
