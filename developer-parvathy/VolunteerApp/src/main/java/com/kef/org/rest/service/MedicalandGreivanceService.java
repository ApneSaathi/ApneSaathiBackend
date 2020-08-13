package com.kef.org.rest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;

@Service("medicalandgreivanceservice")
public class MedicalandGreivanceService {
	
	@Autowired
	private MedicalandGreivanceRepository medicalandgreivancerespository;
	
	@Autowired
	private VolunteerAssignmentRepository volunteerassignmentrespository;
	
	@Autowired
	private GreivanceTrackingRepository greivanceTrackingRepository;

	
	private MedicalandGreivance medicalandgreivance;
	private GreivanceTracking greivanceTrackingDB;
	
	Integer idgreivance;
	Integer idgreivanceTracking;
	
	
	public static final Logger logger = LoggerFactory.getLogger(MedicalandGreivanceService.class);
	  
	@Transactional
	public Integer processformData(VolunteerAssignment volunteerassignement)
	{
		
		Integer callstatuscode;
		//Integer callstatussubcode;
		GreivanceTracking greivanceTracking = null;
		List<GreivanceTracking> greivanceTrackingList = null;
	
		
		String talkedwith;
		
		
		callstatuscode = volunteerassignement.getCallstatusCode();
		//callstatussubcode = volunteerassignement.getCallstatussubCode();
		talkedwith = volunteerassignement.getTalkedwith();
		
	
		
		
	//	volunteerassignmentrespository.save(volunteerassignement);
		
		if(callstatuscode.equals(10) )
		{   
			
			
			VolunteerAssignment va1 = volunteerassignmentrespository.findById(volunteerassignement.getCallid()).get(); 
			va1.setCallstatusCode(volunteerassignement.getCallstatusCode());
			//va1.setCallstatussubCode(volunteerassignement.getCallstatussubCode());
			va1.setLoggeddateTime(LocalDateTime.now());
			va1.setTalkedwith(volunteerassignement.getTalkedwith());
			va1.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
			va1.setIdvolunteer(null != volunteerassignement.getIdvolunteer() ? volunteerassignement.getIdvolunteer() :null);
			va1.setRole(null != volunteerassignement.getRole() ? volunteerassignement.getRole() : null);
			
			
			
			volunteerassignmentrespository.save(va1);
			
			 Iterator<MedicalandGreivance> medicalandgreivanceIterator =
					  volunteerassignement.getMedicalandgreivance().iterator();
					  
					  while(medicalandgreivanceIterator.hasNext()) { 
					  medicalandgreivance =  medicalandgreivanceIterator.next();
					  medicalandgreivance.setCreatedDate(LocalDateTime.now());
					  medicalandgreivance.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
					  medicalandgreivance.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
					  medicalandgreivance.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
					  medicalandgreivance.setIdvolunteer(volunteerassignement.getIdvolunteer());
					  medicalandgreivance.setRole(volunteerassignement.getRole());
					  medicalandgreivance.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
						  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
					  
					  
					  
					  idgreivance =	  medicalandgreivancerespository.save(medicalandgreivance).getIdgrevance();
					  
					  if("YES".equalsIgnoreCase(  medicalandgreivance.getLackofessentialservices()) || "Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired())) {
						  greivanceTrackingList = new ArrayList<>();
						  
						  if(4 != medicalandgreivance.getFoodshortage()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setGreivanceType("Lack of food");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getFoodshortage() ? "RAISED" : 
								  2 == medicalandgreivance.getFoodshortage() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getAceesstobankingissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of access to banking services");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getAceesstobankingissue() ? "RAISED" : 
								  2 == medicalandgreivance.getAceesstobankingissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getHygieneissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of hygiene and sanitation");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  greivanceTracking.setStatus(1 == medicalandgreivance.getHygieneissue() ? "RAISED" : 
								  2 == medicalandgreivance.getHygieneissue() ? "UNDER REVIEW" : "RESOLVED");
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getMedicineshortage()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of medicine");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getMedicineshortage() ? "RAISED" : 
								  2 == medicalandgreivance.getMedicineshortage() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getPhoneandinternetissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Phone & Internet services");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getPhoneandinternetissue() ? "RAISED" : 
								  2 == medicalandgreivance.getPhoneandinternetissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getSafetyissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of Safety");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getSafetyissue() ? "RAISED" : 
								  2 == medicalandgreivance.getSafetyissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getUtilitysupplyissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of utilities supply");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getUtilitysupplyissue() ? "RAISED" : 
								  2 == medicalandgreivance.getUtilitysupplyissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getEmergencyserviceissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(null != volunteerassignement.getAdminId() ? volunteerassignement.getAdminId() : va1.getAdminId());
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(volunteerassignement.getDistrictsrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of access to emergency services");
							  greivanceTracking.setIdvolunteer(volunteerassignement.getIdvolunteer());
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getEmergencyserviceissue() ? "RAISED" : 
								  2 == medicalandgreivance.getEmergencyserviceissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
							  }
							  greivanceTrackingList.add(greivanceTracking);
						  }
						  
						  
						  if(!greivanceTrackingList.isEmpty() && null != greivanceTrackingList) {
						  Iterator<GreivanceTracking> greivanceTrackingIterator =
								  greivanceTrackingList.iterator(); 
						  while(greivanceTrackingIterator.hasNext()) {
						  greivanceTrackingDB =  greivanceTrackingIterator.next();
						  
						  idgreivanceTracking =	  greivanceTrackingRepository.save(greivanceTrackingDB).getTrackingId();
						  }
						  }
						  
						  
					  }
					  }
					  
			
					 
					
					  return 	idgreivance;

						/*
						 * if(talkedwith.equals("Senior Citizen")) {
						 * 
						 * //va1.setMedicalandgreivance(volunteerassignement.getMedicalandgreivance());
						 * volunteerassignmentrespository.save(va1);
						 * 
						 * Iterator<MedicalandGreivance> medicalandgreivanceIterator =
						 * volunteerassignement.getMedicalandgreivance().iterator();
						 * 
						 * while(medicalandgreivanceIterator.hasNext()) { Integer idgreivancelocal;
						 * medicalandgreivance = medicalandgreivanceIterator.next();
						 * 
						 * idgreivance =
						 * medicalandgreivancerespository.save(medicalandgreivance).getIdgrevance();
						 * 
						 * 
						 * }
						 * 
						 * 
						 * return idgreivance;
						 * 
						 * }else {
						 * 
						 * 
						 * 
						 * // va1.setNamesrcitizen(volunteerassignement.getNamesrcitizen()); //
						 * va1.setAgesrcitizen(volunteerassignement.getAgesrcitizen()); //
						 * va1.setGendersrcitizen(volunteerassignement.getGendersrcitizen()); //
						 * va1.setPhonenosrcitizen(volunteerassignement.getPhonenosrcitizen());
						 * //.setAddresssrcitizen(volunteerassignement.getAddresssrcitizen());
						 * 
						 * //va1.setMedicalandgreivance(volunteerassignement.getMedicalandgreivance());
						 * volunteerassignmentrespository.save(va1);
						 * 
						 * 
						 * Iterator<MedicalandGreivance> medicalandgreivanceIterator =
						 * volunteerassignement.getMedicalandgreivance().iterator();
						 * 
						 * while(medicalandgreivanceIterator.hasNext()) { Integer idgreivancelocal;
						 * medicalandgreivance = medicalandgreivanceIterator.next();
						 * 
						 * idgreivance =
						 * medicalandgreivancerespository.save(medicalandgreivance).getIdgrevance();
						 * 
						 * 
						 * } return idgreivance;
						 * 
						 * 
						 * }
						 */
			}else {

				VolunteerAssignment va1 = volunteerassignmentrespository.findById(volunteerassignement.getCallid()).get(); 
				va1.setCallstatusCode(volunteerassignement.getCallstatusCode());
				if(null != volunteerassignement.getCallstatusCode() && volunteerassignement.getCallstatusCode().equals(9)){
					va1.setRemarks(null != volunteerassignement.getRemarks() ? volunteerassignement.getRemarks() : null);
				}
				va1.setLoggeddateTime(LocalDateTime.now());
				//va1.setCallstatussubCode(volunteerassignement.getCallstatussubCode());
				
				volunteerassignmentrespository.save(va1);
			
				return -1;
				
				  
				  }
				
				
				
			}
		
		}
		

	
	


