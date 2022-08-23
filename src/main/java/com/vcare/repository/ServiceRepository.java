package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

	@Query("select distinct serviceName from Services  ")
	List<String> findDistinctService(List<String> serviceNames);

	@Query("select al from Services al  where  al.serviceName=?1 ")
	List<Services> findService(String serviceNames);
	
	@Query(value = "SELECT al FROM Services al WHERE al.hospitalbranch.hospitalBranchId =?1")
	List<Services> findservicesbyHospitalBranch(Integer id);
	
	@Query("select serviceName from Services al where al.hospitalbranch.hospitalBranchId=?1")
	List <String> BranchServices(int branchId);

}
