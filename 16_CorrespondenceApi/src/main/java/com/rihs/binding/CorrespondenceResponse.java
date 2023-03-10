package com.rihs.binding;

import lombok.Data;

@Data
public class CorrespondenceResponse {

	private Long totalTriggers;
	private Long successTriggers;
	private Long failedTriggers;
}
