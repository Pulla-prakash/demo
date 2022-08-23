package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.Hospital;
import com.vcare.repository.HospitalRepository;

@Service
public class HospitalServiceImpl implements HospitalService {

	@Autowired
	HospitalRepository hospitalRepository;

	@Override
	public Hospital addHospital(Hospital hop) {
		// TODO Auto-generated method stub
		return hospitalRepository.save(hop);
	}

	@Override
	public Hospital getHospitalById(int hospitalId) {
		// TODO Auto-generated method stub
		return hospitalRepository.findById(hospitalId).get();
	}

	@Override
	public void updateHospital(Hospital hop) {
		// TODO Auto-generated method stub
		this.hospitalRepository.save(hop);
	}

	@Override
	public void deleteHospitalById(int hospitalId) {
		// TODO Auto-generated method stub

		hospitalRepository.deleteById(hospitalId);

	}

	@Override
	public List<Hospital> getAllHospitals() {
		// TODO Auto-generated method stub
		return hospitalRepository.findAll();
	}

	@Override
	public Hospital getAllHospital(int hospitalId) {
		// TODO Auto-generated method stub
		return hospitalRepository.getById(hospitalId);
	}

	@Override
	public List<Hospital> findByHospitalName(String hospitalName) {

		return hospitalRepository.findByHospitalName(hospitalName);
	}

}
