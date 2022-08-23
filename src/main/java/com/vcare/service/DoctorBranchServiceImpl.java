package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.DoctorBranch;
import com.vcare.repository.DoctorBranchRepository;

@Service
public class DoctorBranchServiceImpl implements DoctorBranchService {
	@Autowired
	DoctorBranchRepository doctorBranchRepository;

	@Override
	public List<DoctorBranch> getAllDoctorBranchs() {
		return doctorBranchRepository.findAll();
	}

	@Override
	public DoctorBranch addDoctorBranch(DoctorBranch doctorBranch) {

		return doctorBranchRepository.save(doctorBranch);
	}

	@Override
	public void updateDoctorBranch(DoctorBranch doctorBranch) {
		doctorBranchRepository.save(doctorBranch);

	}

	@Override
	public void deleteDoctorBranchById(long DoctorBranchId) {
		doctorBranchRepository.deleteById(DoctorBranchId);

	}

	@Override
	public DoctorBranch getDepartmentById(long DoctorBranchId) {
		return doctorBranchRepository.getById(DoctorBranchId);
	}
	
}
