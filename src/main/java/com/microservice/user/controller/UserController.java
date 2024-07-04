package com.microservice.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserUpdateDto;
import com.microservice.user.response.UserPaginationResponse;
import com.microservice.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<UserPaginationResponse> getAllUsers(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		UserPaginationResponse response = userService.getAllUsers(pageNumber, pageSize);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("user/{userId}")
	public ResponseEntity<Users> getUserById(@PathVariable Long userId) throws UserApplicationException {
		Users response = userService.getUserById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("search")
	public ResponseEntity<Users> searchUser(@RequestParam(required = false) String username,
			@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String email)
			throws UserApplicationException {

		Users response = userService.searchUser(username, phoneNumber, email);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PutMapping("user/{userId}")
	public ResponseEntity<Users> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto)
			throws UserApplicationException {

		Users updatedUser = userService.updateUser(userId, userUpdateDto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@DeleteMapping("user/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws UserApplicationException {
		String response = userService.deleteUser(userId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/soft-delete")
	public ResponseEntity<String> logout(@Valid @RequestParam Long id) throws UserApplicationException {

		String response = userService.softDeleteUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
