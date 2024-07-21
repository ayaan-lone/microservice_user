package com.onlineBanking.user.response;
import java.util.List;

public class DashboardDetailsResponseDto {
	
	
    private AccountResponseDto account;
    
    private List<CardResponseDto> cards;
    
	
	
	public AccountResponseDto getAccount() {
		return account;
	}

	public void setAccount(AccountResponseDto account) {
		this.account = account;
	}

	public List<CardResponseDto> getCards() {
		return cards;
	}
	
	public void setCards(List<CardResponseDto> cards) {
		this.cards = cards;
	}

}
