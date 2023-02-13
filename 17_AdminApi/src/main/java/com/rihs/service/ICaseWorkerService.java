package com.rihs.service;

import java.util.List;

import com.rihs.binding.CaseWorkerReq;
import com.rihs.entity.CaseWorker;

public interface ICaseWorkerService {

	String saveCaseWorker(CaseWorkerReq request);
	List<CaseWorker> getAllCaseWorkers();
	CaseWorker getOneCaseWorker(Integer id);
	String deleteCaseWorker(Integer id);
	String toggleCaseWorker(Integer id);
	
	
}
