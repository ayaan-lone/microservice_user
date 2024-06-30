package com.microservice.user.service;

import com.microservice.user.exception.UserApplicationException;

public interface GenerateOtpService {
	String generateOtp(String email) throws UserApplicationException;

}
