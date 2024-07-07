package com.onlineBanking.user.service;

import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserLoginRequestDto;

public interface LoginService {
	Users loginUser(UserLoginRequestDto userLoginRequestDto) throws UserApplicationException;

}
