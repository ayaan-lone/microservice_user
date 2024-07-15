package com.onlineBanking.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.TransactionDto;
import com.onlineBanking.user.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	private final RestTemplate restTemplate;
	private final UserRepository userRepository;
	
	@Autowired
	public TransactionServiceImpl(RestTemplate restTemplate, UserRepository userRepository) {
		this.restTemplate = restTemplate;
		this.userRepository = userRepository;
	}
	
	@Value("${onlineBanking.transaction.url}")
	private String transactionUrl;

	@Override
	public String updateTransaction(TransactionDto transactionDto) throws UserApplicationException {
		// TODO Auto-generated method stub
		
		Optional<Users> optionalUser = userRepository.findById(transactionDto.getUserId());
		if(!optionalUser.isPresent()) {
			throw new UserApplicationException(HttpStatus.NOT_FOUND, "User is not present!");
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<TransactionDto> requestEntity = new HttpEntity<>(transactionDto, headers);
		ResponseEntity<String> response = restTemplate.exchange(transactionUrl, HttpMethod.POST, requestEntity, String.class);
		
		return response.getBody();
	}

}
