package com.kef.org.rest.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@Entity
@Table(name = "greivance_tracking")
@NamedQueries({
@NamedQuery(name = "GreivanceTracking.findAllbyidvolunteer",
query = "SELECT g FROM GreivanceTracking g WHERE g.idvolunteer =:idvolunteer and g.createdBy =:createdBy"
),
@NamedQuery(name = "GreivanceTracking.findAllbyadminId",
query = "SELECT g FROM GreivanceTracking g WHERE g.adminId =:adminId and g.createdBy =:createdBy"
),
@NamedQuery(name = "GreivanceTracking.findbytrackingid",
query = "SELECT g FROM GreivanceTracking g WHERE g.trackingId =:trackingId "
),
@NamedQuery(name = "GreivanceTracking.findAllbyDistrictName",
query = "SELECT g FROM GreivanceTracking g WHERE g.districtsrcitizen =:districtName "
)
}) 
public class GreivanceTracking {
	
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "TRACKINGID",nullable =false)
	  private Integer trackingId;
	  
	  @Column(name = "CALL_ID",nullable =false)
	  private Integer callid;
	  
	  @Column(name = "IDVOLUNTEER",nullable =false)
	  private Integer idvolunteer;
	  
	  @Column(name = "admin_id",nullable =false)
	  private Integer adminId;
	  
	  @Column(name="role")
	  private Integer role;
	  
	  @Column(name="district_srcitizen")
	  private String districtsrcitizen;
	  
	  @Column(name = "IDGREIVANCE",nullable =false)
	  private Integer idgrevance;
	  
	  @Column(name = "name_srcitizen",nullable =false)
	  private String namesrcitizen;
	  
	  @Column(name = "gender_srcitizen",nullable =false)
	  private String gendersrcitizen;

	  @Column(name = "type_of_greivance")
	  private String greivanceType;
	  
	  @Column(name = "status")
	  private String status;
	  
	  @Column(name = "description", columnDefinition ="varchar(255) default ''")
	  private String description;
	  
	  @Column(name = "createddate", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , nullable =false)
	  private LocalDateTime createdDate;
	  
	  @Column(name = "priority",columnDefinition ="varchar(255) default 'Low'")
	  private String priority;
	  
	  @Column(name = "lastupdatedon", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime lastUpdatedOn;
	  
	  
	  @Column(name = "underreviewdate", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime underReviewDate;
	  
	  @Column(name = "resolveddate", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime resolvedDate;
	  
	  @Column(name = "underreviewremarks", columnDefinition ="varchar(45) default ''")
	  private String underReviewRemarks;
	  
	  @Column(name = "resolvedremarks", columnDefinition ="varchar(45) default ''")
	  private String resolvedRemarks;
	  
	  @Column(name = "createdby",columnDefinition ="varchar(255) default NULL")
	  private String createdBy;

	

	public LocalDateTime getUnderReviewDate() {
		return underReviewDate;
	}

	public void setUnderReviewDate(LocalDateTime underReviewDate) {
		this.underReviewDate = underReviewDate;
	}

	public LocalDateTime getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(LocalDateTime resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public String getUnderReviewRemarks() {
		return underReviewRemarks;
	}

	public void setUnderReviewRemarks(String underReviewRemarks) {
		this.underReviewRemarks = underReviewRemarks;
	}

	public String getResolvedRemarks() {
		return resolvedRemarks;
	}

	public void setResolvedRemarks(String resolvedRemarks) {
		this.resolvedRemarks = resolvedRemarks;
	}

	public String getPriority() {
		return priority;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
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


	public String getNamesrcitizen() {
		return namesrcitizen;
	}

	public void setNamesrcitizen(String namesrcitizen) {
		this.namesrcitizen = namesrcitizen;
	}

	public String getGendersrcitizen() {
		return gendersrcitizen;
	}

	public void setGendersrcitizen(String gendersrcitizen) {
		this.gendersrcitizen = gendersrcitizen;
	}

	public String getGreivanceType() {
		return greivanceType;
	}

	public void setGreivanceType(String greivanceType) {
		this.greivanceType = greivanceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIdgrevance() {
		return idgrevance;
	}

	public void setIdgrevance(Integer idgrevance) {
		this.idgrevance = idgrevance;
	}

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

	public String getDistrictsrcitizen() {
		return districtsrcitizen;
	}

	public void setDistrictsrcitizen(String districtsrcitizen) {
		this.districtsrcitizen = districtsrcitizen;
	}
	  
	  
}
