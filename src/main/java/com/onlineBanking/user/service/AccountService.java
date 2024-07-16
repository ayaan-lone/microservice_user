package com.onlineBanking.user.service;

import javax.security.auth.login.AccountException;

import com.onlineBanking.user.exception.UserApplicationException;

public interface AccountService {
    void createAccount(long userId, long accountId) throws UserApplicationException;
    
}
