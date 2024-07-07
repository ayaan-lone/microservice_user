package com.onlineBanking.user.service;


import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserRegistrationRequestDto;

public interface RegistrationService {
	String registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException;
}
