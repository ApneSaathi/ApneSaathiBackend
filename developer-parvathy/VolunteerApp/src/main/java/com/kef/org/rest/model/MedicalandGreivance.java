package com.kef.org.rest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "srcitizen_greivance_log")
public class MedicalandGreivance {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "IDGREIVANCE",nullable =false)
	  private Integer idgrevance;
	  
	  
	  @Column(name = "CALL_ID",nullable =false)
	  private Integer callid;
	  
	  
	  @Column(name = "IDVOLUNTEER")
	  private Integer idvolunteer;
	  
	  @Column(name = "admin_id",nullable =false)
	  private Integer adminId;
	  
	  @Column(name="role")
	  private Integer role;
	  
	  @Column(name = "is_diabetic",columnDefinition ="varchar(10) default 'N'")
	  private String diabetic;
	  
	  @Column(name = "is_bloddpressure",columnDefinition ="varchar(10) default 'N'")
	  private String bloodpressure;
	  
	  @Column(name = "is_lungailment",columnDefinition ="varchar(10) default 'N'")
	  private String lungailment;
	  
	  @Column(name = "is_cancer_or_major_surgery",columnDefinition ="varchar(10) default 'N'")
	  private String cancer_or_majorsurgery;
	  
	  @Column(name = "is_other_ailments",columnDefinition ="varchar(10) default 'N'")
	  private String other_ailments;
	  
	  @Column(name = "remarks_medical_history",columnDefinition ="varchar(255) default ''")
	  private String remarks_medical_history;
	  
	  @Column(name = "related_info_talked_about",columnDefinition ="varchar(255) default 'Prevention'")
	  private String related_info_talked_about;
	  
	  @Column(name = "behavioural_change_noticed",columnDefinition ="varchar(50) default 'Yes'")
	  private String behavioural_change_noticed;
	  
	  
	  
	  @Column(name = "is_covid_symptoms",columnDefinition ="varchar(10) default 'N'")
	  private String iscovidsymptoms;
	  
	  
	  @Column(name = "has_cough",columnDefinition ="varchar(10) default 'N'")
	  private String hascough;
	  
	  @Column(name = "has_fever",columnDefinition ="varchar(10) default 'N'")
	  private String hasfever;
	  
	  @Column(name = "has_shortnessofbreath",columnDefinition ="varchar(10) default 'N'")
	  private String has_shortnes_of_breath;

	  @Column(name = "has_sore_throat",columnDefinition ="varchar(10) default 'N'")
	  private String has_sorethroat;
	  
	  //0.Not quarantine,1. Home Quarantine, 2. Govt Quarantine, 3. Hospitalized
	  @Column(name = "quarantine_status",columnDefinition ="integer default 0")
	  private Integer quarantinestatus;
