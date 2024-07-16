package com.onlineBanking.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.response.CardDetailResponseDto;
import com.onlineBanking.user.service.CardService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class CardServiceImpl implements CardService {
	
	@Value("${onlineBanking.card_details.url}")
	private String cardDetailsUrl;
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	public CardServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public String deactivateCard(Long userId, String last4Digits) {
		// Create a new Uri and add the query params
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ConstantUtil.CARD_API_URL)
				.queryParam("userId", userId).queryParam("last4Digits", last4Digits);
		return restTemplate.postForObject(builder.toUriString(), null, String.class);
	}
	@Override
	public List<CardDetailResponseDto> checkDetail(AccountDetailRequestDto cardDetailRequestDto)
			throws UserApplicationException {
		Optional<Users> optionalUser = userRepository.findByUsername(cardDetailRequestDto.getUsername().toLowerCase().trim());

		// If User does not exist
		if (!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND);
		}

		Users user = optionalUser.get();
		long userId = user.getId();
		System.out.println("User Id Is " + userId);

		if (!cardDetailRequestDto.getPassword().equals(user.getPassword())) {
			throw new UserApplicationException(HttpStatus.UNAUTHORIZED, ConstantUtil.INVALID_CREDENTIALS);
		}

		// Make a call to CardMicroservice to fetch card details
		String cardMicroserviceUrl = cardDetailsUrl + userId;


		ResponseEntity<List<CardDetailResponseDto>> response = restTemplate.exchange(cardMicroserviceUrl,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<CardDetailResponseDto>>() {
				});
		if(response == null) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND,ConstantUtil.NO_ACCOUNT_FOUND);
			
		}
		List<CardDetailResponseDto> cards = response.getBody();
//		System.out.println(cards);
		return cards;

	}
}
