package com.skillstorms.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillstorms.backend.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByusername(String username);

}
