package com.microservice.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.service.RegistrationService;
import com.microservice.user.util.ConstantUtil;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final RegisterUserRepository registerUserRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public RegistrationServiceImpl(RegisterUserRepository registerUserRepository, ModelMapper modelMapper) {

		this.modelMapper = modelMapper;
		this.registerUserRepository = registerUserRepository;
	}

	@Override
	public String registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException {
		
		Optional<Users> optionalUser = registerUserRepository.findByEmail(userRegistrationRequestDto.getEmail());
		if (optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.CONFLICT, ConstantUtil.USER_ALREADY_PRESENT);
		}
		Users user = modelMapper.map(userRegistrationRequestDto, Users.class);
		registerUserRepository.save(user);
		return "User has been Created";
	}

	

}
