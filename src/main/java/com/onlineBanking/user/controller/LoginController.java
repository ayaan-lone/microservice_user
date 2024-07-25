package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserLoginRequestDto;
import com.onlineBanking.user.response.LoginResponseDto;
import com.onlineBanking.user.service.LoginService;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:5500/") 
@RestController
public class LoginController {

	private final LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
    
	//To Login a User
	@PostMapping("login")
	public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto userLoginDto)
			throws UserApplicationException {
		LoginResponseDto response = loginService.loginUser(userLoginDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}