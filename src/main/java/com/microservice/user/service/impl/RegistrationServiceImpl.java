package com.microservice.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.response.UserPaginationResponse;
import com.microservice.user.service.RegistrationService;

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
	public Users registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException {
		
		//Convert all the characters into lowercase 
		//Trim the backspace
		//Add Conflict 
		//Add constants in utils
		// For Create and Update : Return String
		
		Optional<Users> optionalUser = registerUserRepository.findByEmail(userRegistrationRequestDto.getEmail());
		if (optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.CONFLICT, "User already present");
		}
		Users user = modelMapper.map(userRegistrationRequestDto, Users.class);
		registerUserRepository.save(user);
		return user;
	}

	@Override
	public UserPaginationResponse getAllUsers(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		UserPaginationResponse response = new UserPaginationResponse();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = registerUserRepository.findAll(pageable);
		response.setPageNo(pageNumber);
		response.setPageSize(pageSize);
		response.setPageCount(userPage.getTotalElements());
		response.setUserList(userPage.getContent());
		return response;
	}

}
