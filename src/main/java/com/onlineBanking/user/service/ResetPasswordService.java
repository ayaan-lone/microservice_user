package com.onlineBanking.user.service;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.ChangePasswordDto;
import com.onlineBanking.user.request.ResetPasswordDto;
import com.onlineBanking.user.request.VerifyOtpDto;

public interface ResetPasswordService {
	
	String changePassword(ChangePasswordDto changePasswordDto) throws UserApplicationException;
	String generateOtp(String email) throws UserApplicationException;
	String verifyOtp(VerifyOtpDto verifyOtpDto) throws UserApplicationException;
	String changePasswordWithOtp(ResetPasswordDto newPassword) throws UserApplicationException;
}
