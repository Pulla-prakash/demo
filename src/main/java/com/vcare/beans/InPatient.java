package com.vcare.beans;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Nayni
@Entity
@Table(name = "In_patient")
public class InPatient {
	@Id
	@Column(name = "in_patient_id")
	int inPatientId;

	@Column(name = "date_of_admit")
	LocalDate dateOfAdmit;
	@Column(name = "date_of_discharge")
	LocalDate dateOfDischarge;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prescriptionId")
	private Prescription prescription;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_no")
	private Room room;

	private char isactive;
	private String createdBy;
	private String updateBy;
	private Date updated;
	private Date created;

	public int getInPatientId() {
		return inPatientId;
	}

	public void setInPatientId(int inPatientId) {
		this.inPatientId = inPatientId;
	}

	public LocalDate getDateOfAdmit() {
		return dateOfAdmit;
	}

	public void setDateOfAdmit(LocalDate dateOfAdmit) {
		this.dateOfAdmit = dateOfAdmit;
	}

	public LocalDate getDateOfDischarge() {
		return dateOfDischarge;
	}

	public void setDateOfDischarge(LocalDate dateOfDischarge) {
		this.dateOfDischarge = dateOfDischarge;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
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