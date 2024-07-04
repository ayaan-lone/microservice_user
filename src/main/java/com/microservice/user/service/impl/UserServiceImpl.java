package com.microservice.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.UserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserUpdateDto;
import com.microservice.user.service.UserService;
import com.microservice.user.util.ConstantUtil;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Users getUserByUsername(String username) throws UserApplicationException {
		Optional<Users> userOptional = userRepository.findByUsername(username);

		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND,
					ConstantUtil.USER_NOT_FOUND + "username " + username);
		}

		return userOptional.get();
	}

	public Users getUserByPhoneNumber(String phoneNumber) throws UserApplicationException {
		Optional<Users> userOptional = userRepository.findByPhoneNumber(phoneNumber);
		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND,
					ConstantUtil.USER_NOT_FOUND + "phone Number " + phoneNumber);
		}
		return userOptional.get();
	}

	public Users getUserByEmail(String email) throws UserApplicationException {
		Optional<Users> userOptional = userRepository.findByEmail(email);
		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND + "email " + email);
		}
		return userOptional.get();
	}

	@Override
	public List<Users> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Users getUserById(Long id) throws UserApplicationException {
		// TODO Auto-generated method stub
		Optional<Users> userOptional = userRepository.findById(id);

		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_AVAILABLE + id);
		}

		return userOptional.get();
	}

	@Override
	public Users searchUser(String username, String phoneNumber, String email) throws UserApplicationException {
	    
		return userRepository.searchUser(username, email, phoneNumber); 
	}

	@Override
	public Users updateUser(Long userId, UserUpdateDto userUpdateDto) throws UserApplicationException {
		Optional<Users> userOptional = userRepository.findById(userId);

		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_AVAILABLE + userId);
		}

		Users user = userOptional.get();
		user.setFirstName(userUpdateDto.getFirstname());
		user.setLastName(userUpdateDto.getLastname());
		user.setPhoneNumber(userUpdateDto.getPhoneNumber());
		return userRepository.save(user);
	}

	@Override
	public String deleteUser(Long userId) throws UserApplicationException {

		Optional<Users> userOptional = userRepository.findById(userId);

		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_AVAILABLE + userId);
		}

		userRepository.deleteById(userId);
		return "User deleted Successfully"; 
	}

}
