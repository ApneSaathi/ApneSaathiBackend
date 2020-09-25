package com.kef.org.rest.domain.model;

import java.util.List;



public class SrCitizenListResponse {

	private String message;

	private Integer totalSrCitizen;
	private String statusCode;
	private List<SrCitizenVO> srCitizenList;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getTotalSrCitizen() {
		return totalSrCitizen;
	}
	public void setTotalSrCitizen(Integer totalSrCitizen) {
		this.totalSrCitizen = totalSrCitizen;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<SrCitizenVO> getSrCitizenList() {
		return srCitizenList;
	}
	public void setSrCitizenList(List<SrCitizenVO> srCitizenList) {
		this.srCitizenList = srCitizenList;
	}
	
	
	
	
}
