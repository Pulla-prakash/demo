package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.MedicalHistory;
import com.vcare.beans.Patients;
import com.vcare.repository.MedicalHistoryRepository;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
	@Autowired
	MedicalHistoryRepository medicalHistoryRepository;

	@Override
	public void saveImage(MultipartFile imageFile) throws Exception {
// TODO Auto-generated method stub

	}

	@Override
	public void updateMedicalHistory(MedicalHistory medicalHistory) {
		medicalHistoryRepository.save(medicalHistory);

	}

	@Override
	public MedicalHistory savedata(MedicalHistory object) {
// TODO Auto-generated method stub
		return medicalHistoryRepository.save(object);
	}

	@Override
	public MedicalHistory getMedicalById(int medicalId) {

		return medicalHistoryRepository.getById(medicalId);
	}

	@Override
	public List<MedicalHistory> getMedicalHistory(Patients patientId) {

		return medicalHistoryRepository.getMedicalHistory(patientId);
	}

	@Override
	public String findCorrectHistoryPath(String historyPath) {

		return medicalHistoryRepository.selectHistoryPath(historyPath);
	}

	@Override
	public List<MedicalHistory> getAllHistory() {

		return medicalHistoryRepository.findAll();
	}

	@Override
	public void deleteImageById(int medicalId) {
		medicalHistoryRepository.deleteById(medicalId);
	}

}
