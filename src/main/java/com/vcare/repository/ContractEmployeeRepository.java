package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.ContractEmployees;
import com.vcare.beans.Employees;

@Repository
public interface ContractEmployeeRepository extends JpaRepository<ContractEmployees,Integer> {
	
	@Query("select al from ContractEmployees al where al.email=?1 and al.password=?2")
	ContractEmployees findByEmailAndPassword(String email, String password);
	

}
