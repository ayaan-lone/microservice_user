//package com.onlineBanking.user.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import com.onlineBanking.user.service.impl.CardServiceImpl;
//import com.onlineBanking.user.util.ConstantUtil;
//
//@ExtendWith(MockitoExtension.class)
//public class CardServiceImplTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private CardServiceImpl cardServiceImpl;
//
//    private Long userId;
//    private String last4Digits;
//    private String url;
//
//    @BeforeEach
//    void setUp() {
//        userId = 1L;
//        last4Digits = "1234";
//        url = UriComponentsBuilder.fromHttpUrl(ConstantUtil.CARD_API_URL)
//                .queryParam("userId", userId)
//                .queryParam("last4Digits", last4Digits)
//                .toUriString();
//    }
//
//    @Test
//    void testDeactivateCard_Success() {
//        String expectedResponse = "Card deactivated successfully";
//
//        when(restTemplate.postForObject(url, null, String.class)).thenReturn(expectedResponse);
//
//        String response = cardServiceImpl.deactivateCard(userId, last4Digits);
//
//        assertEquals(expectedResponse, response);
//        verify(restTemplate).postForObject(url, null, String.class);
//    }
//
//    @Test
//    void testDeactivateCard_RestClientException() {
//        doThrow(new RestClientException("Service Unavailable")).when(restTemplate).postForObject(url, null, String.class);
//
//        assertThrows(RestClientException.class, () -> {
//            cardServiceImpl.deactivateCard(userId, last4Digits);
//        });
//
//        verify(restTemplate).postForObject(url, null, String.class);
//    }
//}
