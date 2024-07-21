package com.onlineBanking.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.request.DashboardDetailsRequestDto;
import com.onlineBanking.user.response.CardResponseDto;

@Component
public class CardClientHandler {

	private final RestTemplate restTemplate;

	@Value("${onlineBanking.card_details.url}")
	private String cardListUrl;

	public CardClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<CardResponseDto> getUserCards(DashboardDetailsRequestDto dashboardDetailsRequestDto) {
		// Prepare request for card details
		HttpEntity<DashboardDetailsRequestDto> cardHttpEntity = new HttpEntity<>(dashboardDetailsRequestDto);

		// Fetch card details
		ResponseEntity<List<CardResponseDto>> cardResponseEntity = restTemplate.exchange(
				cardListUrl + dashboardDetailsRequestDto.getUserId(), HttpMethod.GET, cardHttpEntity,
				new ParameterizedTypeReference<List<CardResponseDto>>() {
				});
		
		return cardResponseEntity.getBody();
	}

}