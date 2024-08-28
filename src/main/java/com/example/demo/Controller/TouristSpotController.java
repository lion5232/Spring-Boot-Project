package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/TourSpot")
public class TouristSpotController {
	@GetMapping("/list")
	public String TourSpot() {
		return "TourSpot_list";
	}

	@PostMapping("/list")
	public String list() {
		return "TourSpot_list";
	}
}
