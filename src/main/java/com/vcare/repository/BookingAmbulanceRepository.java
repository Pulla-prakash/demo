package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Appointment;
import com.vcare.beans.BookingAmbulance;
@Repository
public interface BookingAmbulanceRepository extends JpaRepository<BookingAmbulance,Integer>{
	

}
