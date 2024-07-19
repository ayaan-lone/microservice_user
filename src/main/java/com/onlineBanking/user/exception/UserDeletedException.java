package com.onlineBanking.user.exception;

import org.springframework.http.HttpStatus;

public class UserDeletedException extends Exception {

	private static final long serialVersionUID = -5533734464088037908L;

	private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	private final String message = "User is deleted from the database";

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}
