package com.onlineBanking.user.request;

import javax.validation.constraints.NotEmpty;

public class DashboardDetailsRequestDto {
	@NotEmpty(message = "UserId cannot be empty")
	private Long userId;

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}