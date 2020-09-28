package com.kef.org.rest.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "sr_citizen_info")
@NamedQueries({
		@NamedQuery(name = "SeniorCitizen.fetchByStatus", query = "SELECT s FROM SeniorCitizen s where s.status=?1"),
		@NamedQuery(name = "SeniorCitizen.fetchAllPhoneNumbers", query = "SELECT s.phoneNo from SeniorCitizen s") })
public class SeniorCitizen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDSRCITIZEN")
	private Integer srCitizenId;

	@Column(name = "FIRSTNAME", nullable = false)
	private String firstName;

	@Column(name = "LASTNAME", nullable = false)
	private String lastName;

	@Column(name = "AGE", nullable = false)
	private Integer age;

	@Column(name = "GENDER", nullable = false)
	private char gender;

	@Column(name = "MOBILENO", nullable = false, unique = true)
	private String phoneNo;

	@Column(name = "EMAIL")
	private String emailID;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "DISTRICT", nullable = false)
	private String district;

	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@Column(name = "BLOCK_NAME")
	private String blockName;

	@Column(name = "VILLAGE")
	private String village;

	@Column(name = "CREATED_BY_VOLUNTEERID")
	private Integer volunteerId;

	@Column(name = "REFFERED_BY")
	private String refferedby;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "debordedon", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE")
	private LocalDateTime deboardedOn;

	@Column(name = "REASONS")
	private String reasons;

	public LocalDateTime getDeboardedOn() {
		return deboardedOn;
	}

	public void setDeboardedOn(LocalDateTime deboardedOn) {
		this.deboardedOn = deboardedOn;
	}

	public Integer getSrCitizenId() {
		return srCitizenId;
	}

	public void setSrCitizenId(Integer srCitizenId) {
		this.srCitizenId = srCitizenId;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
