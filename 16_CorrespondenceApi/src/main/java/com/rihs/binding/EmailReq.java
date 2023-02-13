package com.rihs.binding;

import lombok.Data;

@Data
public class EmailReq {

	private String emailTo;
	private String emailFrom;
	private String emailSubject;
	private String emailText;
	//private byte[] fileContent;
	private String fileName;
}
