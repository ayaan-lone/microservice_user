package com.onlineBanking.user.response;

import com.onlineBanking.user.entity.UserRole;

public class LoginResponseDto {
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	private Long userId;
	private UserRole userRole;
	private String JwtToken;

	public LoginResponseDto(String email, String username, String firstName, String lastName, Long userId,
			UserRole userRole, String jwtToken) {
		super();
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.userRole = userRole;
		JwtToken = jwtToken;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getEmail() {
		return email;
	}

	public String getJwtToken() {
		return JwtToken;
	}

	public void setJwtToken(String jwtToken) {
		JwtToken = jwtToken;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
