package com.kef.org.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kef.org.rest.controller.VolunteerController;
import com.kef.org.rest.domain.model.SrCitizenDetailsResponse;
import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.domain.model.VolunteerAssignmentVO;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.SrCitizenResponse;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.SeniorCitizenRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRatingRepository;
import com.kef.org.rest.repository.VolunteerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service("srCitizenService")
public class SeniorCitizenService {
	
	@Autowired
	private SeniorCitizenRepository srCitizenRespository;
	
	  @Autowired
	   	private VolunteerAssignmentRepository volunteerassignmentRespository;
	  
	  @Autowired 
	  private VolunteerRepository volunteerRepository;
	  
	  @Autowired
	  private VolunteerRatingRepository volunteerRatingRepository;
	  
	  @Autowired 
	  private MedicalandGreivanceRepository medicalRepository;
	  
	  @Autowired
	  private GreivanceTrackingRepository greivanceRepository;
		public static final Logger logger = LoggerFactory.getLogger(SeniorCitizenService.class);

	
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
	
public List<SeniorCitizen> srCitizenAssignedToVol(Integer idvolunteer) {
		
		List<VolunteerAssignment> vol=new ArrayList<>();
		List<VolunteerAssignmentVO> result=new ArrayList<>();
		Optional<SeniorCitizen> srCitizen;
		SeniorCitizen sr=new SeniorCitizen();
		List<SeniorCitizen> srCitizenList=new ArrayList<>();
		vol=volunteerassignmentRespository.findAllByIdvolunteerAndStatus(idvolunteer,"Assigned");

			if(vol!=null && !vol.isEmpty()) {

				for(VolunteerAssignment va:vol) {
		

						srCitizen=srCitizenRespository.findAllByPhoneNoAndFirstNameIgnoreCase(va.getPhonenosrcitizen(),va.getNamesrcitizen());
						
						
						if(srCitizen.isPresent()) {
							sr=srCitizen.get();
							srCitizenList.add(sr);
						}
				}
				
			}		
					return srCitizenList;
				}
	
	public VolunteerAssignmentVO mapVoluntereAssignmentToEntity(VolunteerAssignment vol, Integer srCitizenId){
		
		VolunteerAssignmentVO volAss=new VolunteerAssignmentVO();
		volAss.setIdvolunteer(vol.getIdvolunteer());
		volAss.setNamesrcitizen(vol.getNamesrcitizen());
		volAss.setPhonenosrcitizen(vol.getPhonenosrcitizen());
		volAss.setCallid(vol.getCallid());
		volAss.setAgesrcitizen(vol.getAgesrcitizen());
		volAss.setGendersrcitizen(vol.getGendersrcitizen());
		volAss.setAddresssrcitizen(vol.getAddresssrcitizen());
		volAss.setEmailsrcitizen(vol.getEmailsrcitizen());
		volAss.setStatesrcitizen(vol.getStatesrcitizen());
		volAss.setDistrictsrcitizen(vol.getDistrictsrcitizen());
		volAss.setBlocknamesrcitizen(vol.getBlocknamesrcitizen());
		volAss.setVillagesrcitizen(vol.getVillagesrcitizen());
		volAss.setAssignedbyMember(vol.getAssignedbyMember());
		volAss.setCallstatusCode(vol.getCallstatusCode());
		volAss.setRemarks(vol.getRemarks());
		volAss.setTalkedwith(vol.getTalkedwith());
		volAss.setRole(vol.getRole());
		volAss.setAdminId(vol.getAdminId());
		volAss.setStatus(vol.getStatus());
		volAss.setIdSrCitizen(srCitizenId);
		
		return volAss;
	}
	
	public SrCitizenDetailsResponse getSrCitizenPersonalInfo(Integer id) {
			
		Optional<SeniorCitizen> srCitizen=srCitizenRespository.findById(id);
		SeniorCitizen srCiti=new SeniorCitizen();
		Integer idvolunteer;
		Volunteer volunteer;
		SrCitizenDetailsResponse srCitizenResponse= new SrCitizenDetailsResponse();
		List<VolunteerAssignment>volAssigned=new ArrayList<VolunteerAssignment>() ;
		List<MedicalandGreivance> medicalList=new ArrayList<>();
		
			if(srCitizen.isPresent()) {
			
				srCiti=srCitizen.get();
				volAssigned=volunteerassignmentRespository.findByPhonenosrcitizenAndStatusIgnoreCase(srCiti.getPhoneNo(),"Assigned");
				
				if(!volAssigned.isEmpty() && volAssigned!=null) {
					
					idvolunteer=volAssigned.get(0).getIdvolunteer();
					volunteer=volunteerRepository.findbyidvolunteer(idvolunteer);
//					medicalList=medicalRepository.findByCallid(volAssigned.get(0).getCallid());
					medicalList=volAssigned.get(0).getMedicalandgreivance();
					
				if(!medicalList.isEmpty() && medicalList!=null) {
						srCitizenResponse.setMedicalGreivanceList(medicalList);
									
					}
				
//					srCitizenResponse.setGreivanceTracking();
//					volunteer.getVolunteercallList();
					srCitizenResponse.setVolunteerId(idvolunteer);
					srCitizenResponse.setVolunteerFirstName(volunteer.getFirstName());
					srCitizenResponse.setVolunteerLastName(volunteer.getLastName());
					srCitizenResponse.setVolunteerContact(volunteer.getphoneNo());
					srCitizenResponse.setVolunteerRating(volunteerRatingRepository.getAvgRating(idvolunteer));
					
				}
				srCitizenResponse.setSrCitizenId(srCiti.getSrCitizenId());
				srCitizenResponse.setFirstName(srCiti.getFirstName());
				srCitizenResponse.setAge(srCiti.getAge());
				srCitizenResponse.setPhoneNo(srCiti.getPhoneNo());
				srCitizenResponse.setState(srCiti.getState());
				srCitizenResponse.setDistrict(srCiti.getDistrict());
				srCitizenResponse.setBlockName(srCiti.getBlockName());
				srCitizenResponse.setAddress(srCiti.getAddress());
				srCitizenResponse.setVillage(srCiti.getVillage());
				srCitizenResponse.setEmailID(srCiti.getEmailID());
				srCitizenResponse.setStatus(srCiti.getStatus());
				srCitizenResponse.setGender(srCiti.getGender());
				
				}
			
		return srCitizenResponse ;
		
	}
}
