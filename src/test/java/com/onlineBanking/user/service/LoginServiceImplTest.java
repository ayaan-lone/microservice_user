package com.onlineBanking.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.onlineBanking.user.dao.RegisterUserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserLoginRequestDto;
import com.onlineBanking.user.response.LoginResponseDto;
import com.onlineBanking.user.service.impl.LoginServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

	@Mock
	private RegisterUserRepository registerUserRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private LoginServiceImpl loginServiceImpl;

	private Users user;
	private UserLoginRequestDto userLoginRequestDto;

	@BeforeEach
	void setUp() {
		user = new Users();
		user.setEmail("test@test.com");
		user.setPassword("password");
		user.setNumberOfAttempts(0);
		user.setBlocked(false);
		user.setLoggedIn(false);

		userLoginRequestDto = new UserLoginRequestDto();
		userLoginRequestDto.setEmail("test@test.com");
		userLoginRequestDto.setPassword("password");
	}

	@Test
	void testLoginUser_UserNotFound() {
		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testLoginUser_UserBlocked_BlockDurationNotPassed() {
		user.setBlocked(true);
		user.setBlockedDate(LocalDateTime.now().minusHours(1));

		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.ACCOUNT_BLOCKED, exception.getMessage());
	}

	@Test
	void testLoginUser_UserBlocked_BlockDurationPassed() throws UserApplicationException {
		user.setBlocked(true);
		user.setBlockedDate(LocalDateTime.now().minusHours(25));

		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		when(modelMapper.map(user, LoginResponseDto.class)).thenReturn(loginResponseDto);

		LoginResponseDto response = loginServiceImpl.loginUser(userLoginRequestDto);

		assertEquals(loginResponseDto, response);
		assertEquals(0, user.getNumberOfAttempts());
		assertEquals(false, user.isBlocked());
		assertEquals(true, user.isLoggedIn());
	}

	@Test
	void testLoginUser_InvalidCredentials() {
		userLoginRequestDto.setPassword("wrongpassword");

		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.INVALID_CREDENTIALS, exception.getMessage());
		assertEquals(1, user.getNumberOfAttempts());
	}

	@Test
	void testLoginUser_MaxAttemptsReached() {
		user.setNumberOfAttempts(4);
		userLoginRequestDto.setPassword("wrongpassword");

		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			loginServiceImpl.loginUser(userLoginRequestDto);
		});

		assertEquals(ConstantUtil.INVALID_CREDENTIALS, exception.getMessage());
		assertEquals(5, user.getNumberOfAttempts());
		assertEquals(true, user.isBlocked());
	}

	@Test
	void testLoginUser_Success() throws UserApplicationException {
		when(registerUserRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		when(modelMapper.map(user, LoginResponseDto.class)).thenReturn(loginResponseDto);

		LoginResponseDto response = loginServiceImpl.loginUser(userLoginRequestDto);

		assertEquals(loginResponseDto, response);
		assertEquals(0, user.getNumberOfAttempts());
		assertEquals(true, user.isLoggedIn());
	}
}
