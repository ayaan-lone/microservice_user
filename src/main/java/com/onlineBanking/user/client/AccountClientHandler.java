package com.onlineBanking.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import com.onlineBanking.user.response.AccountResponseDto;

@Component
public class AccountClientHandler {

	private final RestTemplate restTemplate;

	@Value("${onlineBanking.account_details.url}")
	private String accountDetailsUrl;

	public AccountClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public AccountResponseDto getAccountDetails(Long userId, String token) {
		// Prepare request for account details
		
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);  // Add "Bearer " prefix if needed
        HttpEntity<Void> accountHttpEntity = new HttpEntity<>(headers);

		// Fetch account details
		ResponseEntity<AccountResponseDto> accountResponseEntity = restTemplate.exchange(
				accountDetailsUrl + userId, HttpMethod.GET,
				accountHttpEntity, new ParameterizedTypeReference<AccountResponseDto>() {
				});
		
		return accountResponseEntity.getBody();
	}
	

}