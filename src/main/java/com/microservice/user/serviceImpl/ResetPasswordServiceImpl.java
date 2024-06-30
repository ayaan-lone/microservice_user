package com.microservice.user.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.entity.Users;
import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.exception.UserApplicationException;
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
