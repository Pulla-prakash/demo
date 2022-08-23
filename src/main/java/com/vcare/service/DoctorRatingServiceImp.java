package com.vcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcare.beans.Rating;
import com.vcare.repository.DoctorRatingRepository;

@Service
public class DoctorRatingServiceImp implements DoctorRatingService {

	@Autowired
	DoctorRatingRepository doctorRatingRepository;

	@Override
	public List<Rating> GetAllDoctorRating() {
		return doctorRatingRepository.findAll();
	}

	@Override
	public Rating getDoctorRatingById(long id) {
		return doctorRatingRepository.getById(id);
	}

	@Override
	public Rating addDoctorRating(Rating doctorRating) {
		return doctorRatingRepository.save(doctorRating);
	}

	@Override
	public void updateDoctorRating(Rating doctorRating) {
		doctorRatingRepository.save(doctorRating);
	}

	@Override
	public void deleteDoctorRatingById(long id) {
		doctorRatingRepository.deleteById(id);
	}

}
