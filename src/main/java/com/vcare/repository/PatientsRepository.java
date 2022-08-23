package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Doctor;
import com.vcare.beans.HospitalBranch;
import com.vcare.beans.Patients;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {

	@Query("select al from Patients al where al.patientMailId=?1 and al.patientPassword=?2")
	Patients findByEmailAndPassword(String patientMailId, String patientPassword);

	@Query("select p from Patients p where p.hospitalBranch =?1")
	List<Patients> getBranchPatients(HospitalBranch hospitalBranchId);
	
	@Query("select al.patientMailId from Patients al where al.patientMailId=?1")
	String findByPatientEmail(String patientMailId);
	
	@Query("select al from Patients al WHERE lower(al.patientMailId)=lower(?1)")
	public Patients findByEmail(String patientMailId);

	@Query("select al from Patients al WHERE lower(al.patientMailId)=lower(?1) and lower(al.firstName)=lower(?2) and al.patientMobile=?3")
	public List<Patients> findPatientforAppointment(String patientMailId,String firstName,long Mobile);
}
