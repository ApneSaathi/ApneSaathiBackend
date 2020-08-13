package com.kef.org.rest.interfaces;

import java.util.List;

import com.kef.org.rest.model.Volunteer;

public interface VolunteerInterface {

	public Integer  findvolunteerId(String mobileno);
	
	public List<Volunteer> findAllVolunteerDetailsByAdminId(Integer adminId);
	
	
}
