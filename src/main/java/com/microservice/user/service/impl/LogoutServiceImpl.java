package com.microservice.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.LogoutService;

@Service
public class LogoutServiceImpl implements LogoutService {
	private final RegisterUserRepository registerUserRepository;

	@Autowired
	public LogoutServiceImpl(RegisterUserRepository registerUserRepository) {
		this.registerUserRepository = registerUserRepository;
	}

	@Override
	public Users logoutUser(Long id) throws UserApplicationException {
		Optional<Users> optionalUser = registerUserRepository.findById(id);
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User not present");
		}

		Users user = optionalUser.get();
		user.setLoggedIn(false);
		registerUserRepository.save(user);
		return user;
	}

}
