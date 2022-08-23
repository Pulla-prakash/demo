package com.vcare.beans;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//Nayni
@Entity
@Table(name = "out_patient")
public class OutPatient {
	@Id
	@Column(name = "out_patient_id")
	int outPatientId;

	// Child
	// For patient name from Prescription
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prescriptionId")
	private Prescription prescription;

	@Column(name = "date")
	LocalDate date;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private char isactive;
	private String createdBy;
	private String updateBy;
	private Date updated;
	private Date created;

	public int getOutPatientId() {
		return outPatientId;
	}

	public void setOutPatientId(int outPatientId) {
		this.outPatientId = outPatientId;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public char getIsactive() {
		return isactive;
	}

	public void setIsactive(char isactive) {
		this.isactive = isactive;
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

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
