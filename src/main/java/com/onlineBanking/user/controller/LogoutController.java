package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.service.LogoutService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/")
@RestController
public class LogoutController {
	private final LogoutService logoutService;

	@Autowired
	public LogoutController(LogoutService logoutService) {
		this.logoutService = logoutService;
	}
    
	// To Logout a user
	@GetMapping("/logout")
	public ResponseEntity<Users> logout(HttpServletRequest request) throws UserApplicationException {
		Long userId = (Long) request.getAttribute("userId");
		Users response = logoutService.logoutUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
