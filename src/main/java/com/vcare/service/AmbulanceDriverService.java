package com.vcare.service;

import java.util.List;

import com.vcare.beans.Ambulance;
import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.beans.HospitalBranch;

public interface AmbulanceDriverService {
	
	
 List<AmbulanceDriverAssosiation> getAllAambulancedriver();
 AmbulanceDriverAssosiation addAmbulancedriver(AmbulanceDriverAssosiation ambDriverAssosiation);
 void UpdateAmbulancedriver(AmbulanceDriverAssosiation ambDriverAssosiation);
 void deleteAmbulancedriverById(int ambdriversId );
 AmbulanceDriverAssosiation getById(int ambdriversId);
 AmbulanceDriverAssosiation getAmbdriverId(int ambdriversId);
 String validateduplicate(String ambulancetype);
}
