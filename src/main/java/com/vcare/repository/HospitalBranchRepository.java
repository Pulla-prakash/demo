package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Hospital;
import com.vcare.beans.HospitalBranch;

@Repository
public interface HospitalBranchRepository extends JpaRepository<HospitalBranch, Integer> {

	@Query("select h from HospitalBranch h where h.hospitals =?1")
	List<HospitalBranch> getBranchList(Hospital hospitalId);

}
