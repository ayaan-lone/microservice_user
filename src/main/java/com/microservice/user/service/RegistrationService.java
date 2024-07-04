package com.microservice.user.service;


import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;

public interface RegistrationService {
	Users registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException;

}
