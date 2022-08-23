package com.vcare.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

	@Query("select h from Hospital h where lower(h.hospitalName) like %lower(?1)%")
	List<Hospital> findByHospitalName(String hospitalName);

}
