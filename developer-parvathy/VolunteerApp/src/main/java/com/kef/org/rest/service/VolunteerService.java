package com.kef.org.rest.service;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.interfaces.VolunteerInterface;

import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.model.VolunteerResponse;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRepository;


@Service("volunteerService")

public class VolunteerService implements VolunteerInterface{
	@Autowired
	private VolunteerRepository volunteerRespository;
	
	@Autowired
	private VolunteerAssignmentRepository volunteerAssignmentRepository;
	


	
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
	


	public VolunteerResponse getVolunteerListByQuery(VolunteerVO volunteerStatus){
		
		
		List<VolunteerVO> result=new ArrayList<VolunteerVO>();
		String status=volunteerStatus.getStatus();
		String filterState=volunteerStatus.getFilterState();
		String filterDistrict=volunteerStatus.getFilterDistrict();
		String filterBlock=volunteerStatus.getFilterBlock();
		String sortBy=volunteerStatus.getSortBy();
		String sortType=volunteerStatus.getSortType();
		Integer limit;
    	Integer pagenumber;
    	List<Object> resultList;
    	List<VolunteerVO> excludedVolunteers =new ArrayList<VolunteerVO>();
    	VolunteerResponse volResponse=new VolunteerResponse();
    	if(volunteerStatus.getLimit()==null && volunteerStatus.getPagenumber()==null) {
    		limit=10;
    		pagenumber=0;
    		
    	}
    	else {
    	limit=volunteerStatus.getLimit();
    	
    	pagenumber=volunteerStatus.getPagenumber();
    	}
    	
    	Pageable ptry;
    	Page <Object> page;
    	Integer totalVolunteer=0;
    	if(sortBy!=null && sortType!=null) {
    		Direction direction=sortType.equalsIgnoreCase("DESC") && sortType!=null?Sort.Direction.DESC 
    			: Sort.Direction.ASC;
 
    	
    		
    	
    		 if (sortBy.equalsIgnoreCase("rating")&& sortBy!=null) {
    		
    			ptry=PageRequest.of(pagenumber, limit, JpaSort.unsafe(direction,"(average_rating)"));
    			}	
    		else if(sortBy.equalsIgnoreCase("assignedSrCitizen") && sortBy!=null) {
    		
    			ptry=PageRequest.of(pagenumber, limit, JpaSort.unsafe(direction, "(countsr)"));
    		
    			}
    		else {
    			ptry=PageRequest.of(pagenumber, limit);
    		}
    	}
    	else {
    		ptry=PageRequest.of(pagenumber, limit);
    	}
    	if(filterState!=null && filterDistrict==null && filterBlock==null) {
    		page=volunteerRespository.fetchByStatusAndState(status, filterState, ptry);
    		resultList=page.getContent();
    		totalVolunteer=(int) page.getTotalElements();
    	}

    	else if(filterState!=null && filterDistrict!=null && filterBlock==null) {
    		page=volunteerRespository.fetchByStatusAndStateAndDistrict(status, filterState, filterDistrict, ptry);
    		resultList=page.getContent();
    		totalVolunteer=(int) page.getTotalElements();
    	}
 
    	else if (filterState!=null && filterDistrict!=null && filterBlock!=null){
    		
    		page=volunteerRespository.fetchByStatusAndStateAndDistrictAndBlock(status, filterState, filterDistrict, filterBlock, ptry);
    		resultList=page.getContent();
    		totalVolunteer=(int) page.getTotalElements();
    	}
    	else {
    		page=volunteerRespository.queryFunction(status, ptry);
    		resultList=page.getContent();
    		totalVolunteer=(int) page.getTotalElements();
//    		resultList=volunteerRespository.queryFunction(status, ptry);
    	}
		
		if(resultList!=null && !resultList.isEmpty()) {
				
				for(int i=0;i<resultList.size();i++){
				Object[] row=(Object[]) resultList.get(i);
				VolunteerVO vo=new VolunteerVO();
				vo.setIdvolunteer(Integer.valueOf(String.valueOf(row[0])));
				vo.setFirstName(String.valueOf(row[1]));
				vo.setLastName(String.valueOf(row[2]));
				vo.setphoneNo(String.valueOf(row[3]));
				vo.setEmail(String.valueOf(row[4]));
				vo.setGender(String.valueOf(row[5]));
				vo.setState(String.valueOf(row[6]));
				vo.setDistrict(String.valueOf(row[7]));
				vo.setBlock(String.valueOf(row[8]));
				vo.setAddress(String.valueOf(row[9]));
				vo.setVillage(String.valueOf(row[10]));
				vo.setAssignedtoFellow(String.valueOf(row[11]));
				vo.setAssignedtoFellowContact(String.valueOf(row[12]));
//				vo.setPic((String.valueOf(row[13]).getBytes()));
				vo.setRole(Integer.valueOf(String.valueOf(row[14])));
				vo.setAdminId(Integer.valueOf(String.valueOf(row[15])));
				vo.setStatus(String.valueOf(row[16]));
				vo.setRating(Float.valueOf(String.valueOf(row[17])));
//				vo.setCount_SrCitizen(Integer.valueOf(String.valueOf(row[18])));
				Integer idvolunteer=Integer.valueOf(String.valueOf(row[0]));
				Integer countsr=getSrCitizenCount(idvolunteer);
				vo.setCount_SrCitizen(countsr);
				result.add(vo);
			}
				if(volunteerStatus.getExcludeIds()!=null && !volunteerStatus.getExcludeIds().isEmpty()) {
					excludedVolunteers=excludeVolunteer(volunteerStatus.getExcludeIds(),result);
					volResponse.setExcludedVolunteers(excludedVolunteers);
					}
				}
		volResponse.setVolunteers(result);
		volResponse.setTotalVolunteers(totalVolunteer);
		
		
		return volResponse; 
	}
	
		public Integer getSrCitizenCount(Integer idvolunteer) {
		
		List<VolunteerAssignment> vol=new ArrayList<>();
		Integer countsrCitizen=0;
		vol=volunteerAssignmentRepository.findAllByIdVolunteer(idvolunteer);
			if(vol!=null && !vol.isEmpty()) {
				for(VolunteerAssignment va:vol) {
					if(!va.getStatus().equalsIgnoreCase("UnAssigned")) {
						
						countsrCitizen+=1;
					}
				}
				
			}		
					return countsrCitizen;
				}
		
		public List<VolunteerVO> excludeVolunteer(List<Integer> exclude,List<VolunteerVO> resultList){
			
			List<VolunteerVO> results=new ArrayList<>();
			
			
				
				for(VolunteerVO t:resultList) {
				if(exclude.contains(t.getIdvolunteer())) {
					
				
				}
				else {
					results.add(t);
				}
			}
			return results;
		}
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
		
