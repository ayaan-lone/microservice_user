package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ChangePasswordDto;
import com.microservice.user.service.ChangePasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class ChangePasswordController {

	private final ChangePasswordService changePasswordService;

	@Autowired
	public ChangePasswordController(ChangePasswordService changePasswordService) {
		this.changePasswordService = changePasswordService;
	}

	@PostMapping("/change-password")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto)
			throws UserApplicationException {
		return ResponseEntity.ok(changePasswordService.changePassword(changePasswordDto));
	}
}