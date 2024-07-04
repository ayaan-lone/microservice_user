package com.microservice.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.service.RegistrationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/")
@RestController
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		super();
		this.registrationService = registrationService;
	}
	
	@PostMapping("register")
	public ResponseEntity<Users> registerUser(
			@Valid @org.springframework.web.bind.annotation.RequestBody UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException {
		Users response = registrationService.registerUser(userRegistrationRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
