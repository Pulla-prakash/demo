package com.vcare.beans;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//Prakash
@Entity
@Table(name = "hospitalbranch")
public class HospitalBranch {
	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	private int hospitalBranchId;
	private String hospitalBranchName;
	private String hospitalBranchLocation;
	private long hospitalBranchNumber;
	private String hospitalBranchAddress;
	private int hospitalBranchPincode;
	private String hospitalBranchState;
	private String hospitalBranchCity;
	private String hospitalBranchEmailId;
	private String hospitalBranchLicense;
	private String branchDescription;
	private String branchType;
	private float latitude;
	private float longitude;
	@Lob
	private String hospitalBranchImage;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private Character isactive;

	private String createdBy;

	private String updateBy;

	private LocalDate updated;
	@Column(columnDefinition = "date default now()")
	private LocalDate created;
	
	 @OneToMany(mappedBy = ("hospitalBranch"), cascade = CascadeType.ALL)
	    private List<DoctorAvailability> doctorAvailability;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "hospitalId")
	private Hospital hospitals;

	@OneToMany(mappedBy = ("hospitalbranch"), cascade = CascadeType.ALL)
	private List<Services> services;
	
	@OneToMany(mappedBy = ("hospitalBranch"), cascade = CascadeType.ALL)
	private List<Employees> employees;
	
	@OneToMany(mappedBy=("hospitalBranch"),cascade = CascadeType.ALL)
	private List<Department> department;

	@OneToMany(mappedBy=("hospitalbranch"),cascade = CascadeType.ALL)
	private List<Ambulance> ambulance;
	
	@OneToMany(mappedBy=("hospitalbranch"),cascade = CascadeType.ALL)
	private List<AmbulanceDriverAssosiation> AmbulanceDriverAssosiation;
	
	
	public String getHospitalBranchAddress() {
		return hospitalBranchAddress;
	}

	public void setHospitalBranchAddress(String hospitalBranchAddress) {
		this.hospitalBranchAddress = hospitalBranchAddress;
	}
	

	public List<AmbulanceDriverAssosiation> getAmbulanceDriverAssosiation() {
		return AmbulanceDriverAssosiation;
	}

	public void setAmbulanceDriverAssosiation(List<AmbulanceDriverAssosiation> ambulanceDriverAssosiation) {
		AmbulanceDriverAssosiation = ambulanceDriverAssosiation;
	}
	

	public List<DoctorAvailability> getDoctorAvailability() {
		return doctorAvailability;
	}

	public void setDoctorAvailability(List<DoctorAvailability> doctorAvailability) {
		this.doctorAvailability = doctorAvailability;
	}

	public List<Ambulance> getAmbulance() {
		return ambulance;
	}

	public void setAmbulance(List<Ambulance> ambulance) {
		this.ambulance = ambulance;
	}

	public List<Employees> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}

	public int getHospitalBranchPincode() {
		return hospitalBranchPincode;
	}

	public void setHospitalBranchPincode(int hospitalBranchPincode) {
		this.hospitalBranchPincode = hospitalBranchPincode;
	}
	

	public String getBranchDescription() {
		return branchDescription;
	}

	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getHospitalBranchImage() {
		return hospitalBranchImage;
	}

	public void setHospitalBranchImage(String hospitalBranchImage) {
		this.hospitalBranchImage = hospitalBranchImage;
	}

	public String getHospitalBranchState() {
		return hospitalBranchState;
	}

	public void setHospitalBranchState(String hospitalBranchState) {
		this.hospitalBranchState = hospitalBranchState;
	}

	public String getHospitalBranchCity() {
		return hospitalBranchCity;
	}

	public void setHospitalBranchCity(String hospitalBranchCity) {
		this.hospitalBranchCity = hospitalBranchCity;
	}

	public String getHospitalBranchEmailId() {
		return hospitalBranchEmailId;
	}

	public void setHospitalBranchEmailId(String hospitalBranchEmailId) {
		this.hospitalBranchEmailId = hospitalBranchEmailId;
	}

	public String getHospitalBranchLicense() {
		return hospitalBranchLicense;
	}

	public void setHospitalBranchLicense(String hospitalBranchLicense) {
		this.hospitalBranchLicense = hospitalBranchLicense;
	}

	public Character getIsactive() {
		return isactive;
	}

	public void setIsactive(Character isactive) {
		this.isactive = isactive;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
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

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public int getHospitalBranchId() {
		return hospitalBranchId;
	}

	public void setHospitalBranchId(int hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}

	public String getHospitalBranchName() {
		return hospitalBranchName;
	}

	public void setHospitalBranchName(String hospitalBranchName) {
		this.hospitalBranchName = hospitalBranchName;
	}

	public String getHospitalBranchLocation() {
		return hospitalBranchLocation;
	}

	public void setHospitalBranchLocation(String hospitalBranchLocation) {
		this.hospitalBranchLocation = hospitalBranchLocation;
	}

	public long getHospitalBranchNumber() {
		return hospitalBranchNumber;
	}

	public void setHospitalBranchNumber(long hospitalBranchNumber) {
		this.hospitalBranchNumber = hospitalBranchNumber;
	}

	public Hospital getHospitals() {
		return hospitals;
	}

	public void setHospitals(Hospital hospitals) {
		this.hospitals = hospitals;
	}

}
