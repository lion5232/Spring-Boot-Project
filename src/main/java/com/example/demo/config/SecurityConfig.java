package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//스프링의 환경 설정 파일이다
@Configuration
//스프링 시큐리티의 제어를 받는다 -> 보안 정책을 커스텀할수 있다 -> 스프링 시큐리티의 활성화
@EnableWebSecurity
public class SecurityConfig {
	//내부적으로 스프링 시큐리티에 사용된 객체를 빈으로 구성
	//스프링 부트가 SecurityFilterChain을 호출해서 객체를 하나 만들어 놓고 필요한데 쓸 수 있게끔 해준다.
	@Bean
	SecurityFilterChain fliterChain(HttpSecurity http) throws Exception {
		//1. http 체인닝 코드(=> 객체.메소드().메소드().메소드()...)
		 
		//() -> 람다 표현식 문법: (파라미터) -> 람다연산자 expression: 함수의 본문
		http
				//http.authorizeHttpRequests : http요청에 대한 인증().
				// /** => 인증되지 않은 모든 페이지의 요청을 허가한다, 로그인 없이 접근 가능
				.authorizeHttpRequests( ( a ) ->a.requestMatchers
				(new AntPathRequestMatcher("/**")).permitAll() )
		.csrf( (b) -> 
			b.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))) 
		//h2-console 뒤의 모든 경로에 대해선 CSRF 보호가 필요한 URL 패턴을 설정하지 않고, 모든 URL 패턴에 대해 CSRF 보호를 적용하지 않도록 설정한다.
		// X-Frame-Options 헤더값을 SAMEORIGIN으로 대체 -> iframe 미로딩되는 문제 해결
		.headers( (c )  ->c.addHeaderWriter(new XFrameOptionsHeaderWriter(
				XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN	)) )
		
		;
			
		// 2. http 빌드 후 리턴
		return http.build();
		//여기까지하면 h2-console에는 접속시 403오류로 csrf가 없어서 보안 이슈가 생긴다. => 설정에 csrf에 대한 예외 규정을 추가해야 한다.
		// 토큰을 발급해서 전송함으로써 처리해준다.
	}
}
