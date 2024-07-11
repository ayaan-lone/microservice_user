package com.onlineBanking.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.onlineBanking.user.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void createAccount(long userId, String accountType) {
		String url = "http://localhost:8081/api/v1/create?userId=" + userId + "&accountType=" + accountType;
		restTemplate.postForObject(url, null, Object.class);
	}
}
