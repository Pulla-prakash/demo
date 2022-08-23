package com.vcare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.vcare.beans.Prescription;
import com.vcare.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImp implements PrescriptionService {

	// Prescription Service
	@Autowired
	PrescriptionRepository prescriptionRepository;
	@Autowired JPAStreamer jpaStreamer;

	@Override
	public List<Prescription> getAllPrescription() {
		return prescriptionRepository.findAll();
	}

	@Override
	public Prescription getPrescriptionById(int prescriptionId) {
		return prescriptionRepository.getById(prescriptionId);
	}

	@Override
	public Prescription addPrescription(Prescription prescription) {
		return prescriptionRepository.save(prescription);
	}

	@Override
	public void updatePrescription(Prescription prescription) {
		prescriptionRepository.save(prescription);
	}

	@Override
	public void deletePrescriptionById(int prescriptionId) {
		try {
			prescriptionRepository.deleteById(prescriptionId);
		} catch (DataAccessException ex) {
			throw new RuntimeException(ex.getMessage());
		}

	}

	@Override
	public List<Prescription> getByPid(int pid) {
		// TODO Auto-generated method stub
		return prescriptionRepository.getByPid(pid);
	}
	
	public List<Prescription> prescriptionListByDoctorId(int doctorId){
		return jpaStreamer.stream(Prescription.class).filter(a->doctorId==a.getAppointment().getDoctor().getDoctorId()).collect(Collectors.toList());
	}

	@Override
	public Prescription getByPesid(int pid) {
		return prescriptionRepository.getById(pid);
	}
}
