package com.kef.org.rest.domain.model;

import java.time.LocalDateTime;


public class GreivanceTrackingVO {
	
	  private Integer id;

	  private Integer trackingId;
	  
	  private Integer callid;
	  
	  private Integer idvolunteer;
	  
	  private Integer adminId;
	  
	  private Integer role;
	  
	  private Integer districtId;
	  
	  private Integer idgrevance;
	  
	  private String namesrcitizen;
	  
	  private String gendersrcitizen;

	  private String greivanceType;
	  
	  private String status;
	  
	  private String description;
	  
	  private LocalDateTime createdDate;
	  
	  private String priority;
	  
	  private LocalDateTime lastUpdatedOn;
	  
	  
	  private LocalDateTime underReviewDate;
	  
	  private LocalDateTime resolvedDate;
	  
	  private String underReviewRemarks;
	  
	  private String resolvedRemarks;
	  
	  private String createdBy;
	  
	  private Integer filterBy;

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

	public Integer getIdgrevance() {
		return idgrevance;
	}

	public void setIdgrevance(Integer idgrevance) {
		this.idgrevance = idgrevance;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDateTime getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(Integer filterBy) {
		this.filterBy = filterBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	  
	  


}
