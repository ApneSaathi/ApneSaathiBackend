package com.kef.org.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.repository.SeniorCitizenRepository;


@Service("srCitizenService")
public class SeniorCitizenService {
	
	@Autowired
	private SeniorCitizenRepository srCitizenRespository;
	
	public List<SeniorCitizen> getSeniorCitizen(SrCitizenVO srCitizenStatus){
	
		String status=srCitizenStatus.getStatus();
    	Integer limit;
    	Integer pagenumber;
    	
    	if(srCitizenStatus.getLimit()==null && srCitizenStatus.getPagenumber()==null) {
    		limit=10;
    		pagenumber=0;
    		
    	}
    	else {
    	limit=srCitizenStatus.getLimit();
    	
    	pagenumber=srCitizenStatus.getPagenumber();
    	}
    	
    	
    	String filterState=srCitizenStatus.getFilterState();
    	String filterDistrict=srCitizenStatus.getFilterDistrict();
    	String filterBlock=srCitizenStatus.getFilterBlock();
    	
		List<SeniorCitizen> result;
		
		Pageable ptry=PageRequest.of(pagenumber, limit);
		if(filterState!=null && filterDistrict==null && filterBlock==null) {
    		result=srCitizenRespository.findByStatusAndStateIgnoreCase(status, filterState, ptry);

    	}
    	else if(filterDistrict!=null && filterState==null && filterBlock==null ) {
    		result=srCitizenRespository.findByStatusAndDistrictIgnoreCase(status, filterDistrict, ptry);
    	}
    	else if(filterDistrict==null && filterState==null && filterBlock!=null) {
    		result=srCitizenRespository.findByStatusAndBlockNameIgnoreCase(status, filterBlock, ptry);
    	}
    	else if(filterState!=null && filterDistrict!=null && filterBlock==null) {
    		result=srCitizenRespository.findByStatusAndStateAndDistrictIgnoreCase(status, filterState, filterDistrict,ptry);
    		
    	}
    	else if(filterState!=null && filterDistrict==null && filterBlock!=null) {
    		result=srCitizenRespository.findByStatusAndStateAndBlockNameIgnoreCase(status, filterState, filterBlock, ptry);
    		
    	}
    	else if(filterState==null && filterDistrict!=null && filterBlock!=null) {
    		result=srCitizenRespository.findByStatusAndDistrictAndBlockNameIgnoreCase(status, filterDistrict, filterBlock, ptry);
    		
    	}
    	else if (filterState!=null && filterDistrict!=null && filterBlock!=null){
    		
    		result=srCitizenRespository.findByStatusAndStateAndDistrictAndBlockNameIgnoreCase(status, filterState, filterDistrict, filterBlock, ptry);
    	}
    	else {
    		result=srCitizenRespository.findAllByStatusIgnoreCase(status,ptry);
    	}
		
		return result;
	}
	
	public void updateStatus(String status, Integer id) {
		
		srCitizenRespository.updateStatus(status, id);
	}
}
