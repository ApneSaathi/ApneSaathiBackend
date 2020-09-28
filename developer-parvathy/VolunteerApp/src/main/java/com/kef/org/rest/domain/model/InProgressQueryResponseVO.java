package com.kef.org.rest.domain.model;

import java.util.List;

public class InProgressQueryResponseVO {

	private String message;
	private String statusCode;
	private String greivancedesc;
	private String volunteerFirstName;
	private String volunteerLastName;
	private String srCitizenFirstName;
	private String srCitizenLastName;
	private List<ProgressUpdates> progressUpdates;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getGreivancedesc() {
		return greivancedesc;
	}
	public void setGreivancedesc(String greivancedesc) {
		this.greivancedesc = greivancedesc;
	}
	public String getVolunteerFirstName() {
		return volunteerFirstName;
	}
	public void setVolunteerFirstName(String volunteerFirstName) {
		this.volunteerFirstName = volunteerFirstName;
	}
	public String getVolunteerLastName() {
		return volunteerLastName;
	}
	public void setVolunteerLastName(String volunteerLastName) {
		this.volunteerLastName = volunteerLastName;
	}
	public String getSrCitizenFirstName() {
		return srCitizenFirstName;
	}
	public void setSrCitizenFirstName(String srCitizenFirstName) {
		this.srCitizenFirstName = srCitizenFirstName;
	}
	public String getSrCitizenLastName() {
		return srCitizenLastName;
	}
	public void setSrCitizenLastName(String srCitizenLastName) {
		this.srCitizenLastName = srCitizenLastName;
	}
	public List<ProgressUpdates> getProgressUpdates() {
		return progressUpdates;
	}
	public void setProgressUpdates(List<ProgressUpdates> progressUpdates) {
		this.progressUpdates = progressUpdates;
	}
	
	
	
}
