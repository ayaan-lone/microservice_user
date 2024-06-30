package com.microservice.user.service;

import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ChangePasswordDto;

public interface ChangePasswordService {
	String changePassword(ChangePasswordDto changePasswordDto) throws UserApplicationException;

}
