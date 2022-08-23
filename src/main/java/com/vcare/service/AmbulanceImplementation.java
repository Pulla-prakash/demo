package com.vcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.vcare.beans.Ambulance;
import com.vcare.repository.AmbulanceRepository;
@Repository
public class AmbulanceImplementation  implements AmbulanceService{
   
	@Autowired
	
	AmbulanceRepository ambulancerepo;
	
	@Autowired
	JPAStreamer jpaStreamer;
	
	@Override
	public List<Ambulance> getAllAmbulance() {
		
		return ambulancerepo.findAll();
	}

	@Override
	public Ambulance addAmbulance(Ambulance ambulance) {
		return ambulancerepo.save(ambulance);
	}

	@Override
	public void UpdateAmbulance(Ambulance ambulance) {
		ambulancerepo.save(ambulance);
	}

	@Override
	public void deleteAmbulancById(int ambulanceId) {
		ambulancerepo.deleteById(ambulanceId);
	}

	@Override
	public Ambulance getById(int ambulanceId) {
		return ambulancerepo.getById(ambulanceId);
	}

	@Override
	public void saveAmbulance(MultipartFile file, Ambulance ambulance) {
		ambulancerepo.save(ambulance);
	}
	
	public List<Ambulance> ambulanceByBranch(int branchId){
        return jpaStreamer.stream(Ambulance.class).filter(a->branchId==a.getHospitalbranch().getHospitalBranchId()).collect(Collectors.toList());
    }

}
