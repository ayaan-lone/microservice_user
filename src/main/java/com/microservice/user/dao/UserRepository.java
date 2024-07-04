package com.microservice.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.microservice.user.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findById(Long id);

	Optional<Users> findByUsername(String username);

	Optional<Users> findByPhoneNumber(String phoneNumber);

	Optional<Users> findByEmail(String email);

	@Query("select users from Users users where (:username is null or users.username=:username) and (:email is null or users.email=:email) and (:phoneNumber is null or users.phoneNumber=:phoneNumber) ")
	Users searchUser(String username, String email, String phoneNumber);
}
