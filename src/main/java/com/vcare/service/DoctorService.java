package com.vcare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.Admin;
import com.vcare.beans.Doctor;
import com.vcare.beans.HospitalBranch;

public interface DoctorService {

	List<Doctor> getAllDoctor();

	void deleteDoctorsById(int doctorId);

	void saveDoctor(Doctor doctors);

	Doctor GetDocotorById(int doctorId);

	void updateDoctor(Doctor doctors);

	Doctor addDoctor(Doctor doctors);

	Doctor getDoctors(String doctorMailId, String password);

	String validateduplicate(String doctorMailId);
	
	Doctor findByMail(String doctorMailId);
	
	void updateDoctor(MultipartFile file,Doctor doctors);
	
	public String sendSimpleEmail(String toEmail,
            String body,
            String subject);

	

}
