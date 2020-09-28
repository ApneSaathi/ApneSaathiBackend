package com.kef.org.rest.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.utils.Constants;

public class VolunteerCsvVO implements CSVCompatible {

	private String firstName;
	private String lastName;
	private String mobileNo;
	private String email;
	private String gender;
	private String state;
	private String district;
	private String block;
	private String address;
	private String village;
	private String assignedToFellow;
	private String assignedToFellowContact;
	private String status;
	private Integer role;
	private Integer adminId;
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
			Constants.VolunteerCSVColumns column = Constants.VolunteerCSVColumns.fromString(columns[i]);
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
			case STATE_NAME:
				attributes.add(state);
				break;
			case DISTRICT_NAME:
				attributes.add(district);
				break;
			case ASSIGNED_TO_FELLOW:
				attributes.add(assignedToFellow);
				break;
			case ASSIGNED_TO_FELLOW_CONTACT:
				attributes.add(assignedToFellowContact);
				break;
			case STATUS:
				attributes.add(status);
				break;
			case ADDRESS:
				attributes.add(address);
				break;
			case BLOCK_NAME:
				attributes.add(block);
				break;
			case VILLAGE:
				attributes.add(village);
				break;
			case ADMIN_ROLE:
				attributes.add(String.valueOf(role));
				break;
			case ADMIN_ID:
				attributes.add(String.valueOf(adminId));
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

	public Volunteer toVolunteer() {
		Volunteer volunteer = new Volunteer();
		volunteer.setFirstName(firstName);
		volunteer.setLastName(lastName);
		volunteer.setphoneNo(mobileNo);
		volunteer.setEmail(email);
		volunteer.setGender(gender);
		volunteer.setState(state);
		volunteer.setDistrict(district);
		volunteer.setAssignedtoFellow(assignedToFellow);
		volunteer.setAssignedtoFellowContact(assignedToFellowContact);
		volunteer.setStatus(status);
		volunteer.setAddress(address);
		volunteer.setBlock(block);
		volunteer.setVillage(village);
		volunteer.setAdminId(adminId);
		volunteer.setRole(role);
		return volunteer;
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

	public String getAssignedToFellow() {
		return assignedToFellow;
	}

	public void setAssignedToFellow(String assignedToFellow) {
		this.assignedToFellow = assignedToFellow;
	}

	public String getAssignedToFellowContact() {
		return assignedToFellowContact;
	}

	public void setAssignedToFellowContact(String assignedToFellowContact) {
		this.assignedToFellowContact = assignedToFellowContact;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
