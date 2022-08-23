package com.vcare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.MedicalHistory;
import com.vcare.beans.Patients;

public interface MedicalHistoryService {

	void saveImage(MultipartFile imageFile) throws Exception;

	void updateMedicalHistory(MedicalHistory medicalHistory);

	MedicalHistory savedata(MedicalHistory object);

	List<MedicalHistory> getAllHistory();

	void deleteImageById(int medicalId);

	MedicalHistory getMedicalById(int medicalId);

	public List<MedicalHistory> getMedicalHistory(Patients patientId);

	String findCorrectHistoryPath(String historyPath);
}
