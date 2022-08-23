package com.vcare.beans;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Nayni
@Entity
@Table(name = "Patients")
public class Patients {

	@Id
	@GeneratedValue
	@Column(name = "patient_id")
	Integer patientId;

	@Column(name = "	patient_mobile")
	long patientMobile;
	@Column(name = "patient_mailId")
	String patientMailId;
	@Column(name = "patient_password")
	String patientPassword;

	@Column(name = "first_name", nullable = true)
	String firstName;
	@Column(name = "last_name", nullable = true)
	String lastName;
	private String captcha;

	private String userCaptcha;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getUserCaptcha() {
		return userCaptcha;
	}

	public void setUserCaptcha(String userCaptcha) {
		this.userCaptcha = userCaptcha;
	}

	@Column(name = "patient_age", nullable = true)
	int patientAge;
	@Column
	String patientGender;
	@Column(name = "	patient_address", nullable = true)
	String patientAddress;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private Character isactive;

	public void setIsactive(Character isactive) {
		this.isactive = isactive;
	}

	@Column(nullable = true)
	private String createdBy;
	@Column(columnDefinition = "date default now()")
	private LocalDate created;
	@Column(nullable = true)
	private String updateBy;
	@Column(nullable = true)
	private Date updated;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalHistory> medical = new HashSet<>();

	@OneToMany(mappedBy = "patients", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rating> rating = new HashSet<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hospitalBranchId")
	public HospitalBranch hospitalBranch;

	public Set<MedicalHistory> getMedical() {
		return medical;
	}

	public void setMedical(Set<MedicalHistory> medical) {
		this.medical = medical;
	}

	public Set<Rating> getRating() {
		return rating;
	}

	public void setRating(Set<Rating> rating) {
		this.rating = rating;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public long getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(long patientMobile) {
		this.patientMobile = patientMobile;
	}

	public HospitalBranch getHospitalBranch() {
		return hospitalBranch;
	}

	public void setHospitalBranch(HospitalBranch hospitalBranch) {
		this.hospitalBranch = hospitalBranch;
	}

	public String getPatientMailId() {
		return patientMailId;
	}

	public void setPatientMailId(String patientMailId) {
		this.patientMailId = patientMailId;
	}

	public String getPatientPassword() {
		return patientPassword;
	}

	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public String getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}

	public String getPatientAddress() {
		return patientAddress;
	}

	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
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

	public Character getIsactive() {
		return isactive;
	}
}
