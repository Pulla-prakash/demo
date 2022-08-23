package com.vcare.service;

import java.util.List;

import com.vcare.beans.Hospital;

public interface HospitalService {

	Hospital addHospital(Hospital hop);

	Hospital getHospitalById(int hospitalId);

	void updateHospital(Hospital hop);

	void deleteHospitalById(int hospitalId);

	List<Hospital> getAllHospitals();

	Hospital getAllHospital(int hospitalId);

	List<Hospital> findByHospitalName(String hospitalName);

}
