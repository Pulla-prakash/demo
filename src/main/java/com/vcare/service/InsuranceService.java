package com.vcare.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vcare.beans.Insurance;

public interface InsuranceService {

	// Insurance Table
	List<Insurance> getAllInsurance();

	Insurance getInsuranceById(int insuranceId);

	Insurance addInsurance(Insurance insurance);

	void updateInsurance(Insurance insurance);

	void deleteInsuranceById(int insuranceId);
	
	Insurance saveInsurance(Insurance insurance);
}
