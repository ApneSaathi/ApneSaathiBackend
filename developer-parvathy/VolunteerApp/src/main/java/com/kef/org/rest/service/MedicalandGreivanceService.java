package com.kef.org.rest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kef.org.rest.enums.BehaviouralChangeNoticed;
import com.kef.org.rest.enums.InputEnum;
import com.kef.org.rest.enums.RelatedInfoTalkedAbout;
import com.kef.org.rest.enums.TalkedWith;
import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.MedicalandGreivance;
import com.kef.org.rest.model.VolunteerAssignment;
import com.kef.org.rest.repository.AdminRepository;
import com.kef.org.rest.repository.GreivanceTrackingRepository;
import com.kef.org.rest.repository.MedicalandGreivanceRepository;
import com.kef.org.rest.repository.VolunteerAssignmentRepository;
import com.kef.org.rest.repository.VolunteerRepository;

@Service("medicalandgreivanceservice")
public class MedicalandGreivanceService {
	
	@Autowired
	private MedicalandGreivanceRepository medicalandgreivancerespository;
	
	@Autowired
	private VolunteerAssignmentRepository volunteerassignmentrespository;
	
	@Autowired
	private GreivanceTrackingRepository greivanceTrackingRepository;
	
	@Autowired
	private VolunteerRepository volunteerRepository;
	
	@Autowired
	private AdminRepository adminRepository;

	
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
		String relatedInfoTalkedAbout;
		
		
		callstatuscode = volunteerassignement.getCallstatusCode();
		//callstatussubcode = volunteerassignement.getCallstatussubCode();
		talkedwith = volunteerassignement.getTalkedwith().equals("1") ? TalkedWith.SENIOR_CITIZEN.getValue() : volunteerassignement.getTalkedwith().equals("2") ? TalkedWith.FAMILY_MEMBER_OF_SR_CITIZEN.getValue() : 
				volunteerassignement.getTalkedwith().equals("3") ? TalkedWith.COMMUNITY_MEMBER.getValue() : null;
		
	
		
		
	//	volunteerassignmentrespository.save(volunteerassignement);
		
