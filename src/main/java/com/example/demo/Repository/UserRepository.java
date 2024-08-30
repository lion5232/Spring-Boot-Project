package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.SnsUser;

public interface UserRepository extends JpaRepository<SnsUser, Long>{

}
