package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Aboutus;

@Repository
public interface AboutusRepository extends JpaRepository<Aboutus,Integer> {

}
