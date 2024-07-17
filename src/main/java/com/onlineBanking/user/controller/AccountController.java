package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.CreateAccountRequestDto;
import com.onlineBanking.user.service.AccountService;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {

	@Autowired
	private AccountService accountService;

// API to create a new account which will in turn generate a new Card
	@PostMapping("{userId}")
	public void createAccount(CreateAccountRequestDto createAccountRequestDto) throws UserApplicationException {
		accountService.createAccount(createAccountRequestDto);
	}

}
