package com.onlineBanking.user.service;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserLoginRequestDto;
import com.onlineBanking.user.response.LoginResponseDto;

public interface LoginService {
	LoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) throws UserApplicationException;

}
