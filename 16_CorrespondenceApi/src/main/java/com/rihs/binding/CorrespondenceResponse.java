package com.rihs.binding;

import lombok.Data;

@Data
public class CorrespondenceResponse {

	private Integer totalTriggers;
	private Integer successTriggers;
	private Integer failedTriggers;
}
