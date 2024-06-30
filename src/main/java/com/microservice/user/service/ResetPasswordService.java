package com.microservice.user.service;

import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ResetPasswordDto;
import com.microservice.user.request.VerifyOtpDto;

public interface ResetPasswordService {
	String verifyOtp(VerifyOtpDto verifyOtpDto) throws UserApplicationException;

	String changePasswordWithOtp(ResetPasswordDto newPassword) throws UserApplicationException;

}
