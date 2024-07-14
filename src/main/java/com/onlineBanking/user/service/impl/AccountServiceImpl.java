package com.onlineBanking.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.onlineBanking.user.service.AccountService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void createAccount(long userId, long accountId) {
		//create a Url and pass it on to restTemplate
		String url = ConstantUtil.CREATE_ACCOUNT_API_URL+"?userId=" + userId + "&accountId=" + accountId;
		restTemplate.postForObject(url, null, Object.class);
	}
}
