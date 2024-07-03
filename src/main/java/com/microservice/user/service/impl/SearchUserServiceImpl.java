package com.microservice.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservice.user.dao.RegisterUserRepository;
import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.SearchUserService;
import com.microservice.user.util.*; // Import your USER_NOT_FOUND constant

@Service
public class SearchUserServiceImpl implements SearchUserService {

    private final RegisterUserRepository registerUserRepository;

    @Autowired
    public SearchUserServiceImpl(RegisterUserRepository registerUserRepository) {
        this.registerUserRepository = registerUserRepository;
    }

    @Override
    public Optional<Users> findByUsernameOrPhoneNumberOrEmail(String searchParam) throws UserApplicationException {

        Optional<Users> user = registerUserRepository.findByUsername(searchParam);
        if (user.isPresent()) {
            return user;
        }

        
        user = registerUserRepository.findByPhoneNumber(searchParam);
        if (user.isPresent()) {
            return user;
        }

        
        user = registerUserRepository.findByEmail(searchParam);
        if (user.isPresent()) {
            return user;
        }

        
        throw new UserApplicationException(HttpStatus.NOT_FOUND,ConstantUtil.USER_NOT_AVAILABLE);
    }
}
