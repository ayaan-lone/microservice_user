package com.microservice.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.ChangePasswordDto;
import com.microservice.user.service.ChangePasswordService;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService {

	private final RegisterUserRepository registerUserRepository;

	@Autowired
	public ChangePasswordServiceImpl(RegisterUserRepository registerUserRepository) {
		this.registerUserRepository = registerUserRepository;
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

}
