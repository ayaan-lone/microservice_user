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
import com.onlineBanking.user.request.ChangePasswordDto;
import com.onlineBanking.user.request.ResetPasswordDto;
import com.onlineBanking.user.request.VerifyOtpDto;
import com.onlineBanking.user.service.impl.ResetPasswordServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordServiceImplTest {

	@Mock
	private RegisterUserRepository registerUserRepository;

	@InjectMocks
	private ResetPasswordServiceImpl resetPasswordServiceImpl;

	@Test
	void testChangePassword_UserNotFound() {
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		changePasswordDto.setEmail("test@example.com");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.changePassword(changePasswordDto);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testChangePassword_InvalidCredentials() {
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		changePasswordDto.setEmail("test@example.com");
		changePasswordDto.setCurrentPassword("wrongpassword");

		Users user = new Users();
		user.setPassword("correctpassword");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.changePassword(changePasswordDto);
		});

		assertEquals(ConstantUtil.INVALID_CREDENTIALS, exception.getMessage());
	}

	@Test
	void testChangePassword_Success() throws UserApplicationException {
		ChangePasswordDto changePasswordDto = new ChangePasswordDto();
		changePasswordDto.setEmail("test@example.com");
		changePasswordDto.setCurrentPassword("correctpassword");
		changePasswordDto.setNewPassword("newpassword");

		Users user = new Users();
		user.setPassword("correctpassword");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		String response = resetPasswordServiceImpl.changePassword(changePasswordDto);

		assertEquals("Password has been changed successfully.", response);
		assertEquals("newpassword", user.getPassword());
	}

	@Test
	void testGenerateOtp_UserNotFound() {
		String email = "test@example.com";

		Mockito.when(registerUserRepository.findByEmail(email)).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.generateOtp(email);
		});
		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testGenerateOtp_Success() throws UserApplicationException {
		String email = "test@example.com";

		Users user = new Users();

		Mockito.when(registerUserRepository.findByEmail(email)).thenReturn(Optional.of(user));

		String otp = resetPasswordServiceImpl.generateOtp(email);

		assertEquals(6, otp.length());
		assertEquals(otp, user.getOtpValue());
	}

	@Test
	void testVerifyOtp_UserNotFound() {
		VerifyOtpDto verifyOtpDto = new VerifyOtpDto();
		verifyOtpDto.setEmail("test@example.com");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.verifyOtp(verifyOtpDto);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testVerifyOtp_AccountBlocked() {
		VerifyOtpDto verifyOtpDto = new VerifyOtpDto();
		verifyOtpDto.setEmail("test@example.com");

		Users user = new Users();
		user.setBlocked(true);

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.verifyOtp(verifyOtpDto);
		});

		assertEquals(ConstantUtil.ACCOUNT_BLOCKED, exception.getMessage());
	}

	@Test
	void testVerifyOtp_OtpMismatchOrExpired() {
		VerifyOtpDto verifyOtpDto = new VerifyOtpDto();
		verifyOtpDto.setEmail("test@example.com");
		verifyOtpDto.setOtp("123456");

		Users user = new Users();
		user.setOtpValue("654321");
		user.setOtpGenerationTime(LocalDateTime.now().minusMinutes(10));

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.verifyOtp(verifyOtpDto);
		});
		assertEquals(ConstantUtil.OTP_MISMATCH_EXCEEDED, exception.getMessage());
	}

	@Test
	void testVerifyOtp_Success() throws UserApplicationException {
		VerifyOtpDto verifyOtpDto = new VerifyOtpDto();
		verifyOtpDto.setEmail("test@example.com");
		verifyOtpDto.setOtp("123456");

		Users user = new Users();
		user.setOtpValue("123456");
		user.setOtpGenerationTime(LocalDateTime.now());

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		String response = resetPasswordServiceImpl.verifyOtp(verifyOtpDto);

		assertEquals("Otp has been verified", response);
		assertEquals(true, user.isOtpVerified());
	}

	@Test
	void testChangePasswordWithOtp_UserNotFound() {
		ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
		resetPasswordDto.setEmail("test@example.com");

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.changePasswordWithOtp(resetPasswordDto);
		});

		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
	}

	@Test
	void testChangePasswordWithOtp_AccountBlocked() {
		ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
		resetPasswordDto.setEmail("test@example.com");

		Users user = new Users();
		user.setBlocked(true);

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			resetPasswordServiceImpl.changePasswordWithOtp(resetPasswordDto);
		});

		assertEquals("User is blocked", exception.getMessage());
	}

	@Test
	void testChangePasswordWithOtp_Success() throws UserApplicationException {
		ResetPasswordDto resetPasswordDto = new ResetPasswordDto();
		resetPasswordDto.setEmail("test@example.com");
		resetPasswordDto.setPassword("newpassword");

		Users user = new Users();

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		String response = resetPasswordServiceImpl.changePasswordWithOtp(resetPasswordDto);

		assertEquals("Password updated", response);
		assertEquals("newpassword", user.getPassword());
		assertEquals(null, user.getOtpValue());
		assertEquals(null, user.getOtpGenerationTime());
	}
}
