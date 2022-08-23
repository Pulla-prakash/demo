package com.vcare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.Ambulance;
import com.vcare.beans.Employees;

public interface AmbulanceService {
List<Ambulance> getAllAmbulance();
	
	Ambulance addAmbulance(Ambulance ambulance);

	void UpdateAmbulance(Ambulance ambulance);
	
	void deleteAmbulancById(int ambulanceId );
	
	Ambulance getById(int ambulanceId);
	
	void saveAmbulance(MultipartFile file,Ambulance ambulance);
	
}
