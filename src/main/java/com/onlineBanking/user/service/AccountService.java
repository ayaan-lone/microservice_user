package com.onlineBanking.user.service;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.request.CreateAccountRequestDto;
import com.onlineBanking.user.response.AccountDetailResponseDto;

public interface AccountService {
    void createAccount( CreateAccountRequestDto createAccountRequestDto) throws UserApplicationException;
	AccountDetailResponseDto checkDetail(AccountDetailRequestDto accountDetailRequesteDto) throws UserApplicationException;
    
}
