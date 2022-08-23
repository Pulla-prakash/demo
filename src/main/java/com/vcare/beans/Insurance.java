package com.vcare.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "insurance")
public class Insurance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "insurance_id")
	private int insuranceId;

	String insuranceName;
	String insuranceDescription;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private Character isactive;
	private int createdBy;
	private int updateBy;
	private String termsconditions;
	@Lob

	private String insurencepic;

	public int getInsuranceId() {
		return insuranceId;
	}

	public String getInsurencepic() {
		return insurencepic;
	}

	public void setInsurencepic(String insurencepic) {
		this.insurencepic = insurencepic;
	}

	public void setInsuranceId(int insuranceId) {
		this.insuranceId = insuranceId;
	}

	public String getInsuranceName() {
		return insuranceName;
	}

	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	public String getInsuranceDescription() {
		return insuranceDescription;
	}

	public void setInsuranceDescription(String insuranceDescription) {
		this.insuranceDescription = insuranceDescription;
	}

	public char getIsactive() {
		return isactive;
	}

	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(int updateBy) {
		this.updateBy = updateBy;
	}

	

	public void setImage(Object object) {

	}

	public String getTermsconditions() {
		return termsconditions;
	}

	public void setTermsconditions(String termsconditions) {
		this.termsconditions = termsconditions;
	}

}
