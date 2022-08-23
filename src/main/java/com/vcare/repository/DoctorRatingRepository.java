package com.vcare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcare.beans.Rating;

@Repository
public interface DoctorRatingRepository extends JpaRepository<Rating,Long>{

	@Query("Select sum(rating) from Rating r where r.doctor.doctorId=?1")
	public Long sumOfRating(int did);
	
	@Query("Select count(rating) from Rating r where rating in (select rating from Rating al where al.doctor.doctorId=?1 and rating=5)")
	public Long fivecount(int did);
	@Query("Select count(rating) from Rating where rating in (select rating from Rating al where al.doctor.doctorId=?1 and rating=4)")
	public Long fourcount(int did);
	@Query("Select count(rating) from Rating where rating in (select rating from Rating al where al.doctor.doctorId=?1 and rating=3)")
	public Long threecount(int did);
	@Query("Select count(rating) from Rating where rating in (select rating from Rating al where al.doctor.doctorId=?1 and rating=2)")
	public Long twocount(int did);
	@Query("Select count(rating) from Rating where rating in (select rating from Rating al where al.doctor.doctorId=?1 and rating=1)")
	public Long onecount(int did);
	
	@Query(value = "SELECT *\r\n"
			+ " FROM public.doctor_rating\r\n"
			+ " order by created DESC;", nativeQuery = true)
			List<Rating> findAllRatingsInDescOrder();
	
	
	
	@Query("Select al from Rating al where al.doctor.doctorId=?1")
	List<Rating> docrating(int doctorId);


	  @Query("Select count(rating) from Rating r where r.doctor.doctorId=?1")
	    public Long totalReviews(int did);
	
}
