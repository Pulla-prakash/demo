package com.vcare.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
	
	/*
	 * @Query
	 * ("SELECT  prescription FROM public.prescription p where p. appointment_id in (select appointment_id from appointment  where patient_id=278)"
	 * )
	 * 
	 * List<Prescription> getByPid(int pid);
	 */
	
	@Query(value = "SELECT *	FROM public.prescription p where p. appointment_id in  \r\n"
			+ "			(select appointment_id from appointment  where patient_id=:pid) order by prescription_id desc;", nativeQuery = true)
	
	List<Prescription> getByPid(@Param("pid") int pid);
	
	
	
}
