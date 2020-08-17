package com.kef.org.rest.model;

import java.util.List;

import com.kef.org.rest.domain.model.VolunteerAssignmentVO;
import com.kef.org.rest.domain.model.VolunteerVO;

public class LoginInfo { 
	
	public LoginInfo() {
		
		// TODO Auto-generated constructor stub
	}

	String statusCode;
	String message;
	Integer id;
	Integer LoginOTP;
	Integer role;
	Integer districtId;
	List<VolunteerVO> volunteers;
	List<VolunteerAssignmentVO> srCitizenList;
	Volunteer volunteer;
	Admin admin;
	com.kef.org.rest.domain.model.Admin adminDomain;
	
	VolunteerAssignment volunteerassignment;
	MedicalandGreivance  medicalandgreivance;
	List<GreivanceTracking> greivanceTrackingList;
	
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
	


	public Integer getIdgrevance() {
		return idgrevance;
	}
	public void setIdgrevance(Integer idgrevance) {
		this.idgrevance = idgrevance;
	}

	Integer idgrevance;
	public Volunteer getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
	public VolunteerAssignment getVolunteerassignment() {
		return volunteerassignment;
	}
	public void setVolunteerassignment(VolunteerAssignment volunteerassignment) {
		this.volunteerassignment = volunteerassignment;
	}
	
	
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<GreivanceTracking> getGreivanceTrackingList() {
		return greivanceTrackingList;
	}
	public void setGreivanceTrackingList(List<GreivanceTracking> greivanceTrackingList) {
		this.greivanceTrackingList = greivanceTrackingList;
	}
	public MedicalandGreivance getMedicalandgreivance() {
		return medicalandgreivance;
	}
	public void setMedicalandgreivance(MedicalandGreivance medicalandgreivance) {
		this.medicalandgreivance = medicalandgreivance;
	}
	public Integer getLoginOTP() {
		return LoginOTP;
	}
	public void setLoginOTP(Integer loginOTP) {
		LoginOTP = loginOTP;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public List<VolunteerVO> getVolunteers() {
		return volunteers;
	}
	public void setVolunteers(List<VolunteerVO> volunteers) {
		this.volunteers = volunteers;
	}
	public List<VolunteerAssignmentVO> getSrCitizenList() {
		return srCitizenList;
	}
	public void setSrCitizenList(List<VolunteerAssignmentVO> srCitizenList) {
		this.srCitizenList = srCitizenList;
	}
	public com.kef.org.rest.domain.model.Admin getAdminDomain() {
		return adminDomain;
	}
	public void setAdminDomain(com.kef.org.rest.domain.model.Admin adminDomain) {
		this.adminDomain = adminDomain;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

}