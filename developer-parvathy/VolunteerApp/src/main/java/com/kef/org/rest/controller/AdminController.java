package com.kef.org.rest.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

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

import com.kef.org.rest.domain.model.Admin;
import com.kef.org.rest.domain.model.VolunteerVO;
import com.kef.org.rest.model.LoginInfo;
import com.kef.org.rest.model.Volunteer;
import com.kef.org.rest.repository.AdminRepository;
import com.kef.org.rest.repository.VolunteerRepository;
import com.kef.org.rest.service.AdminService;
import com.kef.org.rest.service.VolunteerService;

@RestController
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@Autowired
	private AdminRepository adminRespository;
	
	@Autowired
	private VolunteerRepository volunteerRepository;
	
	@Autowired
    VolunteerService volunteerService; 
	
	private static MessageDigest md;
	
	
	@RequestMapping(value = "/verifyPassword", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  verifyAdminPassword(@RequestBody Admin admin)
    { 
		LoginInfo loginInfo = new LoginInfo();
		com.kef.org.rest.model.Admin adminDAO = new com.kef.org.rest.model.Admin();
		String encryptedPwd = null != admin.getPassword() ? cryptWithMD5(admin.getPassword()) : null;
		
		if(null != admin) {
    		if(null!= admin.getAdminId()) {
    			Optional<com.kef.org.rest.model.Admin> adminOptional = adminRespository.findById(admin.getAdminId());
    			if(adminOptional.isPresent()) {
    				adminDAO = adminOptional.get();
    				if(null != adminDAO.getPassword() &&  encryptedPwd.equals(new String(adminDAO.getPassword()))) {
    					loginInfo.setMessage("Success"); 
    		    		loginInfo.setStatusCode("0");
    					
    				}else {
    					logger.info("Reached here"); 
    			  		  loginInfo.setMessage("Wrong Password!!Passwords do not match ");
    			  		  loginInfo.setStatusCode("1"); 
    			  		return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK );
    				}
    			}
    		}
    		
		}
		return new ResponseEntity<LoginInfo>(loginInfo, HttpStatus.OK );
    }
	
	public static String cryptWithMD5(String pass){
	    try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	    	logger.error(ex.getMessage());;
	    }
	        return null;


	   }
	
	@RequestMapping(value = "/getVolunteerList", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<LoginInfo>  getVolunteerList(@RequestBody Admin admin)
    { 
		LoginInfo loginInfo = new LoginInfo();
		List<Volunteer> volunteerList = new ArrayList<Volunteer>();
		List<VolunteerVO> volunteerVOList = new ArrayList<>();
		if(null != admin && null != admin.getAdminId()) {
		volunteerList = volunteerService.findAllVolunteerDetailsByAdminId(admin.getAdminId());
		for (Volunteer volunteer : volunteerList) {
			VolunteerVO volunteerVO = new VolunteerVO();
			volunteerVO.setAddress(null != volunteer.getAddress() ? volunteer.getAddress() : null);
			volunteerVO.setAdminId(null != volunteer.getAdminId() ? volunteer.getAdminId() : null);
			volunteerVO.setAssignedtoFellow(null != volunteer.getAssignedtoFellow() ? volunteer.getAssignedtoFellow() : null);
			volunteerVO.setAssignedtoFellowContact(null != volunteer.getAssignedtoFellowContact() ? volunteer.getAssignedtoFellowContact() : null);
			volunteerVO.setBlock(null != volunteer.getBlock() ? volunteer.getBlock() : null);
			volunteerVO.setDistrict(null != volunteer.getDistrict() ? volunteer.getDistrict() : null);
			volunteerVO.setEmail(null != volunteer.getEmail() ? volunteer.getEmail() : null);
			volunteerVO.setFirstName(null != volunteer.getFirstName() ? volunteer.getFirstName() : null);
			volunteerVO.setGender(null != volunteer.getGender() ? volunteer.getGender() : null);
			volunteerVO.setIdvolunteer(null != volunteer.getIdvolunteer() ? volunteer.getIdvolunteer() : null);
			volunteerVO.setLastName(null != volunteer.getLastName() ? volunteer.getLastName() : null);
			volunteerVO.setphoneNo(null!= volunteer.getphoneNo() ? volunteer.getphoneNo() :null);
			volunteerVO.setRole(null!= volunteer.getRole() ? volunteer.getRole() :null);
			volunteerVO.setState(null!= volunteer.getState() ? volunteer.getState() :null);
			volunteerVO.setVillage(null!= volunteer.getVillage() ? volunteer.getVillage() :null);
			volunteerVOList.add(volunteerVO);
		}
		
		if(null != volunteerVOList && !volunteerVOList.isEmpty()) {
			loginInfo.setVolunteers(volunteerVOList);
			loginInfo.setMessage("Success"); 
    		loginInfo.setStatusCode("0");
			
		}else {
			logger.info("Reached here"); 
	  		  loginInfo.setMessage("Failure");
	  		  loginInfo.setStatusCode("1"); 
		}
		}else {
			logger.info("Reached here"); 
	  		  loginInfo.setMessage("Admin Id not found");
	  		  loginInfo.setStatusCode("1"); 
		}
		
		
		return new ResponseEntity<LoginInfo>(loginInfo,loginInfo.getMessage().equalsIgnoreCase("Success")? HttpStatus.OK : HttpStatus.CONFLICT);
    }
	

}
