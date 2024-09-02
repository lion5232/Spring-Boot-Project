package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.SnsUser;

public interface UserRepository extends JpaRepository<SnsUser, Long>{
	Optional<SnsUser> findByUsername(String username);
}
