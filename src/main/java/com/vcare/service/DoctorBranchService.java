package com.vcare.service;

import java.util.List;

import com.vcare.beans.DoctorBranch;



public interface DoctorBranchService {
	
	List<DoctorBranch> getAllDoctorBranchs();

	DoctorBranch addDoctorBranch(DoctorBranch doctorBranch);

	void updateDoctorBranch(DoctorBranch doctorBranch);

	void deleteDoctorBranchById(long DoctorBranchId);

	DoctorBranch getDepartmentById(long DoctorBranchId);

}
