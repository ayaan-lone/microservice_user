package com.onlineBanking.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.onlineBanking.user.client.AccountClientHandler;
import com.onlineBanking.user.client.CardClientHandler;
import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.exception.UserBlockedException;
import com.onlineBanking.user.exception.UserDeletedException;
import com.onlineBanking.user.request.UserUpdateDto;
import com.onlineBanking.user.response.AccountResponseDto;
import com.onlineBanking.user.response.CardResponseDto;
import com.onlineBanking.user.response.DashboardDetailsResponseDto;
import com.onlineBanking.user.response.UserPaginationResponse;
import com.onlineBanking.user.service.UserService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final CardClientHandler cardClientHandler;
	private final AccountClientHandler accountClientHandler;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, AccountClientHandler accountClientHandler,
			CardClientHandler cardClientHandler) {
		this.userRepository = userRepository;
		this.cardClientHandler = cardClientHandler;
		this.accountClientHandler = accountClientHandler;

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
		if (user.isBlocked()) {
			throw new UserApplicationException(HttpStatus.FORBIDDEN, "The user is blocked");
			// throw new UserBlockedException();
		}
		if (user.isDeleted()) {
			throw new UserDeletedException();
		}
		return true;
	}

	@Override
	public DashboardDetailsResponseDto getDashboardDetails(Long userId, String token) throws UserApplicationException {
		
		AccountResponseDto account = accountClientHandler.getAccountDetails(userId, token);
		List<CardResponseDto> cards = cardClientHandler.getUserCards(userId, token);

		// Create and populate the DashboardResponseDto
		DashboardDetailsResponseDto dashboardDetailsResponseDto = new DashboardDetailsResponseDto();
		dashboardDetailsResponseDto.setAccount(account);
		dashboardDetailsResponseDto.setCards(cards);

		return dashboardDetailsResponseDto;
	}

}
