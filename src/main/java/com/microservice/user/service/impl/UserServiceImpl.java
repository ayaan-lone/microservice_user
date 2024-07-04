package com.microservice.user.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.UserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserUpdateDto;
import com.microservice.user.response.UserPaginationResponse;
import com.microservice.user.service.UserService;
import com.microservice.user.util.ConstantUtil;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private Users isUserPersists(Long id) throws UserApplicationException {
		Optional<Users> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND,
					"User with user id " + id + " not present in database");
		}
		return user.get();
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
	public UserPaginationResponse getAllUsers(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		UserPaginationResponse response = new UserPaginationResponse();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRepository.findAll(pageable);
		response.setPageNo(pageNumber);
		response.setPageSize(pageSize);
		response.setPageCount(userPage.getTotalElements());
		response.setUserList(userPage.getContent());
		return response;
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

	@Override
	public String softDeleteUser(Long id) throws UserApplicationException {
		Users user = isUserPersists(id);
		user.setDeleted(true);
		userRepository.save(user);
		return "User is deleted!";
	}

}
