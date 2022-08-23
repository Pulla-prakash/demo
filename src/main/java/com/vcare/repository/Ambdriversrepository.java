package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vcare.beans.AmbulanceDriverAssosiation;
import com.vcare.beans.Employees;


public interface Ambdriversrepository extends JpaRepository<AmbulanceDriverAssosiation, Integer>  {
	
	@Query("select al from AmbulanceDriverAssosiation al where al.hospitalbranch.hospitalBranchId=?1")
//	//String findByAmbulanceIdDuplicate(String ambulancetype);
	  List<AmbulanceDriverAssosiation>ambdriverList(int hbrnachId);
//	
	
	@Query("select al.ambulancetype from AmbulanceDriverAssosiation al where al.ambulancetype=?1")
	String findByAmbulanceIdDuplicate(String doctorMailId);
	
	
}
