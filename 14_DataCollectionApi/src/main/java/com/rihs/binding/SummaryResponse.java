package com.rihs.binding;

import java.util.List;

import com.rihs.entity.Case;
import com.rihs.entity.Education;
import com.rihs.entity.Income;
import com.rihs.entity.Kid;

import lombok.Data;

@Data
public class SummaryResponse {

	private Case cob;
	private Income iob;
	private Education eob;
	private List<Kid> kob;
}
