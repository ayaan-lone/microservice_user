package com.onlineBanking.user.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.response.CardResponseDto;

@Component
public class CardClientHandler {

	private final RestTemplate restTemplate;

	@Value("${onlineBanking.card_details.url}")
	private String cardListUrl;

	public CardClientHandler(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<CardResponseDto> getUserCards( Long userId, String token) {
		// Prepare request for card details
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);  // Add "Bearer " prefix if needed
        HttpEntity<Void> cardHttpEntity = new HttpEntity<>(headers);


		// Fetch card details
		ResponseEntity<List<CardResponseDto>> cardResponseEntity = restTemplate.exchange(
				cardListUrl + userId, HttpMethod.GET, cardHttpEntity,
				new ParameterizedTypeReference<List<CardResponseDto>>() {
				});
		
		return cardResponseEntity.getBody();
	}

}