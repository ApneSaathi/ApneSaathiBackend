package com.kef.org.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kef.org.rest.domain.model.SeniorCitizenQueryResponse;
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
	  @Autowired
	  private EntityManager em;
	  
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
		List<SrCitizenVO> srCitizenVOList;
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
//		SrCitizenresponse.setSrCitizenList(result);
		srCitizenVOList=SrCitizenToSrCitizenVO(result);
		if(!srCitizenVOList.isEmpty() && srCitizenVOList!=null) {
		SrCitizenresponse.setListsrCitizen(srCitizenVOList);
		}
		else {
			SrCitizenresponse.setListsrCitizen(null);
		}
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
					srCitizenResponse.setTalked_with(volAssigned.get(0).getTalkedwith());
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
				srCitizenResponse.setLastName(srCiti.getLastName());
				
				}
			
		return srCitizenResponse ;
		
	}
	public List<SrCitizenVO> SrCitizenToSrCitizenVO(List<SeniorCitizen> srCitizen){
		List<SrCitizenVO> result=new ArrayList<>();
		List<VolunteerAssignment>volAssigned=new ArrayList<VolunteerAssignment>() ;
		Volunteer volunteer;
		if(srCitizen!=null && !srCitizen.isEmpty()) {
			for(SeniorCitizen sr:srCitizen) {
				SrCitizenVO seniorCitizen =new SrCitizenVO();
				seniorCitizen.setSrCitizenId(sr.getSrCitizenId());
				seniorCitizen.setStatus(sr.getStatus());
				seniorCitizen.setDeboardedOn(sr.getDeboardedOn());
				seniorCitizen.setReasons(sr.getReasons());
				seniorCitizen.setFirstName(sr.getFirstName());
				seniorCitizen.setAge(sr.getAge());
				seniorCitizen.setGender(sr.getGender());
				seniorCitizen.setPhoneNo(sr.getPhoneNo());
				seniorCitizen.setState(sr.getState());
				seniorCitizen.setDistrict(sr.getDistrict());
				seniorCitizen.setAddress(sr.getAddress());
				seniorCitizen.setBlockName(sr.getBlockName());
				seniorCitizen.setVillage(sr.getVillage());
				seniorCitizen.setEmailID(sr.getEmailID());
				seniorCitizen.setLastName(sr.getLastName());
				volAssigned=volunteerassignmentRespository.findByPhonenosrcitizenAndStatusIgnoreCase(sr.getPhoneNo(),"Assigned");
							if(!volAssigned.isEmpty() && volAssigned!=null) {
					volunteer=volunteerRepository.findbyidvolunteer(volAssigned.get(0).getIdvolunteer());
				
				seniorCitizen.setAssignedVolunteer(volunteer.getIdvolunteer());
				seniorCitizen.setAssignedVolunteerName(volunteer.getFirstName() + " "+volunteer.getLastName());
			}
			result.add(seniorCitizen);		
			}
	}
		return result;
	}
	
	public List<SeniorCitizenQueryResponse> getSeniorCitizenQueries(String request) {
		JSONObject json = new JSONObject(request);
		String queryType = json.has("queryType") && json.getString("queryType")!=null 
				? json.getString("queryType").trim() : "";
		String state = json.has("state") && json.getString("state")!=null
						? json.getString("state").trim() : "";
		String district = json.has("district") && json.getString("district")!=null
				? json.getString("district").trim() : "";
		String block = json.has("block") && json.getString("block")!=null
						? json.getString("block").trim() : "";
		String sortBy = json.has("sortBy") && json.getString("sortBy")!=null
				? json.getString("sortBy").trim() : "";
		String sortType = json.has("sortType") && json.getString("sortType")!=null
				? json.getString("sortType").trim() : "";
		Integer pageNumber = json.has("pageNumber")? json.getInt("pageNumber"): 1;
		Integer limit = json.has("limit")? json.getInt("limit"): 10;
		if(queryType.equalsIgnoreCase("pending"))
			queryType="RAISED";
		else if(queryType.equalsIgnoreCase("In progress"))
			queryType = "UNDER REVIEW";
		else if(queryType.equalsIgnoreCase("Resolved"))
			queryType = "RESOLVED";
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> gtQuery = builder.createTupleQuery();
		Root<GreivanceTracking> gtRoot = gtQuery.from(GreivanceTracking.class);
		Root<SeniorCitizen> scRoot = gtQuery.from(SeniorCitizen.class);
		List<Predicate> conditions = new ArrayList<>();
		conditions.add(builder.equal(gtRoot.get("phoneNo"),scRoot.get("phoneNo")));
		conditions.add(builder.equal(gtRoot.get("status"), queryType));
		if(!state.isEmpty())
			conditions.add(builder.equal(scRoot.get("state"), state));
		if(!district.isEmpty())
			conditions.add(builder.equal(scRoot.get("district"), district));
		if(!block.isEmpty())
			conditions.add(builder.equal(scRoot.get("blockName"), block));
		
		gtQuery.multiselect(gtRoot.get("namesrcitizen").alias("name"),gtRoot.get("adminId").alias("issueId"),gtRoot.get("greivanceType").alias("issueRaised"),
				scRoot.get("state"),scRoot.get("district"),scRoot.get("blockName").alias("block"),gtRoot.get("createdDate").alias("createdOn"),
				gtRoot.get("priority"),gtRoot.get("lastUpdatedOn"),gtRoot.get("resolvedDate").alias("resolvedOn"));
		TypedQuery<Tuple> typedQuery;
		if(!sortBy.isEmpty()) {
			sortType = sortType.isEmpty() ? "asc" : sortType;
			if(sortType.equalsIgnoreCase("asc")) {
				typedQuery = em.createQuery(gtQuery
						.where(conditions.toArray(new Predicate[] {}))
						.orderBy(builder.asc(sortBy.equalsIgnoreCase("priority") ? gtRoot.get("priority") : gtRoot.get("createdDate"))));
			}else{
				typedQuery = em.createQuery(gtQuery
						.where(conditions.toArray(new Predicate[] {}))
						.orderBy(builder.desc(sortBy.equalsIgnoreCase("priority") ? gtRoot.get("priority") : gtRoot.get("createdDate"))));
			}
		}else {
			typedQuery = em.createQuery(gtQuery
					.where(conditions.toArray(new Predicate[] {})));
		}
		typedQuery.setFirstResult((pageNumber-1)*limit);
		typedQuery.setMaxResults(limit);
		List<Tuple> tupleList = typedQuery.getResultList();
		List<SeniorCitizenQueryResponse> responseList = new ArrayList<>();
		if(tupleList!=null) {
			tupleList.forEach(row->{
				SeniorCitizenQueryResponse response = new SeniorCitizenQueryResponse();
				response.setName(row.get(0)!=null ? String.valueOf(row.get(0)):"");
				response.setIssueId(row.get(1)!=null ? Integer.valueOf(String.valueOf(row.get(1))):null);
				response.setIssuesRaised(row.get(2)!=null ? String.valueOf(row.get(2)):"");
				response.setState(row.get(3)!=null ? String.valueOf(row.get(3)):"");
				response.setDistrict(row.get(4)!=null ? String.valueOf(row.get(4)):"");
				response.setBlock(row.get(5)!=null ? String.valueOf(row.get(5)):"");
				response.setCreatedOn(row.get(6)!=null ? String.valueOf(row.get(6)):"");
				response.setPriority(row.get(7)!=null ? String.valueOf(row.get(7)):"");
				response.setLastUpdatedOn(row.get(8)!=null ? String.valueOf(row.get(8)):"");
				response.setResolvedOn(row.get(9)!=null ? String.valueOf(row.get(9)):"");
				responseList.add(response);
			});
		}
		
		return responseList;
	}
}
