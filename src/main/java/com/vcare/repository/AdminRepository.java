package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select al from Admin al where lower(al.email) = lower(?1) and al.password=?2 ")
	Admin findByEmailIgnoreCaseAndPassword(String email, String password);
	
	
	@Query("select al from Admin al WHERE lower(al.email)=lower(?1)")
	public Admin findByEmail(String email);
	


}
