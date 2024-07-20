package com.onlineBanking.user.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.exception.UserBlockedException;
import com.onlineBanking.user.exception.UserDeletedException;
import com.onlineBanking.user.request.UserUpdateDto;
import com.onlineBanking.user.response.UserPaginationResponse;
import com.onlineBanking.user.service.UserService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// Check whether user is present or not
	private Users isUserPersists(Long userId) throws UserApplicationException {
		Optional<Users> userOptional = userRepository.findById(userId);

		// If user does not exist
		if (!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND + userId);
		}

		return userOptional.get();
	}

	// Fetching all the users

	@Override
	public UserPaginationResponse getAllUsers(Integer pageNumber, Integer pageSize) {

		UserPaginationResponse response = new UserPaginationResponse();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRepository.findAll(pageable);
		response.setPageNo(pageNumber);
		response.setPageSize(pageSize);
		response.setPageCount(userPage.getTotalElements());
		response.setUserList(userPage.getContent());
		return response;
	}

	// Search User by Id

	@Override
	public Users getUserById(Long id) throws UserApplicationException {
		return isUserPersists(id);
	}

	// Search user by username, phoneNumber, email and sending the response by
	// pagination.

	@Override
	public UserPaginationResponse searchUser(String username, String phoneNumber, String email, Integer pageNumber,
			Integer pageSize) throws UserApplicationException {

		UserPaginationResponse response = new UserPaginationResponse();
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Users> userPage = userRepository.searchUser(username, email, phoneNumber, pageable);
		response.setPageNo(pageNumber);
		response.setPageSize(pageSize);
		response.setPageCount(userPage.getTotalElements());
		response.setUserList(userPage.getContent());
		return response;
	}

//  Updating User Profile in DB

	@Override
	public String updateUser(Long userId, UserUpdateDto userUpdateDto) throws UserApplicationException {
		// Updating user data
		Users user = isUserPersists(userId);
		user.setFirstName(userUpdateDto.getFirstname());
		user.setLastName(userUpdateDto.getLastname());
		user.setPhoneNumber(userUpdateDto.getPhoneNumber());
		userRepository.save(user);
		return "User has been updated successfully";
	}

	@Override
	public String deleteUser(Long userId) throws UserApplicationException {
		Users user = isUserPersists(userId);
		// deleting user
		userRepository.deleteById(userId);
		return "User deleted Successfully";
	}

	@Override
	public String softDeleteUser(Long userId) throws UserApplicationException {
		// Check whether user is present or not
		Users user = isUserPersists(userId);
		user.setDeleted(true);
		userRepository.save(user);
		return "User is deleted!";
	}

	@Override
	public Boolean verifyUserAndStatus(Long userId)
			throws UserApplicationException, UserBlockedException, UserDeletedException {
		Users user = isUserPersists(userId);
		if(user.isBlocked()) {
			throw new UserBlockedException();
		}
		if(user.isDeleted()) {
			throw new UserDeletedException();
		}
		return true;
	}

}
