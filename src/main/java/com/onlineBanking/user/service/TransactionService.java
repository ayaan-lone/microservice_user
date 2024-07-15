package com.onlineBanking.user.service;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.TransactionDto;

public interface TransactionService {

	String updateTransaction(TransactionDto transactionDto) throws UserApplicationException;

}
