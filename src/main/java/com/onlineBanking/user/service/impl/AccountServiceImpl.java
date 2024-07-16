package com.onlineBanking.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.onlineBanking.user.dao.UserRepository;
import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;
import com.onlineBanking.user.request.AccountDetailRequestDto;
import com.onlineBanking.user.response.AccountDetailResponseDto;
import com.onlineBanking.user.service.AccountService;
import com.onlineBanking.user.util.ConstantUtil;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Value("${onlineBanking.account_details.url}")
	private String accountDetailsUrl;

    @Autowired
    private RestTemplate restTemplate;

    private UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createAccount(long userId, long accountId) throws UserApplicationException {
        // Create a URL and pass it on to RestTemplate
        Optional<Users> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND);
        }

        String url = ConstantUtil.CREATE_ACCOUNT_API_URL + "?userId=" + userId + "&accountId=" + accountId;
        restTemplate.postForObject(url, null, Object.class);
    }

    @Override
    public AccountDetailResponseDto checkDetail(AccountDetailRequestDto accountDetailRequestDto)
            throws UserApplicationException {
        Optional<Users> optionalUser = userRepository
                .findByUsername(accountDetailRequestDto.getUsername().toLowerCase().trim());

        // If User does not exist
        if (!optionalUser.isPresent()) {
            throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.USER_NOT_FOUND);
        }

        Users user = optionalUser.get();
        long userId = user.getId();
        System.out.println("User Id Is " + userId);

        if (!accountDetailRequestDto.getPassword().equals(user.getPassword())) {
            throw new UserApplicationException(HttpStatus.UNAUTHORIZED, ConstantUtil.INVALID_CREDENTIALS);
        }

        // Make a call to AccountMicroservice to fetch account details
        String accountMicroserviceUrl = accountDetailsUrl+ userId;

        ResponseEntity<AccountDetailResponseDto> response = restTemplate.exchange(accountMicroserviceUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<AccountDetailResponseDto>() {
                });

        if (response == null || response.getBody() == null) {
            throw new UserApplicationException(HttpStatus.NOT_FOUND, ConstantUtil.NO_ACCOUNT_FOUND);
        }

        AccountDetailResponseDto accountDetail = response.getBody();
        return accountDetail;
    }
}
