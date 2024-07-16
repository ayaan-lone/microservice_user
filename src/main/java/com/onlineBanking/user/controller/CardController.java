package com.onlineBanking.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineBanking.user.service.CardService;

@RestController
@RequestMapping("/api/v1/")
public class CardController {
    @Autowired
    private CardService cardService;
    
    // Deactivate Card on the basis of userId and last 4 digits of your card
    @PostMapping("deactivate-card")
    public String deactivateCard(@RequestParam Long userId, @RequestParam String last4Digits) {
        return cardService.deactivateCard(userId, last4Digits);
    }
}
