package com.onlineBanking.user.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class UserApplicationExceptionHandler {
	@ExceptionHandler(value = { UserApplicationException.class })
	public ResponseEntity<Object> handleUserApplicationException(UserApplicationException userApplicationException) {
		return ResponseEntity.status(userApplicationException.getHttpStatus())
				.body(userApplicationException.getMessage());
	}

	@ExceptionHandler(value = { UserBlockedException.class })
	public ResponseEntity<Object> handleUserBlockedException(UserBlockedException userBlockedException) {
		return ResponseEntity.status(userBlockedException.getHttpStatus()).body(userBlockedException.getMessage());
	}
	
	@ExceptionHandler(value = { UserDeletedException.class })
	public ResponseEntity<Object> handleUserDeletedException(UserDeletedException userDeletedException){
		return ResponseEntity.status(userDeletedException.getHttpStatus()).body(userDeletedException.getMessage());
	}

	@ExceptionHandler(value = { HttpClientErrorException.class })
	ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException httpClientErrorException) {
		return ResponseEntity.status(httpClientErrorException.getStatusCode())
				.body(httpClientErrorException.getMessage());
	}
	
}
