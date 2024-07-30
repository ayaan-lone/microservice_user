package com.onlineBanking.user.service.impl;

import java.util.Optional;

//import com.spring3.oauth.jwt.models.UserInfo;
//import com.spring3.oauth.jwt.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public Users loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Entering in loadUserByUsername Method...");
        Optional<Users> userOpt = userRepository.findByUsername(username);
        if(!userOpt.isPresent()){
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        
        Users user = userOpt.get(); 
        
        logger.info("User Authenticated Successfully..!!!");
        return user; 
    }
}