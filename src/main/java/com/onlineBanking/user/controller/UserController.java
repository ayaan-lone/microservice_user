package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.exception.UserBlockedException;
import com.onlineBanking.user.exception.UserDeletedException;
import com.onlineBanking.user.request.DashboardDetailsRequestDto;
import com.onlineBanking.user.request.UserUpdateDto;
import com.onlineBanking.user.response.DashboardDetailsResponseDto;
import com.onlineBanking.user.response.UserPaginationResponse;
import com.onlineBanking.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// To fetch all the users
	@GetMapping
	public ResponseEntity<UserPaginationResponse> getAllUsers(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		UserPaginationResponse response = userService.getAllUsers(pageNumber, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To search a user by user id

	@GetMapping("user/{userId}")
	public ResponseEntity<Users> getUserById(@PathVariable Long userId) throws UserApplicationException {
		Users response = userService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To search a user by username, email, phoneNumber

	@GetMapping("search")
	public ResponseEntity<UserPaginationResponse> searchUser(@RequestParam(required = false) String username,
			@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email,
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) throws UserApplicationException {

		if (email != null) {
			email = email.toLowerCase().trim();
		}

		UserPaginationResponse response = userService.searchUser(username, phoneNumber, email, pageNumber, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// Verify that user is not blocked and account is not deleted
	@GetMapping("verify-user")
	public ResponseEntity<Boolean> verifyUserAndStatus(@RequestParam(required = true) Long userId)
			throws UserApplicationException, UserBlockedException, UserDeletedException {
		Boolean response = userService.verifyUserAndStatus(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To update a user

	@PutMapping("user/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto)
			throws UserApplicationException {

		String response = userService.updateUser(userId, userUpdateDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To delete a user

	@DeleteMapping("user/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws UserApplicationException {
		String response = userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To soft delete a user

	@GetMapping("/soft-delete")
	public ResponseEntity<String> logout(@Valid @RequestParam Long id) throws UserApplicationException {

		String response = userService.softDeleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// To Fetch Account Details and Card Details To Show at Dash-board
	@PostMapping("/dashboard")
	public ResponseEntity<DashboardDetailsResponseDto> getDashboardDetails(
			@Valid @RequestBody DashboardDetailsRequestDto request) throws UserApplicationException {

		DashboardDetailsResponseDto response = userService.getDashboardDetails(request.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
