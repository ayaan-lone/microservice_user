package com.onlineBanking.user.exception;

import org.springframework.http.HttpStatus;

public class UserBlockedException extends Exception {

	private static final long serialVersionUID = -3939736934571471449L;

	private final HttpStatus httpStatus = HttpStatus.FORBIDDEN;
	private final String message = "User is blocked!!";

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}
