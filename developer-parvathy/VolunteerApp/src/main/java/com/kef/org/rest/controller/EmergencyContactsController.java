package com.kef.org.rest.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.kef.org.rest.model.District;
import com.kef.org.rest.model.EmergencyContacts;
import com.kef.org.rest.model.EmergencyContactsInfo;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.LoginInfo;
import com.kef.org.rest.repository.EmergencyContactsRepository;

@RestController
public class EmergencyContactsController {
	
	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	EmergencyContactsRepository emergencyContactsRepository;
	
	@RequestMapping(value = "/getEmergencyContactDetails", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<EmergencyContactsInfo>  fetchEmergencyContactDetails(@RequestBody EmergencyContacts emergencyContacts)
    {
		EmergencyContactsInfo emergencyContactsInfo = new EmergencyContactsInfo();
    	List<EmergencyContacts> emergencyContactsList = new ArrayList<>();
    	if(null != emergencyContacts.getDistrictId()) {
    	emergencyContactsList = emergencyContactsRepository.fetchEmergencyContactsByDistrictId(emergencyContacts.getDistrictId());
		
		  if(null != emergencyContactsList && !emergencyContactsList.isEmpty()) {
			  emergencyContactsInfo.setMessage("Success");
			  emergencyContactsInfo.setStatusCode("0"); 
			  emergencyContactsInfo.setEmergencyContactsList(emergencyContactsList);
		  return new ResponseEntity<EmergencyContactsInfo>(emergencyContactsInfo, HttpStatus.OK);
		  
		  }else { 
			  emergencyContactsInfo.setMessage("Emergency Contacts doesn't exist for this district"); 
			  emergencyContactsInfo.setStatusCode("1"); 
			  return new ResponseEntity<EmergencyContactsInfo>(emergencyContactsInfo, HttpStatus.BAD_REQUEST);
		  
		  }
    	}else {
    		emergencyContactsInfo.setMessage("District Id can't be null"); 
			  emergencyContactsInfo.setStatusCode("1"); 
			  return new ResponseEntity<EmergencyContactsInfo>(emergencyContactsInfo, HttpStatus.BAD_REQUEST);
		  
    	}
    	
    }
    
}
