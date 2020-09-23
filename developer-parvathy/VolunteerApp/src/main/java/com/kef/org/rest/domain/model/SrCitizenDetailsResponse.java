package com.kef.org.rest.domain.model;

import java.util.List;

import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.VolunteerAssignment;

public class SrCitizenDetailsResponse {

	private Integer srCitizenId;
	 private String status;
	 private String firstName;
	 private Integer age;
	 private char gender;
	 private String phoneNo;
	 private String state;
	 private String district;
	 private String address;
	 private String blockName;
	 private String village;   
	 private Integer volunteerId;
	 private String refferedby;
	 private String emailID;
	 private String lastName;
	 private String volunteerFirstName;
	 private String volunteerLastName;
	 private String volunteerContact;
	 private Float volunteerRating;
	 private String talked_with;
	 private String statusCode;
	 private String message;
	 private List <MedicalandGreivance> medicalGreivanceList ;
	 private List <GreivanceTracking> greivanceList;
	 
	public Integer getSrCitizenId() {
		return srCitizenId;
	}
	public void setSrCitizenId(Integer srCitizenId) {
		this.srCitizenId = srCitizenId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Integer getVolunteerId() {
		return volunteerId;
	}
	public void setVolunteerId(Integer volunteerId) {
		this.volunteerId = volunteerId;
	}
	public String getRefferedby() {
		return refferedby;
	}
	public void setRefferedby(String refferedby) {
		this.refferedby = refferedby;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
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
	public String getVolunteerContact() {
		return volunteerContact;
	}
	public void setVolunteerContact(String volunteerContact) {
		this.volunteerContact = volunteerContact;
	}
	public Float getVolunteerRating() {
		return volunteerRating;
	}
	public void setVolunteerRating(Float volunteerRating) {
		this.volunteerRating = volunteerRating;
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
	public List<MedicalandGreivance> getMedicalGreivanceList() {
		return medicalGreivanceList;
	}
	public void setMedicalGreivanceList(List<MedicalandGreivance> medicalGreivanceList) {
		this.medicalGreivanceList = medicalGreivanceList;
	}
	public List<GreivanceTracking> getGreivanceList() {
		return greivanceList;
	}
	public void setGreivanceList(List<GreivanceTracking> greivanceList) {
		this.greivanceList = greivanceList;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTalked_with() {
		return talked_with;
	}
	public void setTalked_with(String talked_with) {
		this.talked_with = talked_with;
	}
	
	 
	 
	 
	
}
