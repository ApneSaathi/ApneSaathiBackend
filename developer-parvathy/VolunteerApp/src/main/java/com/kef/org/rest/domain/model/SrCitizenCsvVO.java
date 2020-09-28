package com.kef.org.rest.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.utils.Constants;

public class SrCitizenCsvVO implements CSVCompatible {

	private String firstName;
	private String lastName;
	private String mobileNo;
	private String email;
	private String gender;
	private String age;
	private String state;
	private String district;
	private String block;
	private String address;
	private String village;
	private String status;
	private String referredBy;
	private String volunteerId;
	private String failureReason;
	private String[] columns;

	@Override
	public String[] getColumns() {
		return this.columns;
	}

	@Override
	public String[] toArray() {
		List<String> attributes = new ArrayList<>();
		for (int i = 0; i < columns.length; i++) {
			Constants.SrCitizenCSVColumns column = Constants.SrCitizenCSVColumns.fromString(columns[i]);
			switch (column) {
			case FIRSTNAME:
				attributes.add(firstName);
				break;
			case LASTNAME:
				attributes.add(lastName);
				break;
			case MOBILE_NO:
				attributes.add(mobileNo);
				break;
			case EMAIL:
				attributes.add(email);
				break;
			case GENDER:
				attributes.add(gender);
				break;
			case AGE:
				attributes.add(age);
				break;
			case STATE_NAME:
				attributes.add(state);
				break;
			case DISTRICT_NAME:
				attributes.add(district);
				break;
			case BLOCK_NAME:
				attributes.add(block);
				break;
			case ADDRESS:
				attributes.add(address);
				break;
			case VILLAGE:
				attributes.add(village);
				break;
			case STATUS:
				attributes.add(status);
				break;
			case REFERRED_BY:
				attributes.add(referredBy);
				break;
			case CREATED_BY_VOLUNTEER_ID:
				attributes.add(volunteerId);
				break;
			case FAILURE_REASON:
				attributes.add(failureReason);
				break;
			default:
				break;
			}
		}
		return attributes.toArray(new String[attributes.size()]);
	}

	public SeniorCitizen toSeniorCitizen() {
		SeniorCitizen srCitizen = new SeniorCitizen();
		srCitizen.setFirstName(firstName);
		srCitizen.setLastName(lastName);
		srCitizen.setPhoneNo(mobileNo);
		srCitizen.setEmailID(email);
		srCitizen.setGender(gender.charAt(0));
		srCitizen.setAge(Integer.valueOf(age));
		srCitizen.setState(state);
		srCitizen.setDistrict(district);
		srCitizen.setBlockName(block);
		srCitizen.setAddress(address);
		srCitizen.setVillage(village);
		srCitizen.setStatus(status);
		srCitizen.setRefferedby(referredBy);
		srCitizen.setVolunteerId(Integer.valueOf(volunteerId));
		return srCitizen;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	public String getVolunteerId() {
		return volunteerId;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

}