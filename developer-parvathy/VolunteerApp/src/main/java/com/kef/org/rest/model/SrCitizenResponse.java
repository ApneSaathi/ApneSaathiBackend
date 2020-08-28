package com.kef.org.rest.model;

import java.util.List;

public class SrCitizenResponse {

	private Integer idvolunteer;
	List<SeniorCitizen> srCitizenList;
	private Integer role;
	private Integer adminId;
	private String message;
	
	public List<SeniorCitizen> getSrCitizenList() {
		return srCitizenList;
		
	}

	public void setSrCitizenList(List<SeniorCitizen> srCitizenList) {
		this.srCitizenList = srCitizenList;
	}

	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	
	
}
