package com.microservice.user.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ChangePasswordDto;
import com.microservice.user.request.ResetPasswordDto;
import com.microservice.user.request.VerifyOtpDto;
import com.microservice.user.service.ResetPasswordService;

@Service

public class ResetPasswordServiceImpl implements ResetPasswordService {
	private final RegisterUserRepository registerUserRepository;

	@Autowired
	public ResetPasswordServiceImpl(RegisterUserRepository registerUserRepository) {
		this.registerUserRepository = registerUserRepository;
	}

	public static String generateOTP() {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
	
	
	@Override
	public String changePassword(ChangePasswordDto changePasswordDto) throws UserApplicationException {
		Optional<Users> optionalUser = registerUserRepository.findByEmail(changePasswordDto.getEmail());
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User not found.");
		}
		Users user = optionalUser.get();
		if (!changePasswordDto.getCurrentPassword().equals(user.getPassword())) {
			throw new UserApplicationException(HttpStatus.UNAUTHORIZED, "Current password is incorrect.");

		}
		user.setPassword(changePasswordDto.getNewPassword());
		registerUserRepository.save(user);
		return "Password has been changed successfully.";
	}

	@Override
	public String generateOtp(String email) throws UserApplicationException {
		// TODO Auto-generated method stub
		Optional<Users> optionalUser = registerUserRepository.findByEmail(email);
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User not found.");
		}
		Users user = optionalUser.get();
		String otp = generateOTP();

		user.setOtpValue(otp);
		user.setOtpGenerationTime(LocalDateTime.now());
		registerUserRepository.save(user);
		return otp;

	}

	@Override
	public String verifyOtp(VerifyOtpDto verifyOtpDto) throws UserApplicationException {
		Optional<Users> optionalUser = registerUserRepository.findByEmail(verifyOtpDto.getEmail());
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User not present");
		}

		Users user = optionalUser.get();

		if (user.isBlocked()) {
			throw new UserApplicationException(HttpStatus.UNAUTHORIZED, "User is blocked");
		}

		if (user.getOtpValue() == null || LocalDateTime.now().isAfter(user.getOtpGenerationTime().plusMinutes(5))) {
			throw new UserApplicationException(HttpStatus.UNAUTHORIZED, "Invalid Otp (Exceeded 5 minutes)");

		}
		if (!user.getOtpValue().equals(verifyOtpDto.getOtp())) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "Incorrect Otp");

		}
		user.setOtpVerified(true);
		registerUserRepository.save(user);
		return "Otp has been verified";
	}

	@Override
	public String changePasswordWithOtp(ResetPasswordDto newPassword) throws UserApplicationException {
		Optional<Users> optionalUser = registerUserRepository.findByEmail(newPassword.getEmail());
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User not present");
		}

		Users user = optionalUser.get();

		if (user.isBlocked()) {
			throw new UserApplicationException(HttpStatus.UNAUTHORIZED, "User is blocked");
		}

		user.setPassword(newPassword.getPassword());
		user.setOtpValue(null);
		user.setOtpGenerationTime(null);
		registerUserRepository.save(user);

		return "Password updated";
	}

}
