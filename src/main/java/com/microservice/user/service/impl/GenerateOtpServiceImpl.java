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
import com.microservice.user.service.GenerateOtpService;

@Service
public class GenerateOtpServiceImpl implements GenerateOtpService {
	public static String generateOTP() {
		SecureRandom random = new SecureRandom();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	private final RegisterUserRepository registerUserRepository;

	@Autowired
	public GenerateOtpServiceImpl(RegisterUserRepository registerUserRepository) {
		this.registerUserRepository = registerUserRepository;
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

}
