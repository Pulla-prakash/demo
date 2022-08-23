
package com.vcare.service;

import java.util.List;

import com.vcare.beans.Hospital;
import com.vcare.beans.HospitalBranch;

public interface HospitalBranchService {

	HospitalBranch addHospitalbranch(HospitalBranch hosps);

	HospitalBranch getHospitalbranchId(int HospitalbranchId);

	void updateHospitalbranch(HospitalBranch hosps);

	void deleteHospitalbranchById(int HospitalbranchId);

	List<HospitalBranch> getAllHospitalbranch();

	List<HospitalBranch> getBranchList(Hospital hospitalId);

	// Page<HospitalBranch> listAll(int pageNumber);

}
