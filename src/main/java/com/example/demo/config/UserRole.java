/**
 * 사용자별 롤(역활), 권리(한) 등을 부여할때
 * 해당 목록을 정의한 열거형, 상수 컨셉
 */

package com.example.demo.config;

import lombok.Getter;

@Getter
public enum UserRole {
	//정책에 맞게 롤을 여러개 구성할 수 있다.
	// 사이트 : 관리자 관점 => 어드민, 총괄메니저, 매니저, ... 직책에 맞게 조정
	//사용자 관점 => 고객 등급별 부여(VVIP,VIP,GOLD,SILVER, BRONZE, NOVI..)
	ADMIN("ROLE_ADMIN"),
	GOLD("ROLE_GOLD"),
	NOR("ROLE_NOR");
	
	UserRole(String role){
		this.role = role;
	}
	private String role;
}
