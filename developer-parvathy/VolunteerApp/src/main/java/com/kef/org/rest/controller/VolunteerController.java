package com.kef.org.rest.controller;



import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.hibernate.validator.internal.constraintvalidators.hv.ISBNValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.kef.org.rest.domain.model.GreivanceTrackingVO;
import com.kef.org.rest.domain.model.InputVO;
import com.kef.org.rest.domain.model.SrCitizenVO;
import com.kef.org.rest.domain.model.VolunteerAssignmentVO;
import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.enums.BehaviouralChangeNoticed;
import com.kef.org.rest.enums.InputEnum;
import com.kef.org.rest.enums.RelatedInfoTalkedAbout;
import com.kef.org.rest.enums.TalkedWith;
import com.kef.org.rest.model.Admin;
import com.kef.org.rest.model.District;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.LoginInfo;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.ProfileResponse;
import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.SrCitizenResponse;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.model.VolunteerRating;
import com.kef.org.rest.model.VolunteerResponse;
import com.kef.org.rest.repository.AdminRepository;
import com.kef.org.rest.repository.DistrictRepository;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.SeniorCitizenRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRatingRepository;
import com.kef.org.rest.repository.VolunteerRepository;
import com.kef.org.rest.service.AdminService;
import com.kef.org.rest.service.MedicalandGreivanceService;
import com.kef.org.rest.service.SeniorCitizenService;
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
    
    @Autowired
    private VolunteerRatingRepository volunteerRatingRepostiry;
	
 @Autowired
   SeniorCitizenService srCitizenService;
   //Need to add role to LoginInfo object
    @RequestMapping(value = "/loginVolunteerOrAdmin", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  checkVolunteerOrAdminMobile(@RequestBody Volunteer volunteer)
	{
		String phoneNo = volunteer.getphoneNo();
		Integer districtId = null;
		logger.info("phoneNo" + phoneNo);
		LoginInfo loginInfo = new LoginInfo();
		if (null != phoneNo && !phoneNo.equals("")) {
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
				loginInfo.setMessage("Phone Number doesn't exist!! Please check");
				loginInfo.setStatusCode("1");
				loginInfo.setId(volunteerService.findvolunteerId(phoneNo));
				return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
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
    public ResponseEntity<ProfileResponse>  VolunteerDetailsbymobile(@RequestBody Volunteer volunteer)
	{
    	ProfileResponse profileResponse = new ProfileResponse();
		String phoneNo = volunteer.getphoneNo();
		if (null != phoneNo) {
			if ((volunteerService.findvolunteerId(phoneNo)) != null) {
				profileResponse.setMessage("Success");
				profileResponse.setStatusCode("0");
				Volunteer v = new Volunteer();
				VolunteerVO vo = new VolunteerVO();
				v = volunteerService.findvolunteerDetails(phoneNo);
				if(null != v) {
					vo=mapEntityToVoluntnteerVO(v);
				}
				profileResponse.setVolunteer(vo);
			} else if (null != adminService.findAdminId(phoneNo)) {
				profileResponse.setMessage("Success");
				profileResponse.setStatusCode("0");
				Admin a = new Admin();
				a = adminService.fetchAdminDetails(phoneNo);
				a.setVolunteerList(null);
				a.setPassword(null);
				a.setDistrictList(null);
				profileResponse.setAdmin(a);
			}else {
				profileResponse.setMessage("Failure");
				profileResponse.setStatusCode("1");
			}
			
		} else {
			profileResponse.setMessage("Failure");
			profileResponse.setStatusCode("1");

		}

		return new ResponseEntity<ProfileResponse>(profileResponse,
				profileResponse.getStatusCode().equals("0") ? HttpStatus.OK : HttpStatus.CONFLICT);

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
				Volunteer v = v1.get();
				loginInfo.setVolunteer(mapVolunteerToEntity(v));
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
				if(!volunteerAssignmentList.isEmpty()) {
				adminDomain.setAdminCallList(mapVolunteerAssignmentListtoEntity(volunteerAssignmentList));
				}
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
		  VolunteerAssignment volAssignment = new VolunteerAssignment();
		  volAssignment = v1.get();
		  volAssignment.setMedicalandgreivance(mapMedicalandGreivanceListToEntity(volAssignment));
		  loginInfo.setVolunteerassignment(volAssignment);
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
				  volunteerAssignmentVO1.setTalkedwith((null!=volunteerAssignment.getTalkedwith() && !volunteerAssignment.getTalkedwith().equals(""))?(volunteerAssignment.getTalkedwith().equalsIgnoreCase(TalkedWith.SENIOR_CITIZEN.getValue()) ? "1" : volunteerAssignment.getTalkedwith().equalsIgnoreCase(TalkedWith.FAMILY_MEMBER_OF_SR_CITIZEN.getValue()) ? "2" : 
				  volunteerAssignment.getTalkedwith().equalsIgnoreCase(TalkedWith.COMMUNITY_MEMBER.getValue()) ? "3" : null):null);
				  volunteerAssignmentVO1.setPhonenosrcitizen(volunteerAssignment.getPhonenosrcitizen());
				  volunteerAssignmentVO1.setStatesrcitizen(volunteerAssignment.getStatesrcitizen());
				  volunteerAssignmentVO1.setVillagesrcitizen(volunteerAssignment.getVillagesrcitizen());
				  volunteerAssignmentVO1.setCallstatusCode(volunteerAssignment.getCallstatusCode());
				  volunteerAssignmentVO1.setRole(volunteerAssignment.getRole());
				  volunteerAssignmentVO1.setAdminId(volunteerAssignment.getAdminId());
				  if(!volunteerAssignment.getMedicalandgreivance().isEmpty()) {
					  medicalGreivanceList = new ArrayList<>();
					  medicalGreivanceList = volunteerAssignment.getMedicalandgreivance();
					  medicalGreivanceList = mapMedicalandGreivanceListToEntity(volunteerAssignment);
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
			  loginInfo.setMessage("Success"); 
			  loginInfo.setStatusCode("0"); 
			  loginInfo.setSrCitizenList(volunteerAssignmentVOList);
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
		  
		  }
		 
    	
    	
    	
    	
    }
    
    @RequestMapping(value = "/volunteerRating", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  provideRating(@RequestBody VolunteerRating volunteerRating)
    {
    	
    	LoginInfo loginInfo = new LoginInfo();
    	Integer ratingId = null;
		if (null != volunteerRating) {
			ratingId= volunteerRatingRepostiry.save(volunteerRating).getRatingId();
			loginInfo.setMessage("Success");
			loginInfo.setStatusCode("0");
			loginInfo.setRatingId(ratingId);
		}
 		 return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK);
   
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
    @RequestMapping(value = "/getVolunteersList", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://15.207.42.209:8080")
    @ResponseBody
public ResponseEntity<VolunteerResponse> getVolunteerList(@RequestBody VolunteerVO volunteerStatus){
    	
    	VolunteerResponse vr=new VolunteerResponse();
    		vr=volunteerService.getVolunteerListByQuery(volunteerStatus);
        	if(!vr.getVolunteers().isEmpty() && vr.getVolunteers()!=null) {
        		
        		vr.setMessage("Success"); 
	    		vr.setStatusCode(0);
	    	
	    		return new ResponseEntity<VolunteerResponse>(vr, HttpStatus.OK);
        	}
    			
    			else {
    				
    				vr.setMessage("Failure");
    				vr.setStatusCode(1); 
    				return new ResponseEntity<VolunteerResponse>(vr, HttpStatus.CONFLICT);
    			}

    }
    
    @RequestMapping(value="/getSrCitizenList",method=RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://15.207.42.209:8080")
    @ResponseBody
 public ResponseEntity<SrCitizenResponse> getSrCitizenList(@RequestBody SrCitizenVO srCitizenStatus){
    	
    	SrCitizenResponse srCitizen =new SrCitizenResponse();
    	srCitizen=srCitizenService.getSeniorCitizen(srCitizenStatus);
    	
    	if(null!=srCitizen.getSrCitizenList() && !srCitizen.getSrCitizenList().isEmpty()) {
    		srCitizen.setMessage("Success");
    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.OK);
    	}
    	else {
    		srCitizen.setMessage("Failure");
    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.CONFLICT);
    	}
//    	if(srCitizenStatus.getLimit()==null && srCitizenStatus.getPagenumber()==null) {
//    		limit=10;
//    		pagenumber=0;
//    		
//    	}
//    	else {
//    	limit=srCitizenStatus.getLimit();
//    	
//    	pagenumber=srCitizenStatus.getPagenumber();
//    	}
//    	
//    	List<SeniorCitizen> srCitizenList;
//    	if(status.length()<=0) {
//    		
//    		srCitizenList=seniorcitizenRepository.findAll();
//    		
//    	}
//    	else {
////    		srCitizenList=seniorcitizenRepository.fetchByStatus(status);
//    		Pageable ptry=PageRequest.of(pagenumber, limit);
//    		srCitizenList=seniorcitizenRepository.findAllByStatus(status, ptry);
//    	}
//    		if(null!=srCitizenList && !srCitizenList.isEmpty()) {
//    		srCitizen.setSrCitizenList(srCitizenList);
//    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.OK);
//    	}
//    	else {
//    		
//    		return new ResponseEntity<SrCitizenResponse>(srCitizen,HttpStatus.CONFLICT);
//    	}
//    	
    	}
    
    @RequestMapping(value="/assignSrCitizen",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @CrossOrigin(origins = "http://15.207.42.209:8080")
    @ResponseBody
    public ResponseEntity<LoginInfo> assignSrCitizen(@RequestBody SrCitizenResponse srCitizen){
    	List<SeniorCitizen> srCitizenList= srCitizen.getSrCitizenList();
    	LoginInfo loginInfo = new LoginInfo();
   
 
    	if(srCitizenList!=null && !srCitizenList.isEmpty()) {
    	
    			for(SeniorCitizen sr:srCitizenList) {
    		
    				Integer idsrcitizen=sr.getSrCitizenId();
    				if(sr.getStatus().equalsIgnoreCase("UnAssigned") && idsrcitizen!=null) {
    						
    					srCitizenService.updateStatus("Assigned",idsrcitizen);
    					
    				}
    				
    				VolunteerAssignment vo=new VolunteerAssignment();
    				vo.setIdvolunteer(srCitizen.getIdvolunteer());
    				vo.setNamesrcitizen(sr.getFirstName());
		    		vo.setPhonenosrcitizen(sr.getPhoneNo());
		    		vo.setAgesrcitizen(sr.getAge());
		    		vo.setGendersrcitizen(String.valueOf(sr.getGender()));
		    		vo.setAddresssrcitizen(sr.getAddress());
		    		vo.setEmailsrcitizen(sr.getEmailID());
		    		vo.setStatesrcitizen(sr.getState());
		    		vo.setDistrictsrcitizen(sr.getDistrict());
		    		vo.setBlocknamesrcitizen(sr.getBlockName());
		    		vo.setVillagesrcitizen(sr.getVillage());
		    		vo.setCallstatusCode(1);
		    		vo.setRole(srCitizen.getRole());
		    		vo.setAdminId(srCitizen.getAdminId());
		    		volunteerassignmentRespository.save(vo);
		    		
		   }
    			loginInfo.setMessage("Success"); 
	    		loginInfo.setStatusCode("0");
	    		
	    		return new ResponseEntity<LoginInfo>(loginInfo,HttpStatus.OK);
    	}
    	else {
    		
    		 loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
    	}
    }
    
    @RequestMapping(value="/distributeSrCitizen",method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo> distributeSrCitizen(@RequestBody SrCitizenResponse srCitizen){
    	List<SeniorCitizen> srCitizenList= srCitizen.getSrCitizenList();
    	List<VolunteerVO> volList = srCitizen.getVolunteerList();
    	LoginInfo loginInfo = new LoginInfo();
    	Collection<List<SeniorCitizen>> srCitizenListChunks;
    	
    	Integer volListSize = volList.size();
    	
    	if(volListSize.equals(1)) {
    		assignSrCitizen(srCitizen);
    	}
		if (srCitizenList != null && !srCitizenList.isEmpty() && null != volList && !volList.isEmpty()) {
			srCitizenListChunks = chopIntoParts(srCitizen.getSrCitizenList(), volListSize);
			System.out.println(srCitizenListChunks.size() + srCitizenListChunks.toString());
			for (int i = 0; i<volList.size() ; i++) {
				for(List<SeniorCitizen> sr :srCitizenListChunks) {
					List<SeniorCitizen> sr1 = new ArrayList<>();
					sr1=sr;
					if(sr1!=null && !sr1.isEmpty()) {
				    	
		    			for(SeniorCitizen sr2:sr1) {
		    		
		    				Integer idsrcitizen=sr2.getSrCitizenId();
		    				if(sr2.getStatus().equalsIgnoreCase("UnAssigned") && idsrcitizen!=null) {
		    						
		    					srCitizenService.updateStatus("Assigned",idsrcitizen);
		    					
		    				}
		    				
		    				VolunteerAssignment vo=new VolunteerAssignment();
		    				vo.setIdvolunteer( volList.get(i).getIdvolunteer());
		    				vo.setNamesrcitizen(sr2.getFirstName());
				    		vo.setPhonenosrcitizen(sr2.getPhoneNo());
				    		vo.setAgesrcitizen(sr2.getAge());
				    		vo.setGendersrcitizen(String.valueOf(sr2.getGender()));
				    		vo.setAddresssrcitizen(sr2.getAddress());
				    		vo.setEmailsrcitizen(sr2.getEmailID());
				    		vo.setStatesrcitizen(sr2.getState());
				    		vo.setDistrictsrcitizen(sr2.getDistrict());
				    		vo.setBlocknamesrcitizen(sr2.getBlockName());
				    		vo.setVillagesrcitizen(sr2.getVillage());
				    		vo.setCallstatusCode(1);
				    		vo.setRole(volList.get(i).getRole());
				    		vo.setAdminId(volList.get(i).getAdminId());
				    		volunteerassignmentRespository.save(vo);
				    		
				   }
		    			
		    	}
					srCitizenListChunks.remove(sr);
					break;
					
				}
			}
			loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
    		
    		return new ResponseEntity<LoginInfo>(loginInfo,HttpStatus.OK);
		}
    	
   
 
    	
    	else {
    		
    		 loginInfo.setMessage("Failure"); 
			  loginInfo.setStatusCode("1"); 
			  return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.CONFLICT);
    	}
    }
    
    
    //Divide Senior citizen list to chunks which needs to be distributed among list of volunteers
    public static <T>List<List<T>> chopIntoParts( final List<T> ls, final int iParts )
	{
	    final List<List<T>> lsParts = new ArrayList<List<T>>();
	    final int iChunkSize = ls.size() / iParts;
	    int iLeftOver = ls.size() % iParts;
	    int iTake = iChunkSize;

	    for( int i = 0, iT = ls.size(); i < iT; i += iTake )
	    {
	        if( iLeftOver > 0 )
	        {
	            iLeftOver--;

	            iTake = iChunkSize + 1;
	        }
	        else
	        {
	            iTake = iChunkSize;
	        }

	        lsParts.add( new ArrayList<T>( ls.subList( i, Math.min( iT, i + iTake ) ) ) );
	    }

	    return lsParts;
	}

    public Volunteer mapVolunteerToEntity(Volunteer volunteer) {
    	
    	Volunteer volunteer1 = new Volunteer();
    	volunteer1 = volunteer;
    	List<VolunteerAssignment> volAssignment = new ArrayList<>();
    	volAssignment = volunteer.getVolunteercallList();
    	if(!volAssignment.isEmpty()) {
    		volAssignment = mapVolunteerAssignmentListtoEntity(volAssignment);
    	}
    	
    	volunteer1.setVolunteercallList(volAssignment);
    	
		return volunteer1;
    }
    
	public List<VolunteerAssignment> mapVolunteerAssignmentListtoEntity(List<VolunteerAssignment> volAssignment) {
		List<MedicalandGreivance> medList = new ArrayList<>();
		if(!volAssignment.isEmpty()) {
			volAssignment.forEach(v->v.setTalkedwith((null!=v.getTalkedwith() && !v.getTalkedwith().equals(""))?(v.getTalkedwith().equalsIgnoreCase(TalkedWith.SENIOR_CITIZEN.getValue()) ? "1" : v.getTalkedwith().equalsIgnoreCase(TalkedWith.FAMILY_MEMBER_OF_SR_CITIZEN.getValue()) ? "2" : 
				v.getTalkedwith().equalsIgnoreCase(TalkedWith.COMMUNITY_MEMBER.getValue()) ? "3" : null):null));
    	}
		for (VolunteerAssignment vol : volAssignment) {
			medList = mapMedicalandGreivanceListToEntity(vol);
		}
		return volAssignment;
	}
    
	public List<MedicalandGreivance> mapMedicalandGreivanceListToEntity(VolunteerAssignment vol) {

		List<MedicalandGreivance> medList = vol.getMedicalandgreivance();
		if (!medList.isEmpty()) {
			for (MedicalandGreivance medicalandgreivance : medList) {

				medicalandgreivance.setDiabetic(null != medicalandgreivance.getDiabetic() && !medicalandgreivance.getDiabetic().equals("")
						? (medicalandgreivance.getDiabetic().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");
				medicalandgreivance.setBloodpressure(null != medicalandgreivance.getBloodpressure() && !medicalandgreivance.getBloodpressure().equals("")
						?( medicalandgreivance.getBloodpressure().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");
				medicalandgreivance.setLungailment(null != medicalandgreivance.getLungailment() && !medicalandgreivance.getLungailment().equals("")
						?( medicalandgreivance.getLungailment().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");
				medicalandgreivance.setCancer_or_majorsurgery(null != medicalandgreivance.getCancer_or_majorsurgery() && !medicalandgreivance.getCancer_or_majorsurgery().equals("")
						?( medicalandgreivance.getCancer_or_majorsurgery().equalsIgnoreCase(InputEnum.Y.name()) ? "1"
								: "2"):"");
				medicalandgreivance.setOther_ailments(null != medicalandgreivance.getOther_ailments() && !medicalandgreivance.getOther_ailments().equals("")
						?(medicalandgreivance.getOther_ailments().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2"): "");
				String str = medicalandgreivance.getRelated_info_talked_about();
				StringJoiner str1 = new StringJoiner(",");
				if (str.contains(",")) {
					List<String> strList = Arrays.asList(str.split(","));
					for (String string : strList) {
						str1.add(string.equalsIgnoreCase(RelatedInfoTalkedAbout.PREVENTION.getValue()) ? "1"
								: string.equalsIgnoreCase(RelatedInfoTalkedAbout.ACCESS.getValue()) ? "2" : "3");
					}
				}

				medicalandgreivance.setRelated_info_talked_about(
						str.equalsIgnoreCase(RelatedInfoTalkedAbout.PREVENTION.getValue()) ? "1"
								: str.equalsIgnoreCase(RelatedInfoTalkedAbout.ACCESS.getValue()) ? "2"
										: str.equalsIgnoreCase(RelatedInfoTalkedAbout.DETECION.getValue()) ? "3"
												: str1.toString());

				if(null != medicalandgreivance.getBehavioural_change_noticed() && !medicalandgreivance.getBehavioural_change_noticed().equals("")) {
				medicalandgreivance.setBehavioural_change_noticed(
						 medicalandgreivance
								.getBehavioural_change_noticed().equalsIgnoreCase(BehaviouralChangeNoticed.YES.name())
										? "1"
										: medicalandgreivance.getBehavioural_change_noticed()
												.equalsIgnoreCase(BehaviouralChangeNoticed.NO.name())
														? "2"
														: medicalandgreivance.getBehavioural_change_noticed()
																.equalsIgnoreCase(
																		BehaviouralChangeNoticed.MAY_BE.name())
																				? "3"
																				: medicalandgreivance
																						.getBehavioural_change_noticed()
																						.equalsIgnoreCase(
																								BehaviouralChangeNoticed.NOT_APPLICABLE
																										.name()) ? "4"
																												: "");
				}
				medicalandgreivance.setIscovidsymptoms(null != medicalandgreivance.getIscovidsymptoms()&& !medicalandgreivance.getIscovidsymptoms().equals("")
						?(medicalandgreivance.getIscovidsymptoms().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2"):"");
				medicalandgreivance.setHas_shortnes_of_breath(null != medicalandgreivance.getHas_shortnes_of_breath() && !medicalandgreivance.getHas_shortnes_of_breath().equals("")
						?(medicalandgreivance.getHas_shortnes_of_breath().equalsIgnoreCase(InputEnum.Y.name()) ? "1"
								: "2"):"");
				medicalandgreivance.setHas_sorethroat(null != medicalandgreivance.getHas_sorethroat()&& !medicalandgreivance.getHas_sorethroat().equals("")
						?( medicalandgreivance.getHas_sorethroat().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2"):"");
				medicalandgreivance.setHascough(null != medicalandgreivance.getHascough() && !medicalandgreivance.getHascough().equals("")
						?(medicalandgreivance.getHascough().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2"):"");
				medicalandgreivance.setHasfever(null != medicalandgreivance.getHasfever() && !medicalandgreivance.getHasfever().equals("")
						?(medicalandgreivance.getHasfever().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2"):"");
				medicalandgreivance.setIsemergencyservicerequired(
						null != medicalandgreivance.getIsemergencyservicerequired() && !medicalandgreivance.getIsemergencyservicerequired().equals("")?( medicalandgreivance
								.getIsemergencyservicerequired().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");
				medicalandgreivance.setLackofessentialservices(null != medicalandgreivance.getLackofessentialservices() && !medicalandgreivance.getLackofessentialservices().equals("")
						?( medicalandgreivance.getLackofessentialservices().equalsIgnoreCase(InputEnum.YES.name()) ? "1"
								: "2"):"");
				medicalandgreivance.setIsSrCitizenAwareOfCovid_19(
						null != medicalandgreivance.getIsSrCitizenAwareOfCovid_19()  && !medicalandgreivance.getIsSrCitizenAwareOfCovid_19().equals("")?( medicalandgreivance
								.getIsSrCitizenAwareOfCovid_19().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");
				medicalandgreivance.setIsSymptomsPreventionTaken(
						null != medicalandgreivance.getIsSymptomsPreventionTaken() && !medicalandgreivance.getIsSymptomsPreventionTaken().equals("") ?( medicalandgreivance
								.getIsSymptomsPreventionTaken().equalsIgnoreCase(InputEnum.Y.name()) ? "1" : "2") : "");

			}
		}

		return medList;

	}
	
	public VolunteerVO mapEntityToVoluntnteerVO(Volunteer v) {
		VolunteerVO v1=new VolunteerVO();
		Float ratingList = null;
		String formattedStr = null;
		v1.setAddress(v.getAddress());
		v1.setAdminId(v.getAdminId());
		v1.setAssignedtoFellow(v.getAssignedtoFellow());
		v1.setAssignedtoFellowContact(v.getAssignedtoFellowContact());
		v1.setBlock(v.getBlock());
		v1.setDistrict(v.getDistrict());
		v1.setEmail(v.getEmail());
		v1.setFirstName(v.getFirstName());
		v1.setGender(v.getGender());
		v1.setIdvolunteer(v.getIdvolunteer());
		v1.setAssignedtoFellow(null != v.getAssignedtoFellow()&& !v.getAssignedtoFellow().equals("")?v.getAssignedtoFellow():null);
		v1.setAssignedtoFellowContact(null != v.getAssignedtoFellowContact() && !v.getAssignedtoFellowContact().equals("") ? v.getAssignedtoFellowContact():null);
		if (null != v.getIdvolunteer()) {
			ratingList = volunteerRatingRepostiry.getAvgRating(v.getIdvolunteer());
			DecimalFormat form = new DecimalFormat("0.0");
			formattedStr = null != ratingList ? form.format(ratingList) : null;
		}
		v1.setRating(null != formattedStr?Float.valueOf(formattedStr):null);
		v1.setLastName(v.getLastName());
		v1.setphoneNo(v.getphoneNo());
		v1.setPic(v.getPic());
		v1.setRole(v.getRole());
		v1.setState(v.getState());
		v1.setVillage(null != v.getVillage()&& !v.getVillage().equals("")?v.getVillage():null);
		
		return v1;
	}
	
	@RequestMapping(value = "/getCSVFile")
    @ResponseBody
public ResponseEntity<VolunteerResponse> getCSV(@RequestParam("file") MultipartFile file, @RequestParam("adminId") Integer adminId, @RequestParam("adminRole") Integer adminRole) throws IOException,ValidationException{
    
    	VolunteerResponse vr=new VolunteerResponse();
    	List<Volunteer> volunteers=new ArrayList<Volunteer>();
    	List<Volunteer> volunteerList=new ArrayList<Volunteer>();
		List<String>error = new ArrayList<String>();
        Set<String> hash_Set = new HashSet<String>();
    	Boolean flag=false;
    	List<Volunteer> mobileNo=new ArrayList<Volunteer>();
    	Set<String> mobile_set=new HashSet<String>();
    	String[] rows=null;
    	String[] columns=null;
    	
    	
    	if (!file.isEmpty() && isCsv(file)) {
     
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);
                 rows = completeData.split("\r\n");
                columns = rows[0].split(",");
              try {
        			for(int k=1;k<rows.length;k++) {
        				Volunteer vv=new Volunteer();
        				String[]attributes=rows[k].split(",");
        				for(int i=0;i<columns.length;i++) {
        					if(columns[i].equalsIgnoreCase("firstname")) {
        						error=ValidateCsv(attributes[i],columns[i]);
        						error.forEach(x->hash_Set.add(x));
        						vv.setFirstName(attributes[i]);
        						
        					}
        					else if(columns[i].equalsIgnoreCase("LASTNAME")) {
        						error=ValidateCsv(attributes[i],columns[i]);
        						error.forEach(x->hash_Set.add(x));
        						vv.setLastName(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("MOBILENO")) {
        						error=ValidateCsv(attributes[i],columns[i]);
        						mobileNo=ValidateMobile(attributes[i]);
        						mobileNo.forEach(v->mobile_set.add(v.getphoneNo()));
        						error.forEach(x->hash_Set.add(x));
        						vv.setphoneNo(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("EMAIL")) {
        						error=ValidateCsv(attributes[i],columns[i]);
        						error.forEach(x->hash_Set.add(x));
        						vv.setEmail(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("GENDER")) {
        						error=ValidateCsv(attributes[i],columns[i]);
        						error.forEach(x->hash_Set.add(x));
        						vv.setGender(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("STATE_NAME")) {
        						vv.setState(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("DISTRICT_NAME")) {
        						vv.setDistrict(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("ASSIGNED_TO_FELLOW")) {
        						vv.setAssignedtoFellow(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("ASSIGNED_TO_FELLOW_CONTACT")) {
        						vv.setAssignedtoFellowContact(attributes[i]);
        					}
        			
        					else if(columns[i].equalsIgnoreCase("status")) {
//        						if(!attributes[i].isBlank()){
        							vv.setStatus(attributes[i]);
//        							}
//        						else{
//        							vv.setStatus("Active");
//        						}
        							
        						
        					}
        					else if(columns[i].equalsIgnoreCase("BLOCK_NAME")) {
        						vv.setBlock(attributes[i]);
        					}
        					else if(columns[i].equalsIgnoreCase("VILLAGE")) {
        						vv.setVillage(attributes[i]);
        					}

        					vv.setRole(adminRole);

        					vv.setAdminId(adminId);
        					
        				}
        				
        				volunteerList.add(vv);
        			}
        			
        			
              if(isCsv(file) && hash_Set.size()<=0 && mobile_set.size()<=0 && volunteerList.size()>0) {
            	  flag=true;
            	  volunteerRespository.saveAll(volunteerList);
   
   				 
   				 	
   			    }
   			 else if(hash_Set.size()>0) {
   				 flag=false;
   				 throw new ValidationException(hash_Set+" "+"Cannot contain null values");
   			 }
   			 else if(mobile_set.size()>0) {
   				 throw new ValidationException(mobile_set+" "+"phone numbers are already present");
   			 }
   			    else {
   			    	flag=false;
   			    	
   			    }
              }catch(ValidationException ex) {
        				logger.error(ex.getMessage());
        				vr.setMessage(ex.getMessage());
        				vr.setStatusCode(1);
        				return new ResponseEntity<VolunteerResponse>(vr,HttpStatus.OK);
        			}
    	}
              if(flag) {
            	  vr.setMessage("Success");
 			    	vr.setStatusCode(0);
 			  
 			    	return new ResponseEntity<VolunteerResponse>(vr,HttpStatus.OK);
              }
              else {
            	  vr.setMessage("Failure");
 					vr.setStatusCode(1); 
 					return new ResponseEntity<VolunteerResponse>(vr, HttpStatus.CONFLICT);
              }
    	}
   
	public Boolean isCsv(MultipartFile file) {
		String type="csv";
   
    	String extension=file.getOriginalFilename().split("\\.")[1];
    	if(extension.equals(type)) {
    		return true;
    		
    	}
    	else {
    		return false;
    	}
	}
	public List<String> ValidateCsv(String attribute,String column) {
		List<String>errorColumn=new ArrayList<String>();
	
		if(attribute.length()<=0) {
			errorColumn.add(column);
//			throw new ValidationException("Column"+" "+column+" cannot take value null");
		}
		
		if(column.equalsIgnoreCase("gender") && attribute.length()>0) {
			if(!(attribute.equalsIgnoreCase("M")||attribute.equalsIgnoreCase("F"))) {
				throw new ValidationException("Gender must be M or F");
			}
		}
		
		return errorColumn;
	}
	public List<Volunteer> ValidateMobile(String attribute){
		List<Volunteer> volunteer=new ArrayList<Volunteer>();
		Optional<Volunteer> vo;
		Volunteer vv=new Volunteer();
		if(attribute.length()>0) {
		
			 vo=volunteerRespository.findByPhoneNo(attribute);
			if(vo.isPresent()) {
				vv=vo.get();
				volunteer.add(vv);
			}
		}
		return volunteer;
	}

    
    @RequestMapping(value = "/getVolunteerDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public  ResponseEntity<VolunteerResponse>  getVolunteerDetails(@RequestBody InputVO inputVO)
    {
    	LoginInfo loginInfo = new LoginInfo();
    	VolunteerResponse volunteerResponse=new VolunteerResponse();
		Optional<Volunteer> v1 = volunteerRespository.findById(inputVO.getId());
		List<VolunteerAssignment> volAssignment = new ArrayList<>();
			if (v1.isPresent()) {
				volunteerResponse.setMessage("Success");
				volunteerResponse.setStatusCode(0);
				Volunteer v = v1.get();
				volAssignment = v.getVolunteercallList();
				volAssignment.stream().forEach(p->p.setMedicalandgreivance(null));
				volunteerResponse.setSrCitizenList(volAssignment);
				volunteerResponse.setVolunteer(mapVolunteerToEntity1(v));
				volunteerResponse.setAvgRating(volunteerRatingRepostiry.getAvgRating(inputVO.getId()));
			}
    	else {
    		loginInfo.setMessage("Failure");
			loginInfo.setStatusCode("1"); 
    	}
		return new ResponseEntity<VolunteerResponse>(volunteerResponse, volunteerResponse.getStatusCode()==0 ? HttpStatus.OK :HttpStatus.CONFLICT);
}
    
public Volunteer mapVolunteerToEntity1(Volunteer volunteer) {
    	
    	Volunteer volunteer1 = new Volunteer();
    	volunteer1 = volunteer;
    	List<VolunteerAssignment> volAssignment = new ArrayList<>();
    	List<VolunteerAssignment> srCitizenList=new ArrayList<>();
    	List<VolunteerRating> volRating = new ArrayList<>();
    	List<VolunteerRating> volRating1 = new ArrayList<>();
    	List<VolunteerRating>finalList=new ArrayList<>();
    	volAssignment = volunteer.getVolunteercallList();
    	Set<Integer> admin_Id=new HashSet<Integer>();
    	volAssignment.stream().forEach(p->p.setMedicalandgreivance(null));
    	
    	for(VolunteerAssignment va:volAssignment) {
    		if(!va.getCallstatusCode().equals(1)) {
    			srCitizenList.add(va);
    		}
    	}
    	volRating = volunteer.getVolunteerRatingList();
    	//Need to add logic for taking cumulative of rating and show all reviews given by different admins
		/*
		 * if(null != volRating && !volRating.isEmpty()) { int i=0; for(int
		 * j=i+1;j<volRating.size();j++) {
		 * if(volRating.get(i).getAdminId().equals(volRating.get(j).getAdminId())) {
		 * float i1 = Float.parseFloat(volRating.get(i).getRating()); float i2 =
		 * Float.parseFloat(volRating.get(j).getRating()); float i3= (i1+i2)/2;
		 * 
		 * volRating.get(i).setRating(String.valueOf(i3)); volRating.remove(j); } } }
		 */
    	if(null != volRating && !volRating.isEmpty()) {
    		
    		for(VolunteerRating vr:volRating) {
    			admin_Id.add(vr.getAdminId());
    		}
    		
    		for(Integer a:admin_Id) {
    		volRating1=volunteerRatingRepostiry.getRatingByAdmin_id(volunteer.getIdvolunteer(), a);
    		finalList.add(volRating1.get(0));
    			}
    		}
    	
    	volunteer1.setVolunteercallList(srCitizenList);
    	volunteer1.setVolunteerRatingList(finalList);
    	
		return volunteer1;
    }
}