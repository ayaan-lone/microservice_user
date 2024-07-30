package com.onlineBanking.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineBanking.user.dao.RegisterUserRepository;
import com.onlineBanking.user.entity.UserRole;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserRegistrationRequestDto;
import com.onlineBanking.user.response.RegistrationResponseDto;
import com.onlineBanking.user.service.RegistrationService;
import com.onlineBanking.user.util.ConstantUtil;

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
	public RegistrationResponseDto registerUser(UserRegistrationRequestDto userRegistrationRequestDto) throws UserApplicationException {
		
		//Converting the email to lowercase and trimming the leading and trailing spaces
		
		String email = userRegistrationRequestDto.getEmail().toLowerCase().trim(); 
		Optional<Users> optionalUser = registerUserRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.CONFLICT, ConstantUtil.USER_ALREADY_PRESENT);
		}
		
		userRegistrationRequestDto.setEmail(email);
		Users user = modelMapper.map(userRegistrationRequestDto, Users.class);
		user.setRole(userRegistrationRequestDto.getRole());
		registerUserRepository.save(user);
		RegistrationResponseDto registrationResponse = new RegistrationResponseDto();
		registrationResponse.setUserId(user.getId());
		return registrationResponse;
	}

	

}
