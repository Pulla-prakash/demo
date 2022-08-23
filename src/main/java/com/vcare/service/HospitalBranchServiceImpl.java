package com.vcare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.Hospital;
import com.vcare.beans.HospitalBranch;
import com.vcare.repository.HospitalBranchRepository;

@Service
public class HospitalBranchServiceImpl implements HospitalBranchService {

	@Autowired
	HospitalBranchRepository hospitalBranchRepository;

	@Override
	public HospitalBranch addHospitalbranch(HospitalBranch hosps) {
		// TODO Auto-generated method stub
		return hospitalBranchRepository.save(hosps);
	}

	@Override
	public HospitalBranch getHospitalbranchId(int HospitalbranchId) {
		// TODO Auto-generated method stub
		return hospitalBranchRepository.getById(HospitalbranchId);
	}

	@Override
	public void updateHospitalbranch(HospitalBranch hosps) {
		// TODO Auto-generated method stub
		hospitalBranchRepository.save(hosps);
	}

	@Override
	public void deleteHospitalbranchById(int HospitalbranchId) {

		hospitalBranchRepository.deleteById(HospitalbranchId);

	}

	@Override
	public List<HospitalBranch> getAllHospitalbranch() {
		List<HospitalBranch> hblist=new ArrayList<>();
		List<HospitalBranch> list=hospitalBranchRepository.findAll();
		for (HospitalBranch app :list ) {
			if (app.getIsactive() == 'y' || app.getIsactive() == 'Y') {
				hblist.add(app);
			}
		}
		return hblist;
	}

	@Override
	public List<HospitalBranch> getBranchList(Hospital hospitalId) {

		return hospitalBranchRepository.getBranchList(hospitalId);
	}
}