//lack_of_essential_services
	  //lack_of_essential_services
	  @Column(name = "lack_of_essential_services",columnDefinition ="varchar(10) default 'No'")
	  private String lackofessentialservices;
	  
	  //1. Raised 2. Under Review 3. Resolved 4 - No issue
	  @Column(name = "is_foodshortage",columnDefinition ="integer default 4")
	  private Integer foodshortage;
	  
	  @Column(name = "is_medicine_shortage",columnDefinition ="integer default 4")
	  private Integer medicineshortage;
	  
	  @Column(name = "is_access_to_bank",columnDefinition ="integer default 4")
	  private Integer aceesstobankingissue;
	  


	@Column(name = "is_utility_supply_issue",columnDefinition ="integer default 4")
	  private Integer utilitysupplyissue;
	  
	  @Column(name = "is_hygene_santiation_issue",columnDefinition ="integer default 4")
	  private Integer hygieneissue;
	  
	  @Column(name = "is_safety_issue",columnDefinition ="integer default 4")
	  private Integer safetyissue;
	  
	  @Column(name = "is_emergency_service_issue",columnDefinition ="integer default 4")
	  private Integer emergencyserviceissue;
	  
	  @Column(name = "is_phone_internet_issue",columnDefinition ="integer default 4")
	  private Integer phoneandinternetissue;
	  
	  @Column(name = "emerygency_flag",columnDefinition ="varchar(10) default 'N'")
	  private String isemergencyservicerequired;
	  
	  @Column(name = "remarks_important_info",columnDefinition ="varchar(255) default ''")
	  private String remakrsimportantinfo;
	  
	  @Column(name = "loggeddatetime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	  private LocalDateTime loggeddattime;
	  
	  @Column(name = "description", columnDefinition ="varchar(255) default ''")
	  private String description;
	  
	  @Column(name = "createddate", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , nullable =false)
	  private LocalDateTime createdDate;
	  
	  @Column(name = "priority",columnDefinition ="varchar(255) default 'Low'")
	  private String priority;
	  
	  @Column(name = "lastupdatedon", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime lastUpdatedOn;
	  
	  @Column(name = "is_sr_citizen_aware_of_covid_19",columnDefinition ="varchar(2) default 'N'")
	  private String isSrCitizenAwareOfCovid_19;
	  
	  @Column(name = "is_symptoms_prevention_taken",columnDefinition ="varchar(2) default 'N'")
	  private String isSymptomsPreventionTaken;
	  
	  @Column(name = "practice_not_allowed",columnDefinition ="varchar(255) default NULL")
	  private String practiceNotAllowed;
	  
	  @Column(name = "createdby",columnDefinition ="varchar(255) default NULL")
	  private String createdBy;
	  
	  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	  @JoinColumn(name = "IDGREIVANCE")
	  private List <GreivanceTracking> greivanceTracking;
	  
	

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getIsSrCitizenAwareOfCovid_19() {
		return isSrCitizenAwareOfCovid_19;
	}

	public void setIsSrCitizenAwareOfCovid_19(String isSrCitizenAwareOfCovid_19) {
		this.isSrCitizenAwareOfCovid_19 = isSrCitizenAwareOfCovid_19;
	}

	public String getIsSymptomsPreventionTaken() {
		return isSymptomsPreventionTaken;
	}

	public void setIsSymptomsPreventionTaken(String isSymptomsPreventionTaken) {
		this.isSymptomsPreventionTaken = isSymptomsPreventionTaken;
	}

	public String getPracticeNotAllowed() {
		return practiceNotAllowed;
	}

	public void setPracticeNotAllowed(String practiceNotAllowed) {
		this.practiceNotAllowed = practiceNotAllowed;
	}

	public LocalDateTime getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	
	  public List<GreivanceTracking> getGreivanceTracking() {
		return greivanceTracking;
	}

	public void setGreivanceTracking(List<GreivanceTracking> greivanceTracking) {
		this.greivanceTracking = greivanceTracking;
	}

	public Integer getIdgrevance() {
		return idgrevance;
	}

	public void setIdgrevance(Integer idgrevance) {
		this.idgrevance = idgrevance;
	}

	public Integer getCallid() {
		return callid;
	}

	public void setCallid(Integer callid) {
		this.callid = callid;
	}

	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}




	public String getDiabetic() {
		return diabetic;
	}

	public void setDiabetic(String diabetic) {
		this.diabetic = diabetic;
	}

	public String getBloodpressure() {
		return bloodpressure;
	}

	public void setBloodpressure(String bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public String getLungailment() {
		return lungailment;
	}

	public void setLungailment(String lungailment) {
		this.lungailment = lungailment;
	}

	public String getCancer_or_majorsurgery() {
		return cancer_or_majorsurgery;
	}

	public void setCancer_or_majorsurgery(String cancer_or_majorsurgery) {
		this.cancer_or_majorsurgery = cancer_or_majorsurgery;
	}

	public String getOther_ailments() {
		return other_ailments;
	}

	public void setOther_ailments(String other_ailments) {
		this.other_ailments = other_ailments;
	}

	public String getRemarks_medical_history() {
		return remarks_medical_history;
	}

	public void setRemarks_medical_history(String remarks_medical_history) {
		this.remarks_medical_history = remarks_medical_history;
	}

	public String getRelated_info_talked_about() {
		return related_info_talked_about;
	}

	public void setRelated_info_talked_about(String related_info_talked_about) {
		this.related_info_talked_about = related_info_talked_about;
	}

	public String getBehavioural_change_noticed() {
		return behavioural_change_noticed;
	}

	public void setBehavioural_change_noticed(String behavioural_change_noticed) {
		this.behavioural_change_noticed = behavioural_change_noticed;
	}

	public String getIscovidsymptoms() {
		return iscovidsymptoms;
	}

	public void setIscovidsymptoms(String iscovidsymptoms) {
		this.iscovidsymptoms = iscovidsymptoms;
	}

	public String getHascough() {
		return hascough;
	}

	public void setHascough(String hascough) {
		this.hascough = hascough;
	}

	public String getHasfever() {
		return hasfever;
	}

	public void setHasfever(String hasfever) {
		this.hasfever = hasfever;
	}

	public String getHas_shortnes_of_breath() {
		return has_shortnes_of_breath;
	}

	public void setHas_shortnes_of_breath(String has_shortnes_of_breath) {
		this.has_shortnes_of_breath = has_shortnes_of_breath;
	}

	public String getHas_sorethroat() {
		return has_sorethroat;
	}

	public void setHas_sorethroat(String has_sorethroat) {
		this.has_sorethroat = has_sorethroat;
	}

	public Integer getQuarantinestatus() {
		return quarantinestatus;
	}

	public void setQuarantinestatus(Integer quarantinestatus) {
		this.quarantinestatus = quarantinestatus;
	}
	
	  public String getLackofessentialservices() {
		return lackofessentialservices;
	}

	public void setLackofessentialservices(String lackofessentialservices) {
		this.lackofessentialservices = lackofessentialservices;
	}

	public Integer getFoodshortage() {
		return foodshortage;
	}

	public void setFoodshortage(Integer foodshortage) {
		this.foodshortage = foodshortage;
	}

	public Integer getMedicineshortage() {
		return medicineshortage;
	}

	public void setMedicineshortage(Integer medicineshortage) {
		this.medicineshortage = medicineshortage;
	}

	public Integer getAceesstobankingissue() {
		return aceesstobankingissue;
	}

	public void setAceesstobankingissue(Integer aceesstobankingissue) {
		this.aceesstobankingissue = aceesstobankingissue;
	}

	public Integer getUtilitysupplyissue() {
		return utilitysupplyissue;
	}

	public void setUtilitysupplyissue(Integer utilitysupplyissue) {
		this.utilitysupplyissue = utilitysupplyissue;
	}

	public Integer getHygieneissue() {
		return hygieneissue;
	}

	public void setHygieneissue(Integer hygieneissue) {
		this.hygieneissue = hygieneissue;
	}

	public Integer getSafetyissue() {
		return safetyissue;
	}

	public void setSafetyissue(Integer safetyissue) {
		this.safetyissue = safetyissue;
	}

	public Integer getEmergencyserviceissue() {
		return emergencyserviceissue;
	}

	public void setEmergencyserviceissue(Integer emergencyserviceissue) {
		this.emergencyserviceissue = emergencyserviceissue;
	}

	public Integer getPhoneandinternetissue() {
		return phoneandinternetissue;
	}

	public void setPhoneandinternetissue(Integer phoneandinternetissue) {
		this.phoneandinternetissue = phoneandinternetissue;
	}

	public String getIsemergencyservicerequired() {
		return isemergencyservicerequired;
	}

	public void setIsemergencyservicerequired(String isemergencyservicerequired) {
		this.isemergencyservicerequired = isemergencyservicerequired;
	}

	public String getRemakrsimportantinfo() {
		return remakrsimportantinfo;
	}

	public void setRemakrsimportantinfo(String remakrsimportantinfo) {
		this.remakrsimportantinfo = remakrsimportantinfo;
	}

	public LocalDateTime getLoggeddattime() {
		return loggeddattime;
	}

	public void setLoggeddattime(LocalDateTime loggeddattime) {
		this.loggeddattime = loggeddattime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	  
	  
	  
	  
	  
 

}
