package com.microservice.user.service;

import java.util.List;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.request.UserUpdateDto;

public interface UserService {
   
	List<Users> getAllUsers();
	Users getUserById(Long userId) throws UserApplicationException; 
	Users searchUser(String username, String phonenumber, String email) throws UserApplicationException; 
	Users updateUser(Long id, UserUpdateDto userUpdateDto) throws UserApplicationException; 
	String deleteUser(Long userId) throws UserApplicationException; 
}
