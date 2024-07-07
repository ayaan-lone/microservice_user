package com.onlineBanking.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onlineBanking.user.dao.RegisterUserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserLoginRequestDto;
import com.onlineBanking.user.service.impl.LoginServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class) 
public class LoginServiceImplTest {

	@Mock
	private RegisterUserRepository registerUserRepository;

	@InjectMocks
	private LoginServiceImpl loginServiceImpl;

	@Test
	void testLoginUser_UserNotFound() {
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@example.com");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testLoginUser_UserBlocked() {
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@example.com");

		Users user = new Users();
		user.setBlocked(true);
		user.setBlockedDate(LocalDateTime.now().minusHours(1));

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.ACCOUNT_BLOCKED, exception.getMessage());
	}

	@Test
	void testLoginUser_InvalidCredentials() {
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@example.com");
		userLoginRequestDto.setPassword("wrongpassword");

		Users user = new Users();
		user.setPassword("correctpassword");
		user.setNumberOfAttempts(4);

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.INVALID_CREDENTIALS, exception.getMessage());

		assertEquals(5, user.getNumberOfAttempts());
		assertEquals(true, user.isBlocked());
	}

	@Test
	void testLoginUser_Success() throws UserApplicationException {
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@example.com");
		userLoginRequestDto.setPassword("correctpassword");

		Users user = new Users();
		user.setPassword("correctpassword");
		user.setNumberOfAttempts(3);
		user.setBlocked(false);

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		Users loggedInUser = loginServiceImpl.loginUser(userLoginRequestDto);

		assertEquals(0, user.getNumberOfAttempts());
		assertEquals(true, user.isLoggedIn());
		assertEquals(loggedInUser, user);
	}

	@Test
	void testLoginUser_UnblockAfterDuration() throws UserApplicationException {
		UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@example.com");
		userLoginRequestDto.setPassword("correctpassword");

		Users user = new Users();
		user.setPassword("correctpassword");
		user.setNumberOfAttempts(5);
		user.setBlocked(true);
		user.setBlockedDate(LocalDateTime.now().minusHours(25));

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		Users loggedInUser = loginServiceImpl.loginUser(userLoginRequestDto);

		assertEquals(0, user.getNumberOfAttempts());
		assertEquals(false, user.isBlocked());
		assertEquals(null, user.getBlockedDate());
		assertEquals(true, user.isLoggedIn());
		assertEquals(loggedInUser, user);
	}
}
