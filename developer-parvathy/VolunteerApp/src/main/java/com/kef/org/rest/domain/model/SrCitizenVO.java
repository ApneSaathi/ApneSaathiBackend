package com.kef.org.rest.domain.model;

import javax.persistence.Column;

public class SrCitizenVO {

	
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
	 //for pagination
	 private Integer limit;
	 private Integer pagenumber;
	 
	 //sorting and filter by
	 private String filterBy;
	 private String filterByData;
	 
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
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getPagenumber() {
		return pagenumber;
	}
	public void setPagenumber(Integer pagenumber) {
		this.pagenumber = pagenumber;
	}

	 
}
