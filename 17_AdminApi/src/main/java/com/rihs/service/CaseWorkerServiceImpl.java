package com.rihs.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rihs.binding.CaseWorkerReq;
import com.rihs.binding.EmailReq;
import com.rihs.binding.UnlockRequest;
import com.rihs.entity.CaseWorker;
import com.rihs.exception.CaseWorkerNotFoundException;
import com.rihs.repository.CaseWorkerRepository;
import com.rihs.util.EmailUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CaseWorkerServiceImpl implements ICaseWorkerService {

	@Autowired
	private CaseWorkerRepository cwRepo;
	
	@Autowired
	private EmailUtil eUtil;

	public String saveCaseWorker(CaseWorkerReq request) {
		log.info("Entering into saveCaseWorker method");
		CaseWorker cw = new CaseWorker();
		BeanUtils.copyProperties(request, cw);
		// Generate the password
		String password = generateRandomPassword();
		cw.setPassword(password);
		cw.setActive(false);
		Integer id = cwRepo.save(cw).getId();

		// logic to send email if everything goes well
		EmailReq req = new EmailReq();
		req.setEmailFrom("noreply@ies.com");
		req.setEmailTo(cw.getEmail());
		req.setEmailSubject("Unlock IES Account");
		req.setEmailText(setEmailBody("Register_Email.txt", cw));
		eUtil.sendEmail(req);

		log.info("Exiting from saveCaseWorker method");
		return "Case Worker with id: " + id + " saved successfully";
	}

	public List<CaseWorker> getAllCaseWorkers() {
		return cwRepo.findAll();
	}

	public CaseWorker getOneCaseWorker(Integer id) {
		return cwRepo.findById(id)
				.orElseThrow(() -> new CaseWorkerNotFoundException("Case Worker with id: " + id + " does not exist"));
	}

	public String deleteCaseWorker(Integer id) {
		cwRepo.delete(this.getOneCaseWorker(id));
		return "Case Worker with id: " + id + " deleted successfully";
	}

	public String toggleCaseWorker(Integer id) {
		log.info("Entering into toggleCaseWorker method");
		CaseWorker cw = getOneCaseWorker(id);
		String msg = null;
		if (cw.isActive()) {
			cw.setActive(false);
			msg = "Case Worker is de-activated";
		} else {
			cw.setActive(true);
			msg = "Case Worker is activated";
		}
		cwRepo.save(cw);
		log.info("Exiting from toggleCaseWorker method");
		return msg;
	}

	public String unlockCaseWorker(UnlockRequest request) {
		log.info("Entering into unlockCaseWorker method");
		// logic to test if the user email and password match
		Optional<CaseWorker> opt= cwRepo.findByEmailAndPassword(request.getEmail(), request.getOldPassword());
		if (opt.isPresent()) {
			CaseWorker cw = opt.get();
			cw.setPassword(request.getNewPassword());
			cw.setActive(true);
			cwRepo.save(cw);
		} else {
			log.error("Case Worker not found. Email and Password does not match.");
			throw new CaseWorkerNotFoundException("Case Worker not found. Email and Password does not match.");
		}
		log.info("Exiting from CaseWorker method");
		return "Account unlocked, please proceed with login";
	}
	
	// method to generate Random password with length 10 characters and Alphanumeric
	// String
	private String generateRandomPassword() {
		log.info("Entering into generateRandomPassword method");
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		log.info("Exiting from generateRandomPassword method");
		return generatedString;
	}
	
	// Set Email Body
	public String setEmailBody(String filename, CaseWorker cw) {
		log.info("Entering into setEmailBody method");
		StringBuffer sb = new StringBuffer();
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			lines.forEach(line -> {
				line = line.replace("${FName}", cw.getFullName());
				line = line.replace("${Email}", cw.getEmail());
				line = line.replace("${Pwd}", cw.getPassword());
				sb.append(line);
			});
		} catch (IOException e) {
			log.error("Error occurred while opening/reading file: " + filename);
			e.printStackTrace();
		}
		log.info("Exiting from setEmailBody method");
		return sb.toString();
	}
}
