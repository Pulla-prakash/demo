package com.vcare.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//Nayni
@Entity
@Table(name = "Room")
public class Room {
	@Id
	@Column(name = "room_no")
	int roomNo;
	@Column(name = "room_type")
	String roomType;
	@Column(name = "room_floor")
	int roomFloor;
	@Column(name = "room_available")
	boolean roomAvailable;
	@OneToOne(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private InPatient inPatient;
	@Column(columnDefinition = "character(1) DEFAULT 'Y'::bpchar")
	private char isactive;
	private String createdBy;
	private String updateBy;
	private Date updated;
	private Date created;

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getRoomFloor() {
		return roomFloor;
	}

	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}

	public boolean isRoomAvailable() {
		return roomAvailable;
	}

	public void setRoomAvailable(boolean roomAvailable) {
		this.roomAvailable = roomAvailable;
	}

	public InPatient getInPatient() {
		return inPatient;
	}

	public void setInPatient(InPatient inPatient) {
		this.inPatient = inPatient;
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