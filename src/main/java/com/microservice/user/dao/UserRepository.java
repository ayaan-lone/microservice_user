package com.microservice.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.user.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	    Optional<Users> findById(Long id);
	
	    Optional<Users> findByUsername(String username);
	    
	    Optional<Users> findByPhoneNumber(String phoneNumber);
	    
	    Optional<Users> findByEmail(String email);
}
