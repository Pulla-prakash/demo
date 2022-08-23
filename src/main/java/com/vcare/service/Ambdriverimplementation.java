package com.vcare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.repository.Ambdriversrepository;
@Repository
public class Ambdriverimplementation implements AmbulanceDriverService {
    @Autowired
    Ambdriversrepository ambdriversrepository;
    
    
	@Override
	public List<AmbulanceDriverAssosiation> getAllAambulancedriver() {

		return ambdriversrepository.findAll();
	}

	@Override
	public AmbulanceDriverAssosiation addAmbulancedriver(AmbulanceDriverAssosiation ambDriverAssosiation) {
		return ambdriversrepository.save(ambDriverAssosiation);
	}

	@Override
	public void UpdateAmbulancedriver(AmbulanceDriverAssosiation ambDriverAssosiation) {
		ambdriversrepository.save(ambDriverAssosiation);
	}

	@Override
	public void deleteAmbulancedriverById(int ambdriversId) {
		ambdriversrepository.deleteById(ambdriversId);
	}

	@Override
	public AmbulanceDriverAssosiation getById(int ambdriversId) {
		return ambdriversrepository.getById(ambdriversId);
	}

	@Override
	public AmbulanceDriverAssosiation getAmbdriverId(int ambdriversId) {
		
		return ambdriversrepository.findById(ambdriversId).get();
	}

	@Override
	public String validateduplicate(String ambulancetype) {
		
		return ambdriversrepository.findByAmbulanceIdDuplicate(ambulancetype);
	}

}
