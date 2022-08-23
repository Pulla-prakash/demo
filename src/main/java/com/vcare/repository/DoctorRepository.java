package com.vcare.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Doctor;
import com.vcare.beans.DoctorAvailability;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	@Query("select al from Doctor al where al.doctorMailId=?1 and al.password=?2")
	Doctor findBydoctorMailIdIgnoreCaseAndPassword(String doctorMailId, String password);

	@Query("select al.doctorMailId from Doctor al where al.doctorMailId=?1")
	String findBydoctorMailIdIgnoreCase(String doctorMailId);
	
	@Query("select al from Doctor al WHERE lower(al.doctorMailId)=lower(?1)")
	public Doctor findByEmail(String doctorMailId);
	
	@Query("select al from Doctor al WHERE lower(al.doctorMailId)=lower(?1) and al.doctorMobileNo=?2 and al.Medicallicense=?3") 
	public Doctor findByFields(String doctorMailId,long doctorMobileNo,String Medicallicense);

	@Query("select al from Doctor al where al.hospitalBranchId LIKE %?1% and al.serviceName=?2")
	ArrayList<Doctor>  findByDoctorBranchId(String dbid,String sname);
	
	@Query("select al from DoctorAvailability al where al.doctor.doctorId=?1")
	public List<DoctorAvailability> availableDoctor(int doctorId);

	@Query("select al from Doctor al where al.hospitalBranchId LIKE %?1% and al.serviceName=?2 ")
	public List<Doctor> serviceDoctors(int hospitalBranchId,String serviceName);
	
	@Query("select al from Doctor al where al.hospitalBranchId=?1")
	public List<Doctor> branchDoctors(String hospitalBranchId);



}
