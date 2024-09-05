package com.example.demo.Controller;

import java.net.MalformedURLException;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/images")
public class ImageController {
	 private final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/pictures/";
	 
	 @GetMapping("/{filename}")
	 @ResponseBody
	 public ResponseEntity<Resource> downloadImage(@PathVariable String filename) throws MalformedURLException {
	        UrlResource resource = new UrlResource("file:" + Paths.get(uploadDir, filename).toString());
	        if (resource.exists() || resource.isReadable()) {
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // 파일의 MIME 타입을 설정
	                    .body(resource);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }
}
