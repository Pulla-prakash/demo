package com.vcare.beans;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Ajith
@Entity
@Table(name = "prescription")
public class Prescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prescription_id")
	private int prescriptionId;

	@Column(name = "prescription")
	private String prescription;

	// This is used in WHERE clause with patients
	@Column(name = "patient_type")
	private String patientType;

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	// Child
	// For HospitalBranch name/location, Doctor Name,patient (name,purpose,age) from
	// Appointment
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointmentId")
	public Appointment appointment;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private char isactive;
	private String createdBy;
	private String updateBy;
	private Date updated;
	private LocalDate created;

	// Parent
	// For patient name to Prescription
	@OneToMany(mappedBy = "prescription")
	private Set<InPatient> inPatient = new HashSet<InPatient>();

	// Parent
	// For patient name to Prescription
	@OneToMany(mappedBy = "prescription")
	private Set<OutPatient> outPatient = new HashSet<OutPatient>();

	public int getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(int prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public String getPresctiption() {
		return prescription;
	}

	public void setPresctiption(String presctiption) {
		this.prescription = presctiption;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
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

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public Set<InPatient> getInPatient() {
		return inPatient;
	}

	public void setInPatient(Set<InPatient> inPatient) {
		this.inPatient = inPatient;
	}

	public Set<OutPatient> getOutPatient() {
		return outPatient;
	}

	public void setOutPatient(Set<OutPatient> outPatient) {
		this.outPatient = outPatient;
	}

}