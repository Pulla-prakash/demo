package com.vcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.vcare.beans.Employees;
import com.vcare.repository.EmployeesRepository;

@Service
public class EmployeesServiceImpl implements EmployeesService {

	@Autowired

	EmployeesRepository employeesRepository;

	@Autowired
	JPAStreamer jpaStreamer;

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public List<Employees> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeesRepository.findAll();
	}

	@Override
	public Employees addEmployees(Employees employees) {
		// TODO Auto-generated method stub
		return employeesRepository.save(employees);
	}

	@Override
	public void UpdateEmployees(Employees employees) {
		// TODO Auto-generated method stub
		employeesRepository.save(employees);
	}

	@Override
	public void deleteEmployeesById(int employeeId) {
		// TODO Auto-generated method stub
		employeesRepository.deleteById(employeeId);
	}

	@Override
	public Employees getById(int employeeId) {
		// TODO Auto-generated method stub
		return employeesRepository.findById(employeeId).get();
	}

	@Override
	public Employees validateduplicate(String email) {
		return employeesRepository.findByEmployeeEmail(email);
	}

	@Override
	public Employees getEmployee(String email, String password) {
		return employeesRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public void saveEmployees(Employees employees) {
		{
			employeesRepository.save(employees);
		}

	}

	public List<Employees> departmentEmployees(int did) {

		return jpaStreamer.stream(Employees.class).filter(a -> did == a.getDepartmentId()).collect(Collectors.toList());
	}

	@Override
	public Employees findByMail(String email) {
		return employeesRepository.findByEmail(email);
	}

	@Override
	public String sendSimpleEmail(String toEmail, String body, String subject) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("pullaprakash12@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);

		javaMailSender.send(message);
		System.out.println("Mail Sent...");
		return "successfulRegistration";
	}
}
