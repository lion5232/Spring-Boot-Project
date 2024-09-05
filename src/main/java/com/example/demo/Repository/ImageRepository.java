package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
