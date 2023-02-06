package com.rihs.service;

import com.rihs.binding.CitizenRegistrationApplicationRequest;
import com.rihs.entity.CitizenRegistrationApplication;

public interface ICitizenRegistrationApplicationService {

	String registerCitizenApplication(CitizenRegistrationApplicationRequest request);

	CitizenRegistrationApplication getApplication(Long appId);
}
