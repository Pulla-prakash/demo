package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.Insurance;
import com.vcare.repository.InsuranceRepository;

@Service
public class InsuranceServiceImp implements InsuranceService {

	@Autowired
	InsuranceRepository insuranceRepository;

	// Insurance Service
	@Override
	public List<Insurance> getAllInsurance() {
		return insuranceRepository.findAll();
	}

	@Override
	public Insurance getInsuranceById(int insuranceId) {
		return insuranceRepository.getById(insuranceId);
	}

	@Override
	public Insurance addInsurance(Insurance insurance) {
		return insuranceRepository.save(insurance);
	}

	@Override
	public void updateInsurance(Insurance insurance) {
		insuranceRepository.save(insurance);
	}

	@Override
	public void deleteInsuranceById(int insuranceId) {
		try {
			insuranceRepository.deleteById(insuranceId);
		} catch (DataAccessException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	@Override
	public Insurance saveInsurance(Insurance insurance) {
		// TODO Auto-generated method stub
		return insuranceRepository.save(insurance);
	}

	
}
