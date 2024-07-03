package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.SoftDeleteService;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/")
@RestController
public class SoftDeleteController {
	
	
	private final SoftDeleteService softDeleteService;
	
	@Autowired
	public SoftDeleteController(SoftDeleteService softDeleteService) {
		this.softDeleteService= softDeleteService;
	}
	
	@GetMapping("/soft-delete")
	public ResponseEntity<Users> logout(@Valid @RequestParam Long id)
			throws UserApplicationException {

		Users response = softDeleteService.softDeleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	

}
