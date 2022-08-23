package com.vcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}