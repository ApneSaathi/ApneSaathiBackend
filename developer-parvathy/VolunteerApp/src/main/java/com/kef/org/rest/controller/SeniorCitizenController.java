package com.kef.org.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kef.org.rest.domain.model.InProgressQueryRequestVO;
import com.kef.org.rest.domain.model.InProgressQueryResponseVO;
import com.kef.org.rest.domain.model.InputVO;
import com.kef.org.rest.domain.model.SrCitizenDetailsResponse;
import com.kef.org.rest.domain.model.SrCitizenQueriesRequestVO;
import com.kef.org.rest.domain.model.SrCitizenQueryResponseVO;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.service.SeniorCitizenService;

@RestController
public class SeniorCitizenController {

	
	public static final Logger logger = LoggerFactory.getLogger(SeniorCitizenController.class);
	
	@Autowired
	SeniorCitizenService srCitizenService;
	
	
	@RequestMapping(value="/srCitizenPersonalinfo",method=RequestMethod.POST,consumes = "application/json", produces = "application/json")
   @ResponseBody
   public ResponseEntity<SrCitizenDetailsResponse> getSrCitizenPersonalInfo(@RequestBody InputVO inputVo ){
		
			SrCitizenDetailsResponse response= new SrCitizenDetailsResponse();
			response=srCitizenService.getSrCitizenPersonalInfo(inputVo.getId());
			if(response.getSrCitizenId()!=null ) {
				
				response.setStatusCode("0");
				response.setMessage("Success");;
			}
			else {
				
				response.setStatusCode("1");
				response.setMessage("Failure");
			}
		
			return new ResponseEntity<SrCitizenDetailsResponse>(response,response.getStatusCode().equals("0")? HttpStatus.OK : HttpStatus.CONFLICT);
	}
	
	/****
	 * API for Senior Citizen Queries
	 * @param requestJson
	 * @return
	 */
	@PostMapping(value="/srCitizenQueries")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody ResponseEntity<SrCitizenQueryResponseVO> getSeniorCitizenQueries(@RequestBody SrCitizenQueriesRequestVO requestJson) {
		
		return srCitizenService.getSeniorCitizenQueries(requestJson);
	}
	
	@RequestMapping(value="/updateGreivanceStatus",method=RequestMethod.PUT,consumes = "application/json", produces = "application/json")
	   @ResponseBody
	   public ResponseEntity<SrCitizenQueryResponseVO> updateGreivanceStatus(@RequestBody GreivanceTracking greivanceTracking){
			
		
		
		SrCitizenQueryResponseVO response= new SrCitizenQueryResponseVO();
		Boolean flag;
				flag=srCitizenService.updateGreivancestatus(greivanceTracking);
				if(flag) {
					
					response.setStatusCode("0");
					response.setMessage("Success");;
				}
				else {
					
					response.setStatusCode("1");
					response.setMessage("Failure");
				}
	
	
				return new ResponseEntity<SrCitizenQueryResponseVO>(response,response.getStatusCode().equals("0")? HttpStatus.OK : HttpStatus.CONFLICT);
		}
	
	@RequestMapping(value="/deboardSrCitizen",method=RequestMethod.PUT,consumes = "application/json", produces = "application/json")
	   @ResponseBody
	   public ResponseEntity<SrCitizenQueryResponseVO> deboardSrCitizen(@RequestBody InputVO inputVO){
			
		
		
		SrCitizenQueryResponseVO response= new SrCitizenQueryResponseVO();
		Boolean flag;
				flag=srCitizenService.deboardSrCitizen(inputVO);
				if(flag) {
					
					response.setStatusCode("0");
					response.setMessage("Success");;
				}
				else {
					
					response.setStatusCode("1");
					response.setMessage("Failure");
				}
	
	
				return new ResponseEntity<SrCitizenQueryResponseVO>(response,response.getStatusCode().equals("0")? HttpStatus.OK : HttpStatus.CONFLICT);
		}
	
	/***
	 * API to fetch InProgress Query details
	 * @param requestJson
	 * @return
	 */
	@PostMapping("/getInProgressQueryDetails")
	public @ResponseBody ResponseEntity<InProgressQueryResponseVO> getInProgressQueryDetails(
			@RequestBody InProgressQueryRequestVO requestJson){
		
		return srCitizenService.getInProgressQueryDetails(requestJson);
	}
}