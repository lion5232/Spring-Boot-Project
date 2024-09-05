package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

 

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Controller
@RequestMapping("/meal")
public class MealController {
	 
	@GetMapping("/list")
	public String meal() {
		return "meal_list";
	}
	@PostMapping("/list")
	public String list() {
		return "meal_list";
	}
}
