package com.onlineBanking.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.onlineBanking.user.service.CardService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String deactivateCard(Long userId, String last4Digits) {
		// Create a new Uri and add the query params
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ConstantUtil.CARD_API_URL)
				.queryParam("userId", userId).queryParam("last4Digits", last4Digits);
		return restTemplate.postForObject(builder.toUriString(), null, String.class);
	}
}
