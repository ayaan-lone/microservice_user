package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.TransactionDto;
import com.onlineBanking.user.service.TransactionService;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
	
	private final TransactionService transactionService;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/transaction")
	ResponseEntity<String> updateTransaction(TransactionDto transactionDto) throws UserApplicationException{
		String response = transactionService.updateTransaction(transactionDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
