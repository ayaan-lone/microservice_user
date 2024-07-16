package com.onlineBanking.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.service.AccountService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private RestTemplate restTemplate;
	
	
	private UserRepository userRepository;
	
	@Autowired
	public AccountServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository; 
	}
	

	@Override
	public void createAccount(long userId, long accountId) throws UserApplicationException {
		//create a Url and pass it on to restTemplate
		Optional<Users> userOptional = userRepository.findById(userId);
		
		if(!userOptional.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND); 	
		}
		
		
		String url = ConstantUtil.CREATE_ACCOUNT_API_URL+"?userId=" + userId + "&accountId=" + accountId;
		restTemplate.postForObject(url, null, Object.class);
	}
}
