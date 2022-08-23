package com.vcare.service;

import java.util.List;

import com.vcare.beans.Department;

public interface DepartmentService {

	List<Department> getAllDepartments();

	Department addDepartment(Department department);

	void updateDepartment(Department department);

	void deleteDepartmentById(int departmentId);

	Department getDepartmentById(int departmentId);

}
