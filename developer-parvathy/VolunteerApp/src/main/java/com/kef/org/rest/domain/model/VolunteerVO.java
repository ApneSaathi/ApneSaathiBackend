package com.kef.org.rest.domain.model;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.model.VolunteerRating;

public class VolunteerVO {

	private Integer idvolunteer;

	private String phoneNo;

	private Integer adminId;

	private String firstName;

	private String lastName;

	private String email;

	private String gender;

	private String State;

	private String District;

	private String Block;

	private String address;

	private String Village;

	private String assignedtoFellow;

	private String assignedtoFellowContact;

	private byte[] pic;

	// 1=Volunteer 2=Staff Member 3=District Admin 4=Master Admin

	private Integer role;

	private Float rating;
	
	private Integer count_SrCitizen;
	
	private Integer limit;
	
	private Integer pagenumber;
	
	private String status;
	
	 private String filterState;
	 private String filterDistrict;
	 private String filterBlock;
	 private  String sortBy;
	 private String sortType;
	// private List <VolunteerAssignment> volunteercallList;
	 private List <VolunteerAssignment> volunteercallList;
	 private List<VolunteerRating> volunteerRatingList;
	 private List<VolunteerAssignment> srCitizenList;
	 private List<Integer> excludeIds; 
	 
	 

	public List<Integer> getExcludeIds() {
		return excludeIds;
	}

	public void setExcludeIds(List<Integer> excludeIds) {
		this.excludeIds = excludeIds;
	}

	public List<VolunteerAssignment> getSrCitizenList() {
		return srCitizenList;
	}

	public void setSrCitizenList(List<VolunteerAssignment> srCitizenList) {
		this.srCitizenList = srCitizenList;
	}

	public List<VolunteerAssignment> getVolunteercallList() {
		return volunteercallList;
	}

	public void setVolunteercallList(List<VolunteerAssignment> volunteercallList) {
		this.volunteercallList = volunteercallList;
	}

	public List<VolunteerRating> getVolunteerRatingList() {
		return volunteerRatingList;
	}

	public void setVolunteerRatingList(List<VolunteerRating> volunteerRatingList) {
		this.volunteerRatingList = volunteerRatingList;
	}

	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}

	/*
	 * public List<VolunteerAssignment> getVolunteercallList() { return
	 * volunteercallList; } public void
	 * setVolunteercallList(List<VolunteerAssignment> volunteercallList) {
	 * this.volunteercallList = volunteercallList; }
	 */
	public String getphoneNo() {
		return phoneNo;
	}

	public void setphoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getBlock() {
		return Block;
	}

	public void setBlock(String block) {
		Block = block;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getVillage() {
		return Village;
	}

	public void setVillage(String village) {
		Village = village;
	}

	public String getAssignedtoFellow() {
		return assignedtoFellow;
	}

	public void setAssignedtoFellow(String assignedtoFellow) {
		this.assignedtoFellow = assignedtoFellow;
	}

	public String getAssignedtoFellowContact() {
		return assignedtoFellowContact;
	}

	public void setAssignedtoFellowContact(String assignedtoFellowContact) {
		this.assignedtoFellowContact = assignedtoFellowContact;

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

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getCount_SrCitizen() {
		return count_SrCitizen;
	}

	public void setCount_SrCitizen(Integer count_SrCitizen) {
		this.count_SrCitizen = count_SrCitizen;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilterState() {
		return filterState;
	}

	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}

	public String getFilterDistrict() {
		return filterDistrict;
	}

	public void setFilterDistrict(String filterDistrict) {
		this.filterDistrict = filterDistrict;
	}

	public String getFilterBlock() {
		return filterBlock;
	}

	public void setFilterBlock(String filterBlock) {
		this.filterBlock = filterBlock;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	

}
