package com.vcare.beans;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DoctorAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int availabilityId;
	private String startTimings;
	private String endTimings;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "doctorId")
	private Doctor doctor;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private  HospitalBranch hospitalBranch;

	public HospitalBranch getHospitalBranch() {
		return hospitalBranch;
	}

	public void setHospitalBranch(HospitalBranch hospitalBranch) {
		this.hospitalBranch = hospitalBranch;
	}

	public int getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(int availabilityId) {
		this.availabilityId = availabilityId;
	}

	public String getStartTimings() {
		return startTimings;
	}

	public void setStartTimings(String startTimings) {
		this.startTimings = startTimings;
	}

	public String getEndTimings() {
		return endTimings;
	}

	public void setEndTimings(String endTimings) {
		this.endTimings = endTimings;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
