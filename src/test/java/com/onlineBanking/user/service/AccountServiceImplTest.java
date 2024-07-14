package com.onlineBanking.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.service.impl.AccountServiceImpl;
import com.onlineBanking.user.util.ConstantUtil;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    private long userId;
    private long accountId;
    private String url;
 
    @BeforeEach
    void setUp() {
        userId = 1L;
        accountId = 1L;
        url = ConstantUtil.CREATE_ACCOUNT_API_URL + "?userId=" + userId + "&accountId=" + accountId;
    }

    @Test
    void testCreateAccount_Success() {
        accountServiceImpl.createAccount(userId, accountId);
        verify(restTemplate).postForObject(url, null, Object.class);
    }

    @Test
    void testCreateAccount_RestClientException() {
        doThrow(new RestClientException("Service Unavailable")).when(restTemplate).postForObject(url, null, Object.class);

        assertThrows(RestClientException.class, () -> {
            accountServiceImpl.createAccount(userId, accountId);
        });

        verify(restTemplate).postForObject(url, null, Object.class);
    }
}
