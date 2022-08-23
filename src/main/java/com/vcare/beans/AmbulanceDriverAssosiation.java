package com.vcare.beans;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class AmbulanceDriverAssosiation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int ambdriversId;
	private String  ambulancetype;
	private String  driverId;
	private char isactive;
	private String createdBy;
	private String updateBy;
	private Date updated;
	private LocalDate created;
	private String status;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="hospitalBranchId")
	private HospitalBranch hospitalbranch;
	
	
	public HospitalBranch getHospitalbranch() {
		return hospitalbranch;
	}
	public void setHospitalbranch(HospitalBranch hospitalbranch) {
		this.hospitalbranch = hospitalbranch;
	}
	
	public int getAmbdriversId() {
		return ambdriversId;
	}
	public void setAmbdriversId(int ambdriversId) {
		this.ambdriversId = ambdriversId;
	}
	public String getAmbulancetype() {
		return ambulancetype;
	}
	public void setAmbulancetype(String ambulancetype) {
		this.ambulancetype = ambulancetype;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
