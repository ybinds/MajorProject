package com.rihs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegisterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisterAppApplication.class, args);
	}

}
