package com.vcare.beans;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//Abhilash
@Entity
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doctor_id")
	public int doctorId;
	public String doctorName;
	private String doctorGender;
	private String doctorSpecialization;
	private int doctorExperience;
	private int doctorFee;
	private long doctorMobileNo;
	private String doctorMailId;
	private String password;
	private String Qualification;
	private String About;
	private int age;
	private String Address;
	private String Medicallicense;
	private String serviceName;

	private String userCaptcha;
	
	private String hospitalBranchId;
	
	@Lob
	private String video;
	@Lob
	private String profilepic;
	@Lob
	private String docQrcode;

	@Column(name = "isActive", columnDefinition = " character(1) DEFAULT 'Y'::bpchar", nullable = true)
	private Character isActive;
	private String createdBy;
	private String updateBy;
	@Column(columnDefinition = "date default now()")
	private Date updated;
	@Column(columnDefinition = "date default now()")
	private LocalDate created;
	@Lob
	private String Image;
	

	

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getHospitalBranchId() {
		return hospitalBranchId;
	}

	public void setHospitalBranchId(String hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}

	public String getUserCaptcha() {
		return userCaptcha;
	}

	public void setUserCaptcha(String userCaptcha) {
		this.userCaptcha = userCaptcha;
	}

	@OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
	private List<Rating> rating = new ArrayList<>();

	@OneToMany(mappedBy = "doctor")
	private Set<Appointment> appointment = new HashSet<Appointment>();

	
	private String captchaOne;

	public String getCaptchaOne() {
		return captchaOne;
	}

	public void setCaptchaOne(String captchaOne) {
		this.captchaOne = captchaOne;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorGender() {
		return doctorGender;
	}

	public void setDoctorGender(String doctorGender) {
		this.doctorGender = doctorGender;
	}

	public String getDoctorSpecialization() {
		return doctorSpecialization;
	}

	public void setDoctorSpecialization(String doctorSpecialization) {
		this.doctorSpecialization = doctorSpecialization;
	}

	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	public String getDocQrcode() {
		return docQrcode;
	}

	public void setDocQrcode(String docQrcode) {
		this.docQrcode = docQrcode;
	}

	public int getDoctorFee() {
		return doctorFee;
	}

	public void setDoctorFee(int doctorFee) {
		this.doctorFee = doctorFee;
	}

	public int getDoctorExperience() {
		return doctorExperience;
	}

	public void setDoctorExperience(int doctorExperience) {
		this.doctorExperience = doctorExperience;
	}

	public long getDoctorMobileNo() {
		return doctorMobileNo;
	}

	public void setDoctorMobileNo(long doctorMobileNo) {
		this.doctorMobileNo = doctorMobileNo;
	}

	public String getDoctorMailId() {
		return doctorMailId;
	}

	public void setDoctorMailId(String doctorMailId) {
		this.doctorMailId = doctorMailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}

	public String getQualification() {
		return Qualification;
	}

	public void setQualification(String qualification) {
		Qualification = qualification;
	}

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getMedicallicense() {
		return Medicallicense;
	}

	public void setMedicallicense(String medicallicense) {
		Medicallicense = medicallicense;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Character getIsActive() {
		return isActive;
	}

	public void setIsActive(Character isActive) {
		this.isActive = isActive;
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

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", doctorName=" + doctorName + ", doctorGender=" + doctorGender
				+ ", doctorSpecialization=" + doctorSpecialization + ", doctorExperience=" + doctorExperience
				+ ", doctorMobileNo=" + doctorMobileNo + ", doctorMailId=" + doctorMailId + ", Password=" + password
				+ ", Qualification=" + Qualification + ", About=" + About + ", age=" + age + ", Address=" + Address
				+ ", Medicallicense=" + Medicallicense + ", serviceId=" + serviceName + ", hospitalBranchId="
				+ ", isActive=" + isActive + ", createdBy=" + createdBy + ", updateBy=" + updateBy + ", updated="
				+ updated + ", created=" + created + ", Image=" + Image + ", consultantfee=" + "]";
	}

}
