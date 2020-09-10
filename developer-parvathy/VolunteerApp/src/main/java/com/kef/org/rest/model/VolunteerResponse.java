package com.kef.org.rest.model;

import java.util.List;

import com.kef.org.rest.domain.model.VolunteerVO;

public class VolunteerResponse {

	
	private List<VolunteerVO> volunteers;
	private String message;
	private Integer statusCode;
	private Integer totalVolunteers;



	private VolunteerVO volunteerVO;
	
	
	public VolunteerVO getVolunteerVO() {
		return volunteerVO;
	}
	public void setVolunteerVO(VolunteerVO volunteerVO) {
		this.volunteerVO = volunteerVO;
	}
	
	
	public Integer getTotalVolunteers() {
		return totalVolunteers;
	}
	public void setTotalVolunteers(Integer totalVolunteers) {
		this.totalVolunteers = totalVolunteers;
	}
	public List<VolunteerVO> getVolunteers() {
		return volunteers;
	}
	public void setVolunteers(List<VolunteerVO> volunteers) {
		this.volunteers = volunteers;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
