package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vcare.beans.DoctorBranch;

@Repository
@Transactional
public interface DoctorBranchRepository extends JpaRepository<DoctorBranch,Long> {
	
	@Modifying
	@Query("delete from DoctorBranch al where al.doctorId=:DoctorId")
	public void doctorBranchDeleteById(@Param("DoctorId") long DoctorId);
}
