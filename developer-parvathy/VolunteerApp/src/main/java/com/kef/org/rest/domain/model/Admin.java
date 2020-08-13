package com.kef.org.rest.domain.model;

import java.util.List;

import com.kef.org.rest.model.VolunteerAssignment;

public class Admin {

	private Integer adminId;

	private String mobileNo;

	private String firstName;

	private String lastName;

	private String email;

	private String State;

	private String District;

	private Integer role;

	private String password;
	
	private List<VolunteerVO> volunteerList;
	
	private List<VolunteerAssignment> adminCallList;

	public List<VolunteerAssignment> getAdminCallList() {
		return adminCallList;
	}

	public void setAdminCallList(List<VolunteerAssignment> adminCallList) {
		this.adminCallList = adminCallList;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String district) {
		District = district;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<VolunteerVO> getVolunteerList() {
		return volunteerList;
	}

	public void setVolunteerList(List<VolunteerVO> volunteerList) {
		this.volunteerList = volunteerList;
	}

}
