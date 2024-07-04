package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.GenerateOtpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class GenerateOtpController {

	private final GenerateOtpService generateOtpService;

	@Autowired
	public GenerateOtpController(GenerateOtpService generateOtpService) {
		this.generateOtpService = generateOtpService;
	}

	@GetMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@Valid @RequestParam String email)
			throws UserApplicationException {

		String response = generateOtpService.generateOtp(email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}