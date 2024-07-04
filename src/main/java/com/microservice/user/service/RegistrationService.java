package com.microservice.user.service;


import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.response.UserPaginationResponse;

public interface RegistrationService {
	
	UserPaginationResponse getAllUsers(Integer pageNumber, Integer pageSize);
	
	Users registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException;

}
