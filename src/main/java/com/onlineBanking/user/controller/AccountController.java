package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.onlineBanking.user.service.AccountService;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping("create/{userId}")
	public void createAccount(@PathVariable long userId, @RequestParam String accountType) {
		accountService.createAccount(userId, accountType);
	}
}
