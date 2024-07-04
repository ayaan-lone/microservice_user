package com.microservice.user.service;


import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.response.UserPaginationResponse;

public interface RegistrationService {
	
	
	
	Users registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException;

}
