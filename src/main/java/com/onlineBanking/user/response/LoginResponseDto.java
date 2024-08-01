package com.onlineBanking.user.response;

import jakarta.persistence.Column;

public class LoginResponseDto {
	
	
	private String email;

	private String username;

	private String token;
	
	private String firstName;

	private String lastName;

	private String phoneNumber;


	public LoginResponseDto(String firstName, String lastName,String username,String email,String phoneNumber, String token) {
		this.email = email;
		this.username = username;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


}
