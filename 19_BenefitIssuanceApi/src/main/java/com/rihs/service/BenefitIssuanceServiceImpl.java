package com.rihs.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihs.binding.EligibilityDetailsResponse;
import com.rihs.consumer.EligibilityDetailsConsumer;
import com.rihs.entity.BenefitIssuance;
import com.rihs.repository.BenefitIssuanceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BenefitIssuanceServiceImpl implements IBenefitIssuanceService{

	@Autowired
	private EligibilityDetailsConsumer consumer;
	
	@Autowired
	private BenefitIssuanceRepository repo;
	
	@Override
	public void generateCsvFile() {
		log.info("Entering into generateCsvFile method");
		List<EligibilityDetailsResponse> edr = consumer.getApprovedEligibilityDetails().getBody();
		StringBuilder sb = new StringBuilder();
		edr.forEach( (e) -> {
				sb.append(e.getCaseNum()+","+e.getHolderName()+","+e.getHolderSsn()+","+e.getPlanName()+","+e.getBenefitAmount()+"\n");
			});
		String filename = "BenefitIssuance-"+LocalDate.now().getMonth()+LocalDate.now().getYear()+".csv";
		BenefitIssuance bi = new BenefitIssuance();
		bi.setIssuanceDetails(sb.toString().getBytes());
		bi.setFilename(filename);
		repo.save(bi);
		try {
			writeIntoCsvFile(filename,sb);
		} catch (IOException exception) {
			log.error("Exception occurred while writing data to csv file " + exception.getMessage());
			exception.printStackTrace();
		}
		log.info("Exiting from generateCsvFile method");
	}

	private void writeIntoCsvFile(String filename, StringBuilder sb) throws IOException {
		log.info("Entering into writeIntoCsvFile method");
		File csvFile = new File(filename);
		FileWriter fileWriter = new FileWriter(csvFile);
		fileWriter.write(sb.toString());
		fileWriter.close();
		log.info("Exiting from writeIntoCsvFile method");
	}

}
