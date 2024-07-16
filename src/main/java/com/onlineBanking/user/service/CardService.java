package com.onlineBanking.user.service;

import java.util.List;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.response.CardDetailResponseDto;

public interface CardService {
	String deactivateCard(Long userId, String last4Digits);
	List<CardDetailResponseDto> checkDetail(AccountDetailRequestDto cardDetailRequesteDto) throws UserApplicationException;

}
