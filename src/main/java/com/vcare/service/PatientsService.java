package com.vcare.service;

import java.util.List;


import com.vcare.beans.HospitalBranch;
import com.vcare.beans.Patients;

public interface PatientsService {
	List<Patients> getAllPatients();

	void deletePatientById(int patientsId);

	Patients addNewPatient(Patients patients);

	void updatePatient(Patients patients);

	Patients getPatient(String email, String password);

	Patients getPatientById(int patientId);

	public List<Patients> getBranchPatients(HospitalBranch hospitalBranchId);
	
	String validateduplicate(String patientMailId);
	
	Patients findByMail(String patientMailId);
	
	public String sendSimpleEmail(String toEmail,
			String body,
			String subject);
}
