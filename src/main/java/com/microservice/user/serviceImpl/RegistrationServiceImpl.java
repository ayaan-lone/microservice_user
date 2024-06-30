package com.microservice.user.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.entity.Users;
import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserRegistrationRequestDto;
import com.microservice.user.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private final RegisterUserRepository registerUserRepository;
	private final ModelMapper modelMapper;

	@Autowired	
	public RegistrationServiceImpl(RegisterUserRepository registerUserRepository, ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
		this.registerUserRepository = registerUserRepository;
	}

	@Override
	public Users registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException {
		Optional<Users> optionalUser = registerUserRepository.findByEmail(userRegistrationRequestDto.getEmail());
		if (optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.BAD_REQUEST, "User already present");
		}
		Users user = modelMapper.map(userRegistrationRequestDto, Users.class);
		registerUserRepository.save(user);
		return user;
	}

	@Override
	public List<Users> getAllUsers() {
		return registerUserRepository.findAll();
		
	}

}
