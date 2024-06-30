package com.microservice.user.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.entity.Users;

@Repository
public interface RegisterUserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
}
