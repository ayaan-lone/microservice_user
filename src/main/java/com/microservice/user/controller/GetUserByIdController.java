package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.GetUserByIdService;

@RestController
@RequestMapping("api/v1/")
public class GetUserByIdController {
	
	private final GetUserByIdService getUserByIdService;
	
	@Autowired
	public GetUserByIdController(GetUserByIdService getUserByIdService) {
		super();
		this.getUserByIdService = getUserByIdService;
	}

	@GetMapping("user/{userId}")
	ResponseEntity<Users> getUserByUserId(@PathVariable Long userId) throws UserApplicationException{
		
		Users user = getUserByIdService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(user);
		
	}
}