package com.vcare.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Ajith
@Entity
@Table(name = "doctor_branch")
public class DoctorBranch {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long doctorBranchId;
	private long doctorId;
	private long hospitalId;
	public long getDoctorBranchId() {
		return doctorBranchId;
	}
	public void setDoctorBranchId(long doctorBranchId) {
		this.doctorBranchId = doctorBranchId;
	}
	
	public long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	
}
