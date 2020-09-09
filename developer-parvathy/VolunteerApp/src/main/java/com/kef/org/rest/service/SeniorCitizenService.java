package com.kef.org.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.SrCitizenResponse;
import com.kef.org.rest.repository.SeniorCitizenRepository;


@Service("srCitizenService")
public class SeniorCitizenService {
	
	@Autowired
	private SeniorCitizenRepository srCitizenRespository;
	
	public SrCitizenResponse getSeniorCitizen(SrCitizenVO srCitizenStatus){
	
		String status=srCitizenStatus.getStatus();
    	Integer limit=null;
    	Integer pagenumber=null;
    	Integer totalSrCitizen;
    	String filterState=srCitizenStatus.getFilterState();
    	String filterDistrict=srCitizenStatus.getFilterDistrict();
    	String filterBlock=srCitizenStatus.getFilterBlock();
    	SrCitizenResponse SrCitizenresponse=new SrCitizenResponse();
		List<SeniorCitizen> result;
		Page<SeniorCitizen> page = null;
		Pageable ptry;
		if(srCitizenStatus.getLimit()==null && srCitizenStatus.getPagenumber()==null) {
			ptry=null;
		}
		else {
			limit=srCitizenStatus.getLimit();
			pagenumber=srCitizenStatus.getPagenumber();
			ptry=PageRequest.of(pagenumber, limit);
		}
		
	
		
		if(filterState!=null && filterDistrict==null && filterBlock==null) {
    		page=srCitizenRespository.findByStatusAndStateIgnoreCase(status, filterState, ptry);
    		result=page.getContent();
    		totalSrCitizen=(int) page.getTotalElements();
    	}
    
    	else if(filterState!=null && filterDistrict!=null && filterBlock==null) {
    		page=srCitizenRespository.findByStatusAndStateAndDistrictIgnoreCase(status, filterState, filterDistrict,ptry);
    		result=page.getContent();
    		totalSrCitizen=(int) page.getTotalElements();
    	}
    	
    	else if (filterState!=null && filterDistrict!=null && filterBlock!=null){
    		
    		page=srCitizenRespository.findByStatusAndStateAndDistrictAndBlockNameIgnoreCase(status, filterState, filterDistrict, filterBlock, ptry);
    		result=page.getContent();
    		totalSrCitizen=(int) page.getTotalElements();
    	}
    	else {
    		page=srCitizenRespository.findAllByStatusIgnoreCase(status,ptry);
    		result=page.getContent();
    		totalSrCitizen=(int) page.getTotalElements();
    	}
		SrCitizenresponse.setSrCitizenList(result);
		SrCitizenresponse.setTotalSrCitizen(totalSrCitizen);
		return SrCitizenresponse;
	}
	
	public void updateStatus(String status, Integer id) {
		
		srCitizenRespository.updateStatus(status, id);
	}
}
