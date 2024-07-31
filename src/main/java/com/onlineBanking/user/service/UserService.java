package com.onlineBanking.user.service;

import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.exception.UserBlockedException;
import com.onlineBanking.user.exception.UserDeletedException;
import com.onlineBanking.user.request.UserUpdateDto;
import com.onlineBanking.user.response.DashboardDetailsResponseDto;
import com.onlineBanking.user.response.UserPaginationResponse;

public interface UserService {

	UserPaginationResponse getAllUsers(Integer pageNumber, Integer pageSize);

	Users getUserById(Long userId) throws UserApplicationException;

	UserPaginationResponse searchUser(String username, String phonenumber, String email, Integer pageNumber,
			Integer pageSize) throws UserApplicationException;

	String updateUser(Long id, UserUpdateDto userUpdateDto) throws UserApplicationException;


	String softDeleteUser(Long userId) throws UserApplicationException;

	DashboardDetailsResponseDto getDashboardDetails(Long userId, String token) throws UserApplicationException;

	Boolean verifyUserAndStatus(Long userId) throws UserApplicationException, UserBlockedException, UserDeletedException;
}
