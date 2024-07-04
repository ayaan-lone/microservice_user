package com.microservice.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.entity.Users;
import com.microservice.user.exception.UserApplicationException;
import com.microservice.user.service.SearchUserService;

@RestController
public class SearchUserController {

    private final SearchUserService searchUserService;

    @Autowired
    public SearchUserController(SearchUserService searchUserService) {
        this.searchUserService = searchUserService;
    }

    @GetMapping("/search/user")
    public ResponseEntity<Optional<Users>> searchUser(
            @RequestParam(required = true) String searchParam
    ) throws UserApplicationException {
        Optional<Users> user = searchUserService.findByUsernameOrPhoneNumberOrEmail(searchParam);
        return ResponseEntity.ok(user);
    }
}
