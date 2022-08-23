package com.vcare.service;

import java.util.List;

import com.vcare.beans.Rating;

public interface DoctorRatingService {
	List<Rating> GetAllDoctorRating();

	Rating getDoctorRatingById(long id);

	Rating addDoctorRating(Rating doctorRating);

	void updateDoctorRating(Rating doctorRating);

	void deleteDoctorRatingById(long id);
}
