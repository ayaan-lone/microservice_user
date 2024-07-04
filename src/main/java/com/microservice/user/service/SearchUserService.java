package com.microservice.user.service;

import java.util.Optional;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;

public interface SearchUserService {
    
    Optional<Users> findByUsernameOrPhoneNumberOrEmail(String searchParam) throws UserApplicationException;
}
