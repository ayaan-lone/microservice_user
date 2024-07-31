package com.onlineBanking.user.response;

public class LoginResponseDto {

	private String JwtToken;

	public LoginResponseDto(String jwtToken) {
		JwtToken = jwtToken;
	}

	public String getJwtToken() {
		return JwtToken;
	}

	public void setJwtToken(String jwtToken) {
		JwtToken = jwtToken;
	}

}
