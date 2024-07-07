package com.onlineBanking.user.service;

import com.onlineBanking.user.entity.Users;
import com.onlineBanking.user.exception.UserApplicationException;

public interface LogoutService {
	Users logoutUser(Long id) throws UserApplicationException;

}