		if(callstatuscode.equals(10) )
		{   
			
			
			VolunteerAssignment va1 = volunteerassignmentrespository.findById(volunteerassignement.getCallid()).get(); 
			va1.setCallstatusCode(volunteerassignement.getCallstatusCode());
			//va1.setCallstatussubCode(volunteerassignement.getCallstatussubCode());
			va1.setLoggeddateTime(LocalDateTime.now());
			va1.setTalkedwith(talkedwith!=null ? talkedwith : va1.getTalkedwith());
			va1.setAdminId(null != volunteerassignement.getRole() & (volunteerassignement.getRole().equals(2) || volunteerassignement.getRole().equals(4)) ?  volunteerassignement.getIdvolunteer() : va1.getAdminId());
			va1.setIdvolunteer(null != volunteerassignement.getRole() & volunteerassignement.getRole().equals(1) ? volunteerassignement.getIdvolunteer() :null);
			va1.setRole(null != volunteerassignement.getRole() ? volunteerassignement.getRole() : null);
			
			String name = "";
			
			if(null!=va1.getIdvolunteer() && va1.getRole().equals(1)) {
				name = volunteerRepository.fetchVolunteerNameById(va1.getIdvolunteer());
			}else if(null!=va1.getAdminId() && (va1.getRole().equals(2) || va1.getRole().equals(4))) {
				name = adminRepository.fetchAdminNameByAdminId(va1.getAdminId());
			}
			
			volunteerassignmentrespository.save(va1);
			
			 Iterator<MedicalandGreivance> medicalandgreivanceIterator =
					  volunteerassignement.getMedicalandgreivance().iterator();
					  
					  while(medicalandgreivanceIterator.hasNext()) { 
					  medicalandgreivance =  medicalandgreivanceIterator.next();
					  medicalandgreivance.setCreatedDate(LocalDateTime.now());
					  medicalandgreivance.setDiabetic(medicalandgreivance.getDiabetic().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setBloodpressure(medicalandgreivance.getBloodpressure().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setLungailment(medicalandgreivance.getLungailment().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setCancer_or_majorsurgery(medicalandgreivance.getCancer_or_majorsurgery().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setOther_ailments(medicalandgreivance.getOther_ailments().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
						String str = medicalandgreivance.getRelated_info_talked_about();
						StringJoiner str1 = new StringJoiner(",");
						if (str.contains(",")) {
							List<String> strList = Arrays.asList(str.split(","));
							for (String string : strList) {
								str1.add(string.equals("1") ? RelatedInfoTalkedAbout.PREVENTION.getValue()
										: string.equals("2") ? RelatedInfoTalkedAbout.ACCESS.getValue()
												: RelatedInfoTalkedAbout.DETECION.getValue());
							}
						}

						medicalandgreivance.setRelated_info_talked_about(
								str.equals("1") ? RelatedInfoTalkedAbout.PREVENTION.getValue()
										: str.equals("2") ? RelatedInfoTalkedAbout.ACCESS.getValue()
												: str.equals("3") ? RelatedInfoTalkedAbout.DETECION.getValue()
														: str1.toString());
					  
						medicalandgreivance.setBehavioural_change_noticed(
								medicalandgreivance.getBehavioural_change_noticed().equals("1")
										? BehaviouralChangeNoticed.YES.name()
										: medicalandgreivance.getBehavioural_change_noticed().equals("2")
												? BehaviouralChangeNoticed.NO.name()
												: medicalandgreivance.getBehavioural_change_noticed().equals("3")
														? BehaviouralChangeNoticed.MAY_BE.name()
														: medicalandgreivance.getBehavioural_change_noticed().equals(
																"4") ? BehaviouralChangeNoticed.NOT_APPLICABLE.name()
																		: null);
					  medicalandgreivance.setIscovidsymptoms(medicalandgreivance.getIscovidsymptoms().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setHas_shortnes_of_breath(medicalandgreivance.getHas_shortnes_of_breath().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setHas_sorethroat(medicalandgreivance.getHas_sorethroat().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setHascough(medicalandgreivance.getHascough().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setHasfever(medicalandgreivance.getHasfever().equals("1") ? InputEnum.Y.name(): InputEnum.N.name()); 
					  medicalandgreivance.setIsemergencyservicerequired(medicalandgreivance.getIsemergencyservicerequired().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setLackofessentialservices(medicalandgreivance.getLackofessentialservices().equals("1") ? InputEnum.YES.name(): InputEnum.NO.name());
					  medicalandgreivance.setIsSrCitizenAwareOfCovid_19(medicalandgreivance.getIsSrCitizenAwareOfCovid_19().equals("1") ? InputEnum.Y.name(): InputEnum.N.name());
					  medicalandgreivance.setIsSymptomsPreventionTaken(null != medicalandgreivance.getIsSymptomsPreventionTaken() && !medicalandgreivance.getIsSymptomsPreventionTaken().equals("") ?( medicalandgreivance.getIsSymptomsPreventionTaken().equals("1") ? InputEnum.Y.name(): InputEnum.N.name()) : "");
					  medicalandgreivance.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
					  medicalandgreivance.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
					  medicalandgreivance.setAdminId(null != volunteerassignement.getRole() & (volunteerassignement.getRole().equals(2) || volunteerassignement.getRole().equals(4)) ?  volunteerassignement.getIdvolunteer() : va1.getAdminId());
					  medicalandgreivance.setIdvolunteer(null != volunteerassignement.getRole() & volunteerassignement.getRole().equals(1) ? volunteerassignement.getIdvolunteer() :null);
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
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setGreivanceType("Lack of food");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getFoodshortage() ? "RAISED" : 
								  2 == medicalandgreivance.getFoodshortage() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getAceesstobankingissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of access to banking services");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getAceesstobankingissue() ? "RAISED" : 
								  2 == medicalandgreivance.getAceesstobankingissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getHygieneissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of hygiene and sanitation");
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
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of medicine");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getMedicineshortage() ? "RAISED" : 
								  2 == medicalandgreivance.getMedicineshortage() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getPhoneandinternetissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Phone & Internet services");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getPhoneandinternetissue() ? "RAISED" : 
								  2 == medicalandgreivance.getPhoneandinternetissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getSafetyissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of Safety");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getSafetyissue() ? "RAISED" : 
								  2 == medicalandgreivance.getSafetyissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getUtilitysupplyissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of utilities supply");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getUtilitysupplyissue() ? "RAISED" : 
								  2 == medicalandgreivance.getUtilitysupplyissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
							  }
							  greivanceTrackingList.add(greivanceTracking);
							  
						  }
						  
						  if(4 != medicalandgreivance.getEmergencyserviceissue()) {
							  greivanceTracking = new GreivanceTracking();
							  greivanceTracking.setCallid(medicalandgreivance.getCallid());
							  greivanceTracking.setGendersrcitizen(volunteerassignement.getGendersrcitizen());
							  greivanceTracking.setIdgrevance(idgreivance);
							  greivanceTracking.setAdminId(medicalandgreivance.getAdminId());
							  greivanceTracking.setIdvolunteer(medicalandgreivance.getIdvolunteer());
							  greivanceTracking.setRole(volunteerassignement.getRole());
							  greivanceTracking.setDistrictsrcitizen(va1.getDistrictsrcitizen());
							  greivanceTracking.setPhoneNo(va1.getPhonenosrcitizen());
							  greivanceTracking.setCreatedBy(null != volunteerassignement.getRole() && volunteerassignement.getRole() == 1 ? "Volunteer" :
								  null != volunteerassignement.getRole() && volunteerassignement.getRole() == 2 ? "Staff Member"  : "Master Admin");
							  
							  greivanceTracking.setGreivanceType("Lack of access to emergency services");
							  greivanceTracking.setNamesrcitizen(volunteerassignement.getNamesrcitizen());
							  greivanceTracking.setPriority("Y".equalsIgnoreCase(medicalandgreivance.getIsemergencyservicerequired()) ? "Emergency" : "Regular");
							  greivanceTracking.setStatus(1 == medicalandgreivance.getEmergencyserviceissue() ? "RAISED" : 
								  2 == medicalandgreivance.getEmergencyserviceissue() ? "UNDER REVIEW" : "RESOLVED");
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RAISED")) {
								  greivanceTracking.setDescription(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setCreatedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setRaisedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("UNDER REVIEW")) {
								  greivanceTracking.setUnderReviewRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setUnderReviewDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setReviewedby(name);
							  }
							  if(greivanceTracking.getStatus().equalsIgnoreCase("RESOLVED")) {
								  greivanceTracking.setResolvedRemarks(null != medicalandgreivance.getDescription() ? medicalandgreivance.getDescription() : null);
								  greivanceTracking.setResolvedDate(null != medicalandgreivance.getCreatedDate() ? medicalandgreivance.getCreatedDate() : LocalDateTime.now());
								  greivanceTracking.setResolvedby(name);
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
		

	
	


