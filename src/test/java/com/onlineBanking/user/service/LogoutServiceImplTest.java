package com.onlineBanking.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.onlineBanking.user.service.impl.LogoutServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class LogoutServiceImplTest {

	@Mock
	private RegisterUserRepository registerUserRepository;

	@InjectMocks
	private LogoutServiceImpl logoutServiceImpl;

	@Test
	void testLogoutUser_UserNotFound() {
		Long userId = 1L;

		Mockito.when(registerUserRepository.findById(userId)).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			logoutServiceImpl.logoutUser(userId);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testLogoutUser_Success() throws UserApplicationException {
		Long userId = 1L;

		Users user = new Users();
		user.setLoggedIn(true);

		Mockito.when(registerUserRepository.findById(userId)).thenReturn(Optional.of(user));

		Users loggedOutUser = logoutServiceImpl.logoutUser(userId);

		assertEquals(false, user.isLoggedIn());
		assertEquals(loggedOutUser, user);
	}
}
