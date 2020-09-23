package com.kef.org.rest.model;

import java.util.List;

import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.domain.model.VolunteerVO;

public class SrCitizenResponse {

	private Integer idvolunteer;
	List<SeniorCitizen> srCitizenList;
	private Integer role;
	private Integer adminId;
	private String message;
	private List<VolunteerVO> volunteerList;
	private Integer totalSrCitizen;
	private String statusCode;
	private List<SrCitizenVO> listsrCitizen;
	
	
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getTotalSrCitizen() {
		return totalSrCitizen;
	}

	public void setTotalSrCitizen(Integer totalSrCitizen) {
		this.totalSrCitizen = totalSrCitizen;
	}

	public List<SeniorCitizen> getSrCitizenList() {
		return srCitizenList;
		
	}

	public void setSrCitizenList(List<SeniorCitizen> srCitizenList) {
		this.srCitizenList = srCitizenList;
	}

	public List<VolunteerVO> getVolunteerList() {
		return volunteerList;
	}

	public void setVolunteerList(List<VolunteerVO> volunteerList) {
		this.volunteerList = volunteerList;
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

	public List<SrCitizenVO> getListsrCitizen() {
		return listsrCitizen;
	}

	public void setListsrCitizen(List<SrCitizenVO> listsrCitizen) {
		this.listsrCitizen = listsrCitizen;
	}


	
	
}
