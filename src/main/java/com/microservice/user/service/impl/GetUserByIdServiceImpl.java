package com.microservice.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.GetUserByIdService;
import com.microservice.user.util.ConstantUtil;
import com.microservice.user.dao.RegisterUserRepository;

@Service
public class GetUserByIdServiceImpl implements GetUserByIdService{

	
	private final RegisterUserRepository registerUserRepository;
	
	@Autowired
	public GetUserByIdServiceImpl(RegisterUserRepository registerUserRepository) {
		super();
		this.registerUserRepository = registerUserRepository;
	}
	
	private Users isUserPersists(Long id) throws  UserApplicationException{
		Optional<Users> userOptional = registerUserRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_AVAILABLE + id);
		}
		return userOptional.get();
	}
	
	
	@Override
	public Users getUserById(Long id) throws UserApplicationException {
		Users user = isUserPersists(id);
		return user;
	}	

}