package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.MedicalHistory;
import com.vcare.beans.Patients;

@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {

	@Query("select al.historyPath from MedicalHistory al where al.historyPath=?1")
	String selectHistoryPath(String historyPath);

	@Query("select p from MedicalHistory p where p.patient =?1")
	List<MedicalHistory> getMedicalHistory(Patients patientId);

}
