package com.onlineBanking.user.service;

public interface CardService {
	String deactivateCard(Long userId, String last4Digits);
}
