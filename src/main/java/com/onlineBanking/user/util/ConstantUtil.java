package com.onlineBanking.user.util;

public class ConstantUtil {
	public static final String USER_NOT_AVAILABLE = "User is not avaialble in db with id ";
	public static final String USER_NOT_FOUND = "User not found in DB with ";
	public static final String USER_ALREADY_PRESENT = "User already present in db";
	public static final String ACCOUNT_BLOCKED = "Account is Blocked. Try again after 24 hours.";
	public static final String INVALID_CREDENTIALS = "Invalid Username or Password";
	public static final String OTP_MISMATCH_EXCEEDED = "Invalid Otp (Exceeded 5 minutes)";
	public static final String OTP_MISMATCH = "Invalid Otp";
	public static final String CREATE_ACCOUNT_API_URL = "http://localhost:8081/api/v1/create";
	public static final String CARD_API_URL = "http://localhost:8082/api/v1/cards/deactivate";
}
