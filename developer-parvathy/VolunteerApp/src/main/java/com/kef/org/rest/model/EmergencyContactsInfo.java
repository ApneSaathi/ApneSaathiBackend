package com.kef.org.rest.model;

import java.util.List;

public class EmergencyContactsInfo {
	
	private List<EmergencyContacts> emergencyContactsList;
	
	private String statusCode;
	private String message;

	public List<EmergencyContacts> getEmergencyContactsList() {
		return emergencyContactsList;
	}

	public void setEmergencyContactsList(List<EmergencyContacts> emergencyContactsList) {
		this.emergencyContactsList = emergencyContactsList;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
