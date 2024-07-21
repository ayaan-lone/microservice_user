package com.onlineBanking.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.request.DashboardDetailsRequestDto;
import com.onlineBanking.user.response.AccountResponseDto;
import com.onlineBanking.user.util.ConstantUtil;

@Component
public class AccountClientHandler {

	private final RestTemplate restTemplate;

	@Value("${onlineBanking.account_details.url}")
	private String accountDetailsUrl;

	public AccountClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public AccountResponseDto getAccountDetails(DashboardDetailsRequestDto dashboardDetailsRequestDto) {
		// Prepare request for account details
		HttpEntity<DashboardDetailsRequestDto> accountHttpEntity = new HttpEntity<>(dashboardDetailsRequestDto);

		// Fetch account details
		ResponseEntity<AccountResponseDto> accountResponseEntity = restTemplate.exchange(
				accountDetailsUrl + dashboardDetailsRequestDto.getUserId(), HttpMethod.GET,
				accountHttpEntity, new ParameterizedTypeReference<AccountResponseDto>() {
				});
		
		return accountResponseEntity.getBody();
	}
	

}
