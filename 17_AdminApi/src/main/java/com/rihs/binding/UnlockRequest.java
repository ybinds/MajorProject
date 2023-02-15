package com.rihs.binding;

import lombok.Data;

@Data
public class UnlockRequest {
	private String email;
	private String oldPassword;
	private String newPassword;
}
