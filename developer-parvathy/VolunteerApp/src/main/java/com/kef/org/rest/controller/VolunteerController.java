package com.kef.org.rest.controller;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kef.org.rest.model.Admin;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.LoginInfo;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.AdminRepository;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRepository;
import com.kef.org.rest.service.AdminService;
import com.kef.org.rest.service.MedicalandGreivanceService;
import com.kef.org.rest.service.VolunteerService;

@RestController
 
public class VolunteerController 
{
	
	public static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);

    
    @Autowired
    VolunteerService volunteerService; 
    
    
    @Autowired
    AdminService adminService; 
    
    
    
    @Autowired
    MedicalandGreivanceService medicalandgreivanceservice; 

    
    @Autowired
	private MedicalandGreivanceRepository medicalandgreivanceRespository;
    
    @Autowired
   	private VolunteerAssignmentRepository volunteerassignmentRespository;
    
    @Autowired
   	private AdminRepository adminRespository;
    
    
    @Autowired
   	private GreivanceTrackingRepository greivanceTrackingRepository;

    
    @Autowired
	private VolunteerRepository volunteerRespository;
	
   //Need to add role to LoginInfo object
    @RequestMapping(value = "/loginVolunteerOrAdmin", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  checkVolunteerOrAdminMobile(@RequestBody Volunteer volunteer)
    { 
    	String phoneNo = volunteer.getphoneNo();
        logger.info("phoneNo" +phoneNo); 
    	LoginInfo loginInfo = new LoginInfo();
    	if(null != phoneNo) {
    	if((volunteerService.findvolunteerId(phoneNo)) != null )
    	{
    		loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
    		Volunteer v= new Volunteer();
    		v = volunteerService.findvolunteerDetails(phoneNo);
    		loginInfo.setId(v.getIdvolunteer());
    		loginInfo.setRole(v.getRole());
  		return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
    	}
    	else if( null!= adminService.findAdminId(phoneNo) ){
    		loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
    		Admin a= new Admin();
    		a = adminService.fetchAdminDetails(phoneNo);
    		loginInfo.setId(a.getAdminId());
    		loginInfo.setRole(a.getRole());
  		return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
    	}else {
  		  logger.info("Reached here"); 
  		  loginInfo.setMessage("Failure");
  		  loginInfo.setStatusCode("1"); 		  
  		  loginInfo.setId(volunteerService.findvolunteerId(phoneNo));
  		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT); }
    	}
    	else {
		  logger.info("Reached here"); 
		  loginInfo.setMessage("Failure");
		  loginInfo.setStatusCode("1"); 		  
		  loginInfo.setId(volunteerService.findvolunteerId(phoneNo));
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT); }
		 
    	
    	
    	
    }
    
    @RequestMapping(value = "/VolunteerData", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  VolunteerDetailsbymobile(@RequestBody Volunteer volunteer)
    {LoginInfo loginInfo = new LoginInfo();
    		String phoneNo = volunteer.getphoneNo();
    		Volunteer v1 = volunteerService.findvolunteerDetails(phoneNo);
    		if(v1.getIdvolunteer().equals(null))
    		{   
    			loginInfo.setMessage("Failure");
    			loginInfo.setStatusCode("1"); 
    			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
    			
    		}
    		else {
    			loginInfo.setMessage("Success"); 
        		loginInfo.setStatusCode("0");
    			loginInfo.setVolunteer(v1);
    			 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
    		}
    		
    	
    	
    }

    
    
    @RequestMapping(value = "/loadDashboard", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public  ResponseEntity<LoginInfo>  loadVolunteerDashboard(@RequestBody Volunteer volunteer)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	Optional<Volunteer> v1	= volunteerRespository.findById(volunteer.getIdvolunteer());
    	if(v1.isPresent()) {
    		loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
			loginInfo.setVolunteer(v1.get());
			 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
    		
    	}else {
    		loginInfo.setMessage("Failure");
			loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
    	}
}
    
    @RequestMapping(value = "/seniorcitizenDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  getseniorcitizendetails(@RequestBody VolunteerAssignment volunteerassignement)
    {
    	LoginInfo loginInfo = new LoginInfo();
		
		  Optional<VolunteerAssignment> v1 =  volunteerassignmentRespository.findById(volunteerassignement.getCallid());
		  if(v1.isPresent()) {
		  loginInfo.setMessage("Success");
		  loginInfo.setStatusCode("0"); 
		  loginInfo.setVolunteerassignment(v1.get());
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
		 
    	
    	
    	
    	
    }
    
    @RequestMapping(value = "/getcalldetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  getseniorcitizencalldetails(@RequestBody MedicalandGreivance medicalgreivance)
    {
    	LoginInfo loginInfo = new LoginInfo();
		
		  Optional<MedicalandGreivance> mg1 =  medicalandgreivanceRespository.findById(medicalgreivance.getIdgrevance());
		  if(mg1.isPresent()) {
		  loginInfo.setMessage("Success");
		  loginInfo.setStatusCode("0"); 
		  loginInfo.setMedicalandgreivance(mg1.get());
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
		 
    	
    	
    	
    	
    }
    
    
    
    @RequestMapping(value = "/saveForm", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  saveFeedbackForm(@RequestBody VolunteerAssignment volunteerassignement)
    {
    	
    	LoginInfo loginInfo = new LoginInfo();
    	Integer idgreviance;
    	idgreviance =	medicalandgreivanceservice.processformData(volunteerassignement);
    	loginInfo.setMessage("Success"); 
		loginInfo.setStatusCode("0");
		loginInfo.setIdgrevance(idgreviance);
		 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
   
}
    
    @RequestMapping(value = "/registerNewSrCitizen", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  registerNewSrCitizen(@RequestBody VolunteerAssignment volunteerassignement)
    {
    	
    	LoginInfo loginInfo = new LoginInfo();
    	volunteerassignmentRespository.save(volunteerassignement);
    	loginInfo.setMessage("Success"); 
		loginInfo.setStatusCode("0");
		 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
   
}
    
    @RequestMapping(value = "/getGreivanceDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  fetchGreivanceDetails(@RequestBody GreivanceTracking greivanceTracking)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	List<GreivanceTracking> greivanceTrackingList = new ArrayList<>();
		
    	greivanceTrackingList =  greivanceTrackingRepository.findAllbyidvolunteer(greivanceTracking.getIdvolunteer());
		  if(null != greivanceTrackingList && !greivanceTrackingList.isEmpty()) {
		  loginInfo.setMessage("Success");
		  loginInfo.setStatusCode("0"); 
		  loginInfo.setGreivanceTrackingList(greivanceTrackingList);
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
		 
    	
    	
    	
    	
    }
    
    @RequestMapping(value = "/updateGreivanceDetails", method = RequestMethod.PUT,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  updateGreivanceDetails(@RequestBody GreivanceTracking greivanceTracking)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	GreivanceTracking greivanceTrack = new GreivanceTracking();
    	MedicalandGreivance mg1 = new MedicalandGreivance();
    	Optional<MedicalandGreivance> medicalandGreivance;
		
    	greivanceTrack =  greivanceTrackingRepository.findbytrackingid(greivanceTracking.getTrackingId());
		  if(null != greivanceTrack) {
			  greivanceTrack.setStatus(greivanceTracking.getStatus());
			  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
				  greivanceTrack.setDescription(greivanceTracking.getDescription());
				  greivanceTrack.setCreatedDate(LocalDateTime.now());
			  }
			  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
				  greivanceTrack.setUnderReviewRemarks(greivanceTracking.getDescription());
				  greivanceTrack.setUnderReviewDate(LocalDateTime.now());
			  }
			  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
				  greivanceTrack.setResolvedRemarks(greivanceTracking.getDescription());
				  greivanceTrack.setResolvedDate(LocalDateTime.now());
			  }
			  greivanceTrack.setLastUpdatedOn(LocalDateTime.now());
			  greivanceTrackingRepository.save(greivanceTrack);
			  
			  medicalandGreivance = medicalandgreivanceRespository.findById(greivanceTrack.getIdgrevance());
			  if(medicalandGreivance.isPresent()) {
				  mg1=medicalandGreivance.get();
				  if("Lack of food".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setFoodshortage(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of access to banking services".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setAceesstobankingissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of hygiene and sanitation".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setHygieneissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of medicine".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setMedicineshortage(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Phone & Internet services".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setPhoneandinternetissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of Safety".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setSafetyissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of utilities supply".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setUtilitysupplyissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  if("Lack of access to emergency services".equalsIgnoreCase(greivanceTracking.getGreivanceType())) {
						mg1.setEmergencyserviceissue(greivanceTracking.getStatus().equalsIgnoreCase("RAISED") ? 1
								: greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW") ? 2
										: greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED") ? 3 : 4);
						mg1.setDescription(greivanceTracking.getDescription());
						mg1.setLastUpdatedOn(LocalDateTime.now());
				  }
				  medicalandgreivanceRespository.save(mg1);
			  }
			  
			  
		  loginInfo.setMessage("Success");
		  loginInfo.setStatusCode("0"); 
		  
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
		 
    	
    	
    	
    	
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.PUT,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  updateProfile(@RequestBody Volunteer volunteer)
    {
    	
    	LoginInfo loginInfo = new LoginInfo();
    	Optional<Volunteer> volunteer1 = volunteerRespository.findById(volunteer.getIdvolunteer());
    	if(volunteer1.isPresent()) {
    		Volunteer vol = new Volunteer();
    		vol = volunteer1.get();
    		vol.setFirstName(null != volunteer.getFirstName() ? volunteer.getFirstName() : vol.getFirstName());
    		vol.setLastName(null != volunteer.getLastName() ? volunteer.getLastName() : vol.getLastName());
    		vol.setAddress(null != volunteer.getAddress() ? volunteer.getAddress() : vol.getAddress());
    		vol.setEmail(null != volunteer.getEmail() ? volunteer.getEmail() : vol.getEmail());
    		
    		volunteerRespository.save(vol);
    	
    	loginInfo.setMessage("Success"); 
		loginInfo.setStatusCode("0");
		 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
    	}else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
   
}
    
    


}