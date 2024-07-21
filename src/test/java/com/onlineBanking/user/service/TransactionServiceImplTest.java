//package com.onlineBanking.user.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import com.onlineBanking.user.dao.UserRepository;
//import com.onlineBanking.user.entity.Users;
//import com.onlineBanking.user.exception.UserApplicationException;
//import com.onlineBanking.user.request.TransactionDto;
//import com.onlineBanking.user.service.impl.TransactionServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class TransactionServiceImplTest {
//
//    @InjectMocks
//    private TransactionServiceImpl transactionServiceImpl;
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @Mock
//    private UserRepository userRepository;
//
//    private TransactionDto transactionDto;
//
//    @BeforeEach
//    public void setUp() {
//        transactionDto = new TransactionDto();
//        transactionDto.setUserId(1L);
//        transactionDto.setAmount(100);
//        transactionDto.setTransactionType("credit");
//
//        transactionServiceImpl = new TransactionServiceImpl(restTemplate, userRepository);
//    }
//
//    @Test
//    public void testUpdateTransaction_Success() throws UserApplicationException {
//        Users user = new Users();
//        when(userRepository.findById(transactionDto.getUserId())).thenReturn(Optional.of(user));
//        ResponseEntity<String> responseEntity = new ResponseEntity<>("Transaction successful", HttpStatus.OK);
//        when(restTemplate.exchange(eq("transactionUrl"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
//            .thenReturn(responseEntity);
//
//        String response = transactionServiceImpl.updateTransaction(transactionDto);
//
//        assertEquals("Transaction successful", response);
//        verify(userRepository).findById(transactionDto.getUserId());
//        verify(restTemplate).exchange(eq("transactionUrl"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
//    }
//
//    @Test
//    public void testUpdateTransaction_UserNotFound() {
//        when(userRepository.findById(transactionDto.getUserId())).thenReturn(Optional.empty());
//
//        UserApplicationException exception = assertThrows(UserApplicationException.class, () -> {
//            transactionServiceImpl.updateTransaction(transactionDto);
//        });
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//        assertEquals("User is not present!", exception.getMessage());
//        verify(userRepository).findById(transactionDto.getUserId());
//    }
//
//    @Test
//    public void testUpdateTransaction_RestTemplateError() {
//        Users user = new Users();
//        when(userRepository.findById(transactionDto.getUserId())).thenReturn(Optional.of(user));
//        when(restTemplate.exchange(eq("transactionUrl"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
//            .thenThrow(new RuntimeException("RestTemplate error"));
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            transactionServiceImpl.updateTransaction(transactionDto);
//        });
//
//        assertEquals("RestTemplate error", exception.getMessage());
//        verify(userRepository).findById(transactionDto.getUserId());
//        verify(restTemplate).exchange(eq("transactionUrl"), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class));
//    }
//}
