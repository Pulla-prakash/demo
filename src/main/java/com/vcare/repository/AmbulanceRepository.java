package com.vcare.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.vcare.beans.Ambulance;
@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Integer> {
	@Query("select al from Ambulance al where al.hospitalbranch.hospitalBranchId=?1")
	List<Ambulance> getambbyBranchList( int hospitalBranchId);

}
