package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vcare.beans.HospitalBranch;
import com.vcare.beans.Patients;
import com.vcare.repository.PatientsRepository;

@Service
public class PatientsServiceImpl implements PatientsService {

	@Autowired
	PatientsRepository patientsRepository;
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public List<Patients> getAllPatients() {

		return patientsRepository.findAll();
	}

	@Override
	public void deletePatientById(int patientsId) {
		patientsRepository.deleteById(patientsId);

	}

	@Override
	public Patients addNewPatient(Patients patients) {

		return patientsRepository.save(patients);
	}

	@Override
	public void updatePatient(Patients patients) {
		patientsRepository.save(patients);

	}

	@Override
	public Patients getPatient(String email, String password) {
		return patientsRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public Patients getPatientById(int patientId) {

		return patientsRepository.getById(patientId);
	}

	@Override
	public List<Patients> getBranchPatients(HospitalBranch hospitalBranchId) {

		return patientsRepository.getBranchPatients(hospitalBranchId);
	}
	
	@Override
	public String validateduplicate(String patientMailId) {
	return patientsRepository.findByPatientEmail(patientMailId);
	}

	@Override
	public Patients findByMail(String patientMailId) {
		// TODO Auto-generated method stub
		return patientsRepository.findByEmail(patientMailId);
	}
	
	@Override
	public String sendSimpleEmail(String toEmail,
			String body,
			String subject) {

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
