package com.kef.org.rest.controller;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.kef.org.rest.domain.model.GreivanceTrackingVO;
import com.kef.org.rest.domain.model.InputVO;
import com.kef.org.rest.domain.model.VolunteerAssignmentVO;
import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.model.Admin;
import com.kef.org.rest.model.District;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.LoginInfo;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.SrCitizenResponse;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.AdminRepository;
import com.kef.org.rest.repository.DistrictRepository;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.SeniorCitizenRepository;
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
    
    @Autowired
	private DistrictRepository districtRespository;
    
    @Autowired
    private SeniorCitizenRepository seniorcitizenRepository;
	
   //Need to add role to LoginInfo object
    @RequestMapping(value = "/loginVolunteerOrAdmin", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  checkVolunteerOrAdminMobile(@RequestBody Volunteer volunteer)
	{
		String phoneNo = volunteer.getphoneNo();
		Integer districtId = null;
		logger.info("phoneNo" + phoneNo);
		LoginInfo loginInfo = new LoginInfo();
		if (null != phoneNo) {
			if ((volunteerService.findvolunteerId(phoneNo)) != null) {
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				Volunteer v = new Volunteer();
				v = volunteerService.findvolunteerDetails(phoneNo);
				loginInfo.setId(v.getIdvolunteer());
				loginInfo.setRole(v.getRole());
			} else if (null != adminService.findAdminId(phoneNo)) {
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				Admin a = new Admin();
				a = adminService.fetchAdminDetails(phoneNo);
				loginInfo.setId(a.getAdminId());
				loginInfo.setRole(a.getRole());
				if (a.getRole() == 3) {
					districtId = districtRespository.findDistrictId(a.getAdminId());
					loginInfo.setDistrictId(districtId);
				}
			} else {
				logger.info("Reached here");
				loginInfo.setMessage("Failure");
				loginInfo.setStatusCode("1");
				loginInfo.setId(volunteerService.findvolunteerId(phoneNo));
			}
		} else {
			logger.info("Reached here");
			loginInfo.setMessage("Failure");
			loginInfo.setStatusCode("1");
			loginInfo.setId(volunteerService.findvolunteerId(phoneNo));
		}
		return new ResponseEntity<LoginInfo>(loginInfo, loginInfo.getStatusCode().equals("0") ?  HttpStatus.OK:HttpStatus.CONFLICT);
	}
    
    @RequestMapping(value = "/VolunteerorAdminData", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  VolunteerDetailsbymobile(@RequestBody Volunteer volunteer)
	{
		LoginInfo loginInfo = new LoginInfo();
		String phoneNo = volunteer.getphoneNo();
		if (null != phoneNo) {
			if ((volunteerService.findvolunteerId(phoneNo)) != null) {
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				Volunteer v = new Volunteer();
				v = volunteerService.findvolunteerDetails(phoneNo);
				loginInfo.setVolunteer(v);
			} else if (null != adminService.findAdminId(phoneNo)) {
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				Admin a = new Admin();
				a = adminService.fetchAdminDetails(phoneNo);
				a.setVolunteerList(null);
				a.setPassword(null);
				a.setDistrictList(null);
				loginInfo.setAdmin(a);
			}else {
				loginInfo.setMessage("Failure");
				loginInfo.setStatusCode("1");
			}
			
		} else {
			loginInfo.setMessage("Failure");
			loginInfo.setStatusCode("1");

		}

		return new ResponseEntity<LoginInfo>(loginInfo,
				loginInfo.getStatusCode().equals("0") ? HttpStatus.OK : HttpStatus.CONFLICT);

	}

    
    
    @RequestMapping(value = "/loadDashboard", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public  ResponseEntity<LoginInfo>  loadVolunteerDashboard(@RequestBody InputVO inputVO)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	Admin a2 = new Admin();
    	com.kef.org.rest.domain.model.Admin adminDomain = new com.kef.org.rest.domain.model.Admin();
    	String createdBy = inputVO.getFilterBy() == 1 ? "Volunteer" : inputVO.getFilterBy()== 2 ? "Staff Member" : inputVO.getFilterBy()== 4 ? "Master Admin" : null;
		if (null!=createdBy && createdBy.equals("Volunteer")) {
			Optional<Volunteer> v1 = volunteerRespository.findById(inputVO.getId());
			if (v1.isPresent()) {
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				loginInfo.setVolunteer(v1.get());
			}
		}
		else if(null!=createdBy && (createdBy.equals("Staff Member") || createdBy.equals("Master Admin"))) {
			
			Optional<Admin> a1 = adminRespository.findById(inputVO.getId());
			if(a1.isPresent()) {
				a2=a1.get();
				Integer role = createdBy.equals("Staff Member") ? 2 : 4;
				List<VolunteerAssignment> volunteerAssignmentList = volunteerassignmentRespository.findAllByAdminId(inputVO.getId(), role);
				adminDomain.setAdminId(a2.getAdminId());
				adminDomain.setDistrict(a2.getDistrict());
				adminDomain.setEmail(a2.getEmail());
				adminDomain.setFirstName(a2.getFirstName());
				adminDomain.setLastName(a2.getLastName());
				adminDomain.setMobileNo(a2.getMobileNo());
				adminDomain.setRole(a2.getRole());
				adminDomain.setState(a2.getState());
				adminDomain.setAdminCallList(volunteerAssignmentList);
				adminDomain.setDistrictList(null != a2.getDistrictList() ? a2.getDistrictList() : null);
				
				loginInfo.setMessage("Success");
				loginInfo.setStatusCode("0");
				loginInfo.setAdminDomain(adminDomain);
			}
			
		}
    	else {
    		loginInfo.setMessage("Failure");
			loginInfo.setStatusCode("1"); 
    	}
		return new ResponseEntity<LoginInfo>(loginInfo, loginInfo.getStatusCode().equals("0") ? HttpStatus.OK :HttpStatus.CONFLICT);
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
    public ResponseEntity<LoginInfo>  registerNewSrCitizen(@RequestBody VolunteerAssignmentVO volunteerassignementVO)
	{

		LoginInfo loginInfo = new LoginInfo();
		VolunteerAssignment volunteerassignement = mapVolunteerAssignmentVOToEntity(volunteerassignementVO);
		volunteerassignmentRespository.save(volunteerassignement);
		loginInfo.setMessage("Success");
		loginInfo.setStatusCode("0");
		return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);

	}
    
    @RequestMapping(value = "/getGreivanceDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  fetchGreivanceDetails(@RequestBody GreivanceTrackingVO greivanceTrackingVO)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	List<GreivanceTracking> greivanceTrackingList = new ArrayList<>();
		
    	if(null !=greivanceTrackingVO.getFilterBy() && greivanceTrackingVO.getFilterBy() == 1) {
    		greivanceTrackingList =  greivanceTrackingRepository.findAllbyidvolunteer(greivanceTrackingVO.getId(), "Volunteer");
    	}
    	if(null !=greivanceTrackingVO.getFilterBy() && (greivanceTrackingVO.getFilterBy() == 2 || greivanceTrackingVO.getFilterBy() == 4)){
    		String createdBy = greivanceTrackingVO.getFilterBy() == 2 ? "Staff Member" : "Master Admin";
    		greivanceTrackingList =  greivanceTrackingRepository.findAllbyadminId(greivanceTrackingVO.getId(), createdBy);
    	}
    	if(null != greivanceTrackingVO.getId() && null!=greivanceTrackingVO.getDistrictId()) {
    		District district = districtRespository.fetchDistrictDetailsByDistrictId(greivanceTrackingVO.getDistrictId(), greivanceTrackingVO.getId());
    		if(null != district) {
    			greivanceTrackingList = greivanceTrackingRepository.findAllbyDistrictName(district.getDistrictName());
    		}
    	}
    	
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
				  greivanceTrack.setRaisedby(greivanceTracking.getRaisedby());
				  greivanceTrack.setCreatedDate(LocalDateTime.now());
			  }
			  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
				  greivanceTrack.setReviewedby(greivanceTracking.getReviewedby());
				  greivanceTrack.setUnderReviewRemarks(greivanceTracking.getDescription());
				  greivanceTrack.setUnderReviewDate(LocalDateTime.now());
			  }
			  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
				  greivanceTrack.setResolvedby(greivanceTracking.getReviewedby());
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
    public ResponseEntity<LoginInfo>  updateProfile(@RequestBody VolunteerVO volunteerVO)
    {
    	
    	LoginInfo loginInfo = new LoginInfo();
    	if(null != volunteerVO.getIdvolunteer()) {
    	Optional<Volunteer> volunteer1 = volunteerRespository.findById(volunteerVO.getIdvolunteer());
    	if(volunteer1.isPresent()) {
    		Volunteer vol = new Volunteer();
    		vol = volunteer1.get();
    		vol.setFirstName(null != volunteerVO.getFirstName() ? volunteerVO.getFirstName() : vol.getFirstName());
    		vol.setLastName(null != volunteerVO.getLastName() ? volunteerVO.getLastName() : vol.getLastName());
    		vol.setAddress(null != volunteerVO.getAddress() ? volunteerVO.getAddress() : vol.getAddress());
    		vol.setEmail(null != volunteerVO.getEmail() ? volunteerVO.getEmail() : vol.getEmail());
    		
    		volunteerRespository.save(vol);
    	
    	loginInfo.setMessage("Success"); 
		loginInfo.setStatusCode("0");
		 
    	}else {
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
		  }
    	}else if(null != volunteerVO.getAdminId()){
        	Optional<Admin> admin1 = adminRespository.findById(volunteerVO.getAdminId());
        	if(admin1.isPresent()) {
        		Admin a1 = new Admin();
        		a1 = admin1.get();
        		a1.setFirstName(null != volunteerVO.getFirstName() ? volunteerVO.getFirstName() : a1.getFirstName());
        		a1.setLastName(null != volunteerVO.getLastName() ? volunteerVO.getLastName() : a1.getLastName());
        		a1.setAddress(null != volunteerVO.getAddress() ? volunteerVO.getAddress() : a1.getAddress());
        		a1.setEmail(null != volunteerVO.getEmail() ? volunteerVO.getEmail() : a1.getEmail());
        		
        		adminRespository.save(a1);
        	
        	loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
    		 
        	}else {
  			  loginInfo.setMessage("Failure"); 
  			  loginInfo.setStatusCode("1"); 
  		  }
        	}else {
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
		  }
    	return new ResponseEntity<LoginInfo>(loginInfo,loginInfo.getStatusCode().equals("0")? HttpStatus.OK : HttpStatus.CONFLICT);
   
}
    @RequestMapping(value = "/getSrCitizenDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  fetchSrCitizenDetails(@RequestBody VolunteerAssignmentVO volunteerAssignmentVO)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	List<MedicalandGreivance> medicalGreivanceList = null;
    	List<VolunteerAssignment> volunteerAssignmentList = new ArrayList<>();
    	List<VolunteerAssignmentVO> volunteerAssignmentVOList = new ArrayList<>();
    	String str = volunteerAssignmentVO.getLoggeddateTime();
    	DateTimeFormatter DATEFORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(DATEFORMATTER1)
        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
        .toFormatter();

        //DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime ldt = LocalDateTime.parse(str, DATEFORMATTER);
        logger.info("Reached here"+ldt.toLocalDate()); 
    	
    	volunteerAssignmentList =  volunteerassignmentRespository.findAllByIdVolunteer(volunteerAssignmentVO.getIdvolunteer());
		  if(null != volunteerAssignmentList && !volunteerAssignmentList.isEmpty()) {
			  volunteerAssignmentList = volunteerAssignmentList.stream().filter(va ->((va.getLoggeddateTime().toLocalDate().isEqual(ldt.toLocalDate()))||
					  (va.getLoggeddateTime().toLocalDate().isBefore(ldt.toLocalDate())))).collect(Collectors.toList());
			  for (VolunteerAssignment volunteerAssignment : volunteerAssignmentList) {
				  VolunteerAssignmentVO volunteerAssignmentVO1 = new VolunteerAssignmentVO();
				  volunteerAssignmentVO1.setAddresssrcitizen(volunteerAssignment.getAddresssrcitizen());
				  volunteerAssignmentVO1.setAgesrcitizen(volunteerAssignment.getAgesrcitizen());
				  volunteerAssignmentVO1.setBlocknamesrcitizen(volunteerAssignment.getBlocknamesrcitizen());
				  volunteerAssignmentVO1.setCallid(volunteerAssignment.getCallid());
				  volunteerAssignmentVO1.setDistrictsrcitizen(volunteerAssignment.getDistrictsrcitizen());
				  volunteerAssignmentVO1.setEmailsrcitizen(volunteerAssignment.getEmailsrcitizen());
				  volunteerAssignmentVO1.setGendersrcitizen(volunteerAssignment.getGendersrcitizen());
				  volunteerAssignmentVO1.setIdvolunteer(volunteerAssignment.getIdvolunteer());
				  volunteerAssignmentVO1.setLoggeddateTime(volunteerAssignment.getLoggeddateTime().toString());
				  volunteerAssignmentVO1.setNamesrcitizen(volunteerAssignment.getNamesrcitizen());
				  volunteerAssignmentVO1.setPhonenosrcitizen(volunteerAssignment.getPhonenosrcitizen());
				  volunteerAssignmentVO1.setStatesrcitizen(volunteerAssignment.getStatesrcitizen());
				  volunteerAssignmentVO1.setVillagesrcitizen(volunteerAssignment.getVillagesrcitizen());
				  volunteerAssignmentVO1.setCallstatusCode(volunteerAssignment.getCallstatusCode());
				  if(!volunteerAssignment.getMedicalandgreivance().isEmpty()) {
					  medicalGreivanceList = new ArrayList<>();
					  medicalGreivanceList = volunteerAssignment.getMedicalandgreivance();
					  medicalGreivanceList.forEach(p->p.setGreivanceTracking(null));
				  }
				  volunteerAssignmentVO1.setMedicalandgreivance(!volunteerAssignment.getMedicalandgreivance().isEmpty()?medicalGreivanceList:null);
				  volunteerAssignmentVOList.add(volunteerAssignmentVO1);
				  
			}
		  loginInfo.setMessage("Success");
		  loginInfo.setStatusCode("0"); 
		  loginInfo.setSrCitizenList(volunteerAssignmentVOList);
		  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }else { 
			  loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
		  
		  }
		 
    	
    	
    	
    	
    }
    
    public VolunteerAssignment mapVolunteerAssignmentVOToEntity(VolunteerAssignmentVO volunteerassignementVO) {
    	
    	VolunteerAssignment volunteerassignement = new VolunteerAssignment();
    	Integer adminId = null;
    	if(volunteerassignementVO.getRole() == 1) {
			volunteerassignement.setIdvolunteer(volunteerassignementVO.getId());
			adminId = volunteerRespository.findAdminId(volunteerassignementVO.getId());
		}
		volunteerassignement.setAddresssrcitizen(volunteerassignementVO.getAddresssrcitizen());
		volunteerassignement.setAgesrcitizen(volunteerassignementVO.getAgesrcitizen());
		volunteerassignement.setAdminId(null != volunteerassignementVO.getRole() && volunteerassignementVO.getRole() != 1? volunteerassignementVO.getId() : adminId );
		volunteerassignement.setAssignedbyMember(volunteerassignementVO.getAssignedbyMember());
		volunteerassignement.setBlocknamesrcitizen(volunteerassignementVO.getBlocknamesrcitizen());
		volunteerassignement.setCallstatusCode(volunteerassignementVO.getCallstatusCode());
		volunteerassignement.setDistrictsrcitizen(volunteerassignementVO.getDistrictsrcitizen());
		volunteerassignement.setEmailsrcitizen(volunteerassignementVO.getEmailsrcitizen());
		volunteerassignement.setGendersrcitizen(volunteerassignementVO.getGendersrcitizen());
		volunteerassignement.setNamesrcitizen(volunteerassignementVO.getNamesrcitizen());
		volunteerassignement.setPhonenosrcitizen(volunteerassignementVO.getPhonenosrcitizen());
		volunteerassignement.setRole(volunteerassignementVO.getRole());
		volunteerassignement.setStatesrcitizen(volunteerassignementVO.getStatesrcitizen());
		volunteerassignement.setVillagesrcitizen(volunteerassignementVO.getVillagesrcitizen());
		
		return volunteerassignement;
    }
  //khushboo - get volunteer list
    @RequestMapping(value = "/getVolunteerList", method = RequestMethod.GET,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo> getVolunteerList(){
    	
    	LoginInfo loginInfo = new LoginInfo();
    	List<Volunteer> volunteer1 = volunteerRespository.findAll();
    	List <VolunteerVO> volunteers=new ArrayList<>();
    	if(null!=volunteer1 && !volunteer1.isEmpty()) {
    		
    		for (Volunteer v:volunteer1) {
    			VolunteerVO v1=new VolunteerVO();
    			v1.setAddress(v.getAddress());
    			v1.setAdminId(v.getAdminId());
    			v1.setAssignedtoFellow(v.getAssignedtoFellow());
    			v1.setAssignedtoFellowContact(v.getAssignedtoFellowContact());
    			v1.setBlock(v.getBlock());
    			v1.setDistrict(v.getBlock());
    			v1.setEmail(v.getEmail());
    			v1.setFirstName(v.getFirstName());
    			v1.setGender(v.getGender());
    			v1.setIdvolunteer(v.getIdvolunteer());
    			v1.setLastName(v.getLastName());
    			v1.setphoneNo(v.getphoneNo());
    			v1.setPic(v.getPic());
    			v1.setRole(v.getRole());
    			v1.setState(v.getState());
    			v1.setVillage(v.getVillage());
    			volunteers.add(v1);
    		}
    		loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
    		loginInfo.setVolunteers(volunteers);
    		 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
			
		}
    	else {
    			loginInfo.setMessage("Failure");
    				loginInfo.setStatusCode("1"); 
    				return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
    	}
    	
    }
    @RequestMapping(value="/getSrCitizenList",method=RequestMethod.GET,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<SrCitizenResponse> getSrCitizenList(){
    	
    	SrCitizenResponse srCitizen =new SrCitizenResponse();
    	List<SeniorCitizen> srCitizenList=seniorcitizenRepository.findAll();
    	
    	if(null!=srCitizenList && !srCitizenList.isEmpty()) {
    		srCitizen.setSrCitizenList(srCitizenList);
    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.OK);
    	}
    	else {
    		
    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.CONFLICT);
    	}
    	
    	}
    
    

}