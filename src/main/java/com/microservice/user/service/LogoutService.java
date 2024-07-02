package com.microservice.user.service;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserLoginRequestDto;

public interface LogoutService {
	Users logoutUser(Long id) throws UserApplicationException;


}
