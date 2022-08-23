package com.vcare.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.DoctorAvailability;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Integer> {
	
	@Query("select al from DoctorAvailability al where al.doctor.doctorId=?1")
	public List<DoctorAvailability> availableDoctor(int doctorId);
	
}

