/**
 * 실제 로그인 처리는 
 * 스프링 시큐리티에서 UserDetailsServcie 객체를 이용하여
 * 반드시 구현해야할 함수를 통해서 처리된다.=> 강제사항
 */

package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.SnsUser;
import com.example.demo.Repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{
	//SnsUser 엔티티 관련 레포지토리를 DI 
	@Autowired
	private UserRepository userRepository;
	
	/**
	 *  사용자가 로그인하면 인증에 대한 처리 진행하는 메인코드
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//1. username을 이용해서 JPA를 사용해 사용자 획득 => SnsUser 엔티티 획득
		Optional<SnsUser> oUser = this.userRepository.findByUsername(username);
		if(oUser.isEmpty() ) { //내용이 없다 => 해당 아이디로 가입자 없다.	
			throw new UsernameNotFoundException("해당 아이디로 가입한 사용자가 없습니다.");
		}
		//사용자 획득
		SnsUser user = oUser.get();
		System.out.println("인증중..." + user.getUsername());
		
		//2.  권한을 (가입하는 단계에서 부여(권한을 구분하는 값), 회원 정보를 획득한 이후 부여)
		// 권한: admin, 일반, 간단하게 username을 기반으로 부여
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>(); 
		// 정책은 간단하게 구성 사용자 아이디가 admin, t2024는 admin 권한을 부여, 나머지는 일반으로 부여
		if(username.equals("admin") || username.equals("t2024")) {//사용자 아이디가 admin, t2024
				grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getRole()));
		}else {
			grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.NOR.getRole()));		
		}
		
		//3. UserDetails 객체 반환
		//org.springframework.security.core.userdetails.User
		// UserDetails을 구현한 클레스가 USer이고, 이 객체를 반환함으로써 로그인이 성립이된다.
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
