package com.vcare;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.vcare.service.PatientsService;

@SpringBootApplication
@ComponentScan(basePackages= {"com.vcare.controller","com.vcare.service","com.vcare.utils"})
@EntityScan("com.vcare.beans")
@EnableJpaRepositories("com.vcare.repository")
public class VcareProjectApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(VcareProjectApplication.class, args);
	}
	@EventListener(ApplicationReadyEvent.class)
	public void triggerMail() throws MessagingException {
	//	serviceemail.sendSimpleEmail("ajithnetha@gmail.com","body","subject" );
	}
	
}
