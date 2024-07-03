package com.microservice.user.service;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;

public interface GetUserByIdService {
	
	Users getUserById(Long id) throws UserApplicationException;
}
