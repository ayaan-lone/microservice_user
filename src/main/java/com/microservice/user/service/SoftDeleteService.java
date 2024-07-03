package com.microservice.user.service;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;

public interface SoftDeleteService {
	Users softDeleteUser(Long id) throws UserApplicationException;

}
