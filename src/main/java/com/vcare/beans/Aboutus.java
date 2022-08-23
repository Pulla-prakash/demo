package com.vcare.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Ram
@Entity
@Table(name = "aboutus")

public class Aboutus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String header;
	private String description;
	private char isActive;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getIsActive() {
		
		
		return isActive;
	}

	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}

}
