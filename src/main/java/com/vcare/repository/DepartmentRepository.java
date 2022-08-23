package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	
	@Query(value = "select * from department dept where lower(dept.department_name)=lower('Driver') and dept.hospital_branch_id=:hospitalBranchId", nativeQuery = true)
	Department dept(@Param("hospitalBranchId") int hospitalBranchId);
	
	
	//List<Department> findByName(String departmentName);
}
