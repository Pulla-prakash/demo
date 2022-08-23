package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	@Query(value = "select al from Appointment al where al.doctor.doctorId=?1 and al.date=?2")
	List<Appointment> appointmentListByDoctorid(int doctorId, String date);

	
	@Query(value = "select al from Appointment al where al.doctor.doctorId=?1 and date between ?2 and ?3")
	List<Appointment> applointmentListBetweenDatesByDoctorId(int doctorid, String from, String to);
	
	@Query(value="select al from Appointment al where al.patient.patientId=?1")
	Appointment getallapointmentkinks(int patientid);
	
	@Query(value = "select al from Appointment al where al.doctor.doctorId=?1")
	List<Appointment> appointmentListByDoctorid(int doctorId);
	
	
	 
	  
	  
	  @Query(value =
	  "SELECT appointment_id, created_by, isactive, patient_purpose, slot, update_by, doctor_id, patient_id, consultant_type, link, date, hospital_branch_id,payment_status\r\n"
	  +
	  " FROM public.appointment where doctor_id=?1 and date between ?2 and ?3 ORDER BY date,slot ASC"
	  , nativeQuery = true) List<Appointment> doctorAppointmentList(int doctorId,
	  String from, String to);
	  
	 



@Query(value = "select al from Appointment al where al.patient.patientId=?1 and date between ?2 and ?3 ORDER BY date DESC, slot DESC")
    List<Appointment> applointmentListBetweenDatesByPatientId(int patientId, String from, String to);

}
