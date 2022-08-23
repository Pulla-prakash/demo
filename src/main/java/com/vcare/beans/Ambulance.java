package com.vcare.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ambulance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ambulanceId;
	private String ambulanceType;
	private String vechileType;
	private String vechilenumber;
	private double charges;
	private char isActive;

	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hospitalBranchId")
	private HospitalBranch hospitalbranch;

	public int getAmbulanceId() {
		return ambulanceId;
	}

	public void setAmbulanceId(int ambulanceId) {
		this.ambulanceId = ambulanceId;
	}

	public HospitalBranch getHospitalbranch() {
		return hospitalbranch;
	}

	public void setHospitalbranch(HospitalBranch hospitalbranch) {
		this.hospitalbranch = hospitalbranch;
	}

	public String getAmbulanceType() {
		return ambulanceType;
	}

	public void setAmbulanceType(String ambulanceType) {
		this.ambulanceType = ambulanceType;
	}

	public String getVechileType() {
		return vechileType;
	}

	public void setVechileType(String vechileType) {
		this.vechileType = vechileType;
	}
	public String getVechilenumber() {
		return vechilenumber;
	}

	public void setVechilenumber(String vechilenumber) {
		this.vechilenumber = vechilenumber;
	}

	public double getCharges() {
		return charges;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	public char getIsActive() {
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
}
