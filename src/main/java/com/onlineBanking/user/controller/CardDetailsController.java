package com.onlineBanking.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.response.CardDetailResponseDto;
import com.onlineBanking.user.service.CardService;

@RestController
@RequestMapping("/api/v1/user")
public class CardDetailsController {

    private final CardService cardDetailService;

    @Autowired
    public CardDetailsController(CardService cardDetailService) {
        this.cardDetailService = cardDetailService;
    }

    @PostMapping("/card-details")
    public ResponseEntity<List<CardDetailResponseDto>> checkCardDetails(@RequestBody AccountDetailRequestDto cardDetailRequestDto) throws UserApplicationException {
            List<CardDetailResponseDto> responseDto = cardDetailService.checkDetail(cardDetailRequestDto);
            return ResponseEntity.ok(responseDto);
       }
}
