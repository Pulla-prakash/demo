package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vcare.beans.ContractEmployees;
import com.vcare.repository.ContractEmployeeRepository;

@Service
public class ContractEmployeeServiceImpl implements ContractEmployeesService {
	
	@Autowired
	
	JavaMailSender javaMailSender;
	
	@Autowired
	ContractEmployeeRepository  contactEmployeeRepository;

	@Override
	public List<ContractEmployees> getAllContactEmployee() {
		return contactEmployeeRepository.findAll();
	}

	@Override
	public ContractEmployees addContactEmployee(ContractEmployees contactEmployees) {
		return contactEmployeeRepository.save(contactEmployees);
	}

	@Override
	public void updateContactEmployee(ContractEmployees contactEmployees) {
		
		contactEmployeeRepository.save(contactEmployees);
	}

	@Override
	public void deleteContactEmployeeId(int id) {
		
		contactEmployeeRepository.deleteById(id);
	}

	@Override
	public ContractEmployees getContactEmployeeById(int id) {
		return contactEmployeeRepository.getById(id);
	}

	public String sendSimpleEmail(String toEmail, String body, String subject) {
		 SimpleMailMessage message = new SimpleMailMessage();
	       message.setFrom("pullaprakash12@gmail.com");
	        message.setTo(toEmail);
	        message.setText(body);
	        message.setSubject(subject);



	       javaMailSender.send(message);
	        System.out.println("Mail Sent...");
		return "succesfulRegistration";
	}

	@Override
	public ContractEmployees getcontractEmployee(String email, String password) {
		return contactEmployeeRepository.findByEmailAndPassword(email, password);
	}

	

}
