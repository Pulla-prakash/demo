package com.vcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.vcare.beans.Doctor;
import com.vcare.repository.DoctorRepository;

@Service
public class DoctorImplementation implements DoctorService {
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	JPAStreamer jpaStreamer;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void deleteDoctorsById(int doctorId) {
		doctorRepository.deleteById(doctorId);

	}

	@Override
	public void saveDoctor(Doctor doctors) {
		doctorRepository.save(doctors);
	}

	@Override
	public Doctor GetDocotorById(int doctorId) {

		return doctorRepository.getById(doctorId);
	}

	@Override
	public void updateDoctor(Doctor doctors) {
		doctorRepository.save(doctors);

	}

	@Override
	public Doctor addDoctor(Doctor doctors) {

		return doctorRepository.save(doctors);
	}

	@Override
	public List<Doctor> getAllDoctor() {

		return doctorRepository.findAll();
	}

	@Override
	public String validateduplicate(String doctorMailId) {

		return doctorRepository.findBydoctorMailIdIgnoreCase(doctorMailId);
	}

	@Override
	public Doctor getDoctors(String doctorMailId, String password) {

		return doctorRepository.findBydoctorMailIdIgnoreCaseAndPassword(doctorMailId, password);
	}

	@Override
	public Doctor findByMail(String doctorMailId) {
		// TODO Auto-generated method stub
		return doctorRepository.findByEmail(doctorMailId);
	}

	@Override
	public void updateDoctor(MultipartFile file, Doctor doctors) {
		// TODO Auto-generated method stub
		doctorRepository.save(doctors);
	}
	
	public List<Doctor> doctorByBranch(String branchId){
		return jpaStreamer.stream(Doctor.class).filter(a->branchId==a.getHospitalBranchId()).collect(Collectors.toList());
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
