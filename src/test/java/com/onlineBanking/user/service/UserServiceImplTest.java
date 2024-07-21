//package com.onlineBanking.user.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import com.onlineBanking.user.dao.UserRepository;
//import com.onlineBanking.user.entity.Users;
//import com.onlineBanking.user.exception.UserApplicationException;
//import com.onlineBanking.user.request.UserUpdateDto;
//import com.onlineBanking.user.response.UserPaginationResponse;
//import com.onlineBanking.user.service.impl.UserServiceImpl;
//import com.onlineBanking.user.util.ConstantUtil;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceImplTest {
//
//	@Mock
//	private UserRepository userRepository;
//
//	@InjectMocks
//	private UserServiceImpl userServiceImpl;
//
//	private Users user;
//
//	@BeforeEach
//	void setUp() {
//		user = new Users();
//		user.setFirstName("John");
//		user.setLastName("Doe");
//		user.setEmail("john.doe@example.com");
//		user.setPhoneNumber("1234567890");
//	}
//
//	@Test
//	void testGetAllUsers() {
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Users> userPage = new PageImpl<>(List.of(user), pageable, 1);
//
//		when(userRepository.findAll(pageable)).thenReturn(userPage);
//
//		UserPaginationResponse response = userServiceImpl.getAllUsers(0, 10);
//
//		assertEquals(0, response.getPageNo());
//		assertEquals(10, response.getPageSize());
//		assertEquals(1, response.getPageCount());
//		assertEquals(1, response.getUserList().size());
//		assertEquals("John", response.getUserList().get(0).getFirstName());
//	}
//
//	@Test
//	void testGetUserById_UserNotFound() {
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
//			userServiceImpl.getUserById(1L);
//		});
//
//		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
//	}
//
//	@Test
//	void testGetUserById_Success() throws UserApplicationException {
//		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//		Users foundUser = userServiceImpl.getUserById(1L);
//
//		assertEquals("John", foundUser.getFirstName());
//	}
//
//	@Test
//	void testSearchUser() throws UserApplicationException {
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Users> userPage = new PageImpl<>(List.of(user), pageable, 1);
//
//		when(userRepository.searchUser("John", "john.doe@example.com", "1234567890", pageable)).thenReturn(userPage);
//
//		UserPaginationResponse response = userServiceImpl.searchUser("John", "1234567890", "john.doe@example.com", 0,
//				10);
//
//		assertEquals(0, response.getPageNo());
//		assertEquals(10, response.getPageSize());
//		assertEquals(1, response.getPageCount());
//		assertEquals(1, response.getUserList().size());
//		assertEquals("John", response.getUserList().get(0).getFirstName());
//	}
//
//	@Test
//	void testUpdateUser_UserNotFound() {
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//		UserUpdateDto userUpdateDto = new UserUpdateDto();
//		userUpdateDto.setFirstname("UpdatedName");
//
//		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
//			userServiceImpl.updateUser(1L, userUpdateDto);
//		});
//
//		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
//	}
//
//	@Test
//	void testUpdateUser_Success() throws UserApplicationException {
//		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//		UserUpdateDto userUpdateDto = new UserUpdateDto();
//		userUpdateDto.setFirstname("UpdatedName");
//		userUpdateDto.setLastname("UpdatedLastName");
//		userUpdateDto.setPhoneNumber("0987654321");
//
//		String response = userServiceImpl.updateUser(1L, userUpdateDto);
//
//		assertEquals("User has been updated successfully", response);
//		assertEquals("UpdatedName", user.getFirstName());
//		assertEquals("UpdatedLastName", user.getLastName());
//		assertEquals("0987654321", user.getPhoneNumber());
//	}
//
//	@Test
//	void testDeleteUser_UserNotFound() {
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
//			userServiceImpl.deleteUser(1L);
//		});
//
//		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
//	}
//
//	@Test
//	void testDeleteUser_Success() throws UserApplicationException {
//		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//		String response = userServiceImpl.deleteUser(1L);
//
//		assertEquals("User deleted Successfully", response);
//		verify(userRepository, times(1)).deleteById(1L);
//	}
//
//	@Test
//	void testSoftDeleteUser_UserNotFound() {
//		when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//		UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
//			userServiceImpl.softDeleteUser(1L);
//		});
//
//		assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
//	}
//
//	@Test
//	void testSoftDeleteUser_Success() throws UserApplicationException {
//		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//		String response = userServiceImpl.softDeleteUser(1L);
//
//		assertEquals("User is deleted!", response);
//		assertEquals(true, user.isDeleted());
//	}
//}
