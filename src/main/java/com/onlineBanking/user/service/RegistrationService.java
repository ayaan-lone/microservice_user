package com.onlineBanking.user.service;


import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserRegistrationRequestDto;
import com.onlineBanking.user.response.RegistrationResponseDto;

public interface RegistrationService {
	RegistrationResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException;
}
