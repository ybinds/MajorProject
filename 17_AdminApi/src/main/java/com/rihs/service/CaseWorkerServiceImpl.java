package com.rihs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihs.binding.CaseWorkerReq;
import com.rihs.entity.CaseWorker;
import com.rihs.exception.CaseWorkerNotFoundException;
import com.rihs.repository.CaseWorkerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaseWorkerServiceImpl implements ICaseWorkerService{

	@Autowired
	private CaseWorkerRepository cwRepo;
	
	public String saveCaseWorker(CaseWorkerReq request) {
		log.info("Entering into saveCaseWorker method");
		CaseWorker cw = new CaseWorker();
		BeanUtils.copyProperties(request, cw);
		cw.setActive(true);
		Integer id = cwRepo.save(cw).getId();
		log.info("Exiting from saveCaseWorker method");
		return "Case Worker with id: " + id + " saved successfully";
	}

	public List<CaseWorker> getAllCaseWorkers() {
		return cwRepo.findAll();
	}

	public CaseWorker getOneCaseWorker(Integer id) {
		return cwRepo.findById(id).orElseThrow(() -> new CaseWorkerNotFoundException("Case Worker with id: " + id + " does not exist"));
	}

	public String deleteCaseWorker(Integer id) {
		cwRepo.delete(this.getOneCaseWorker(id));
		return "Case Worker with id: " + id + " deleted successfully";
	}

	public String toggleCaseWorker(Integer id) {
		CaseWorker cw = getOneCaseWorker(id);
		String msg = null;
		if(cw.isActive()) {
			cw.setActive(false);
			msg = "Case Worker is de-activated";
		} else {
			cw.setActive(true);
			msg = "Case Worker is activated";
		}
		cwRepo.save(cw);
		return msg;
	}

}
