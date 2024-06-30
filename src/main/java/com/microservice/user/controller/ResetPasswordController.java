package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ResetPasswordDto;
import com.microservice.user.request.VerifyOtpDto;
import com.microservice.user.service.ResetPasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class ResetPasswordController {

	private final ResetPasswordService resetPasswordService;

	@Autowired
	public ResetPasswordController(ResetPasswordService resetPasswordService) {
		this.resetPasswordService = resetPasswordService;
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> resetPassword(@Valid @RequestBody VerifyOtpDto verifyOtpDto)
			throws UserApplicationException {
		return ResponseEntity.ok(resetPasswordService.verifyOtp(verifyOtpDto));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> updatePassword(@Valid @RequestBody ResetPasswordDto newPassword)
			throws UserApplicationException {
		return ResponseEntity.ok(resetPasswordService.changePasswordWithOtp(newPassword));
	}

}
