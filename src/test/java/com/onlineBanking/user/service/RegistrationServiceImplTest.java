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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.onlineBanking.user.dao.RegisterUserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.UserRegistrationRequestDto;
import com.onlineBanking.user.service.impl.RegistrationServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {
	@Mock
	RegisterUserRepository registerUserRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private RegistrationServiceImpl registrationServiceImpl;

	@Test
	void testRegisterUser_Should_Throw_UserApplication_Exception() {
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("test@example.com");
		Users existingUser = new Users();
		Optional<Users> optionalUser = Optional.of(existingUser);
		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(optionalUser);
		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
			registrationServiceImpl.registerUser(userRegistrationRequestDto);
		});

		assertEquals(ConstantUtil.USER_ALREADY_PRESENT, exception.getMessage());

	}
	
	
	@Test
	void testRegisterUser_Success() throws UserApplicationException {
		UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto();
		userRegistrationRequestDto.setEmail("test@example.com");

		Users user = new Users();
		Optional<Users> optionalUser = Optional.empty();

		Mockito.when(registerUserRepository.findByEmail("test@example.com")).thenReturn(optionalUser);
		Mockito.when(modelMapper.map(userRegistrationRequestDto, Users.class)).thenReturn(user);
		Mockito.when(registerUserRepository.save(user)).thenReturn(user);

		String response = registrationServiceImpl.registerUser(userRegistrationRequestDto);
		assertEquals("User has been Created", response);
		
	}

}
