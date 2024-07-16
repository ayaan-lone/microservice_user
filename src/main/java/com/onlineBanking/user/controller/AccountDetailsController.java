package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.response.AccountDetailResponseDto;
import com.onlineBanking.user.service.AccountService;

@RestController
@RequestMapping("/api/v1/user")
public class AccountDetailsController {

    private final AccountService accountService;

    @Autowired
    public AccountDetailsController(AccountService accountService) {
        this.accountService = accountService;
    }
//Added an endpoint for displaying the details of the User's Bank Account
    @PostMapping("/account-details")
    public ResponseEntity<AccountDetailResponseDto> checkAccountDetails(@RequestBody AccountDetailRequestDto accountDetailRequestDto) throws UserApplicationException {
        AccountDetailResponseDto responseDto = accountService.checkDetail(accountDetailRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
