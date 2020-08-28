package com.kef.org.rest.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.interfaces.VolunteerInterface;
import com.kef.org.rest.model.SeniorCitizen;
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
	
	
	public List<Volunteer> getVolunteerList(VolunteerVO volunteerStatus){
		
		List<Volunteer> result=null;
		String status=volunteerStatus.getStatus();
		String filterState=volunteerStatus.getFilterState();
		String filterDistrict=volunteerStatus.getFilterDistrict();
		String filterBlock=volunteerStatus.getFilterBlock();
//		String sortBy=volunteerStatus.getSortBy();
		Integer limit;
    	Integer pagenumber;
    	
    	if(volunteerStatus.getLimit()==null && volunteerStatus.getPagenumber()==null) {
    		limit=10;
    		pagenumber=0;
    		
    	}
    	else {
    	limit=volunteerStatus.getLimit();
    	
    	pagenumber=volunteerStatus.getPagenumber();
    	}
    	
    	Pageable ptry=PageRequest.of(pagenumber, limit);
		
    	if(filterState!=null && filterDistrict==null && filterBlock==null) {
    		result=volunteerRespository.findByStatusAndStateIgnoreCase(status, filterState, ptry);

    	}
    	else if(filterDistrict!=null && filterState==null && filterBlock==null ) {
    		result=volunteerRespository.findByStatusAndDistrictIgnoreCase(status, filterDistrict, ptry);
    	}
    	else if(filterDistrict==null && filterState==null && filterBlock!=null) {
    		result=volunteerRespository.findByStatusAndBlockIgnoreCase(status, filterBlock, ptry);
    	}
    	else if(filterState!=null && filterDistrict!=null && filterBlock==null) {
    		result=volunteerRespository.findByStatusAndStateAndDistrictIgnoreCase(status, filterState, filterDistrict,ptry);
    		
    	}
    	else if(filterState!=null && filterDistrict==null && filterBlock!=null) {
    		result=volunteerRespository.findByStatusAndStateAndBlockIgnoreCase(status, filterState, filterBlock, ptry);
    		
    	}
    	else if(filterState==null && filterDistrict!=null && filterBlock!=null) {
    		result=volunteerRespository.findByStatusAndDistrictAndBlockIgnoreCase(status, filterDistrict, filterBlock, ptry);
    		
    	}
    	else if (filterState!=null && filterDistrict!=null && filterBlock!=null){
    		
    		result=volunteerRespository.findByStatusAndStateAndDistrictAndBlockIgnoreCase(status, filterState, filterDistrict, filterBlock, ptry);
    	}
    	else {
    		result=volunteerRespository.findAllByStatusIgnoreCase(status,ptry);
    	}
		
    	
    	
		return result;
	
		
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
