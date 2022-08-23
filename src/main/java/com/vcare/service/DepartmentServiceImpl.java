package com.vcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.vcare.beans.Department;
import com.vcare.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired

	DepartmentRepository departmentRepository;

	@Autowired
	JPAStreamer jpaStreamer;
	@Override
	public List<Department> getAllDepartments() {

		return departmentRepository.findAll();
	}

	@Override
	public Department addDepartment(Department department) {

		return departmentRepository.save(department);
	}

	@Override
	public void updateDepartment(Department department) {
		departmentRepository.save(department);
	}

	@Override
	public void deleteDepartmentById(int departmentId) {
		departmentRepository.deleteById(departmentId);
	}

	@Override
	public Department getDepartmentById(int departmentId) {
		return departmentRepository.getById(departmentId);
	}

	public List<Department> departmentByBranch(int branchId){
		return jpaStreamer.stream(Department.class).filter(a->branchId==a.getHospitalBranch().getHospitalBranchId()).collect(Collectors.toList());
	}
}