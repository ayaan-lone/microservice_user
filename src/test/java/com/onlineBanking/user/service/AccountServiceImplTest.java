package com.onlineBanking.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.service.impl.AccountServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    private Users user;

    @BeforeEach
    public void setUp() {
        user = new Users();
        user.setId(1L);
    }

    @Test
    public void testCreateAccount_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
            accountServiceImpl.createAccount(1L, 1001L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        assertEquals(ConstantUtil.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testCreateAccount_Success() throws UserApplicationException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        accountServiceImpl.createAccount(1L, 1001L);

        String expectedUrl = ConstantUtil.CREATE_ACCOUNT_API_URL + "?userId=1&accountId=1001";
        verify(restTemplate).postForObject(expectedUrl, null, Object.class);
    }

    @Test
    public void testCreateAccount_RestClientException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doThrow(new RestClientException("Service Unavailable")).when(restTemplate).postForObject(anyString(), anyString(), any());

        RestClientException exception = assertThrows(RestClientException.class, () -> {
            accountServiceImpl.createAccount(1L, 1001L);
        });

        assertEquals("Service Unavailable", exception.getMessage());
    }
}
