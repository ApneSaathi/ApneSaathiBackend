package com.kef.org.rest.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.kef.org.rest.interfaces.VolunteerInterface;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.repository.VolunteerRepository;


@Service("volunteerService")

public class VolunteerService implements VolunteerInterface{
	@Autowired
	private VolunteerRepository volunteerRespository;
	
	public Integer findvolunteerId(String phoneNo) {
		
		
		return volunteerRespository.fetchByphoneNumber(phoneNo);
	}
	
	public Volunteer findvolunteerDetails(String phoneNo)
	{
		return volunteerRespository.fetchVolunteerDetails(phoneNo);
	}

	@Override
	public List<Volunteer> findAllVolunteerDetailsByAdminId(Integer adminId) {
		
		return volunteerRespository.findAllVolunteerDetailsByAdminId(adminId);
	}
	
	
	/*
	 * private static List<Volunteer> volunteerlist; static{ volunteerlist=
	 * VolunteerDAO.populateDummyVoluteerData(); }
	 * 
	 * public String isVolunteerRegistered(String phone_no) { for(Volunteer
	 * volunteer : volunteerlist){ if(volunteer.getphoneNo().equals(phone_no) ){
	 * return volunteer.getVolunteerId(); }
	 * 
	 * 
	 * }
	 * 
	 * return null;
	 * 
	 * }
	 */
	
}
