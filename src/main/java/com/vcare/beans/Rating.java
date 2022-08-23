package com.vcare.beans;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Ajith
@Entity
@Table(name = "doctor_rating")
public class Rating {

	@Id
	@GeneratedValue
	private long doctorRatingId;
	private String patientName;
	private long rating;
	private String review;
	// Child
	// For doctor name,age,gender from Patient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctorId")
	private Doctor doctor;

	// Child
	// For doctor name,age,gender from Patient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patientId")
	private Patients patients;

	public Patients getPatients() {
		return patients;
	}

	public void setPatients(Patients patients) {
		this.patients = patients;
	}
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private char isactive;
	private String createdBy;
	private String updateBy;
	private LocalDate updated;
	private LocalDate created;

	public char getIsactive() {
		return isactive;
	}

	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDate getUpdated() {
		return updated;
	}

	public LocalDate getCreated() {
		return created;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	public long getDoctorRatingId() {
		return doctorRatingId;
	}

	public void setDoctorRatingId(long doctorRatingId) {
		this.doctorRatingId = doctorRatingId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

}
