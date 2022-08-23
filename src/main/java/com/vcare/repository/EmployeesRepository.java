package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

	@Query("select al from Employees al where al.email=?1")
	Employees findByEmployeeEmail(String email);
	
	@Query("select al from Employees al where al.email=?1 and al.password=?2")
	Employees findByEmailAndPassword(String email, String password);
	

	@Query("select Al from Employees Al WHERE Al.departmentId =?1") //
	List<Employees> empDriverList(int departmentId);
	
	@Query("select al from Employees al WHERE lower(al.email)=lower(?1)")
    public Employees findByEmail(String email);


}
