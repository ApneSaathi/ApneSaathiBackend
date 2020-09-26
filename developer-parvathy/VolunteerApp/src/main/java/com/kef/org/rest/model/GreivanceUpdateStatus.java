package com.kef.org.rest.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "greivance_update_status")
public class GreivanceUpdateStatus {
	
	
	 @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "greivance_update_status_id",nullable =false)
	 private Integer greivance_update_id; 
	 
	 @Column(name = "tracking_id",nullable=false)
	 private Integer trackingId;
	  
	  @Column(name = "call_id")
	  private Integer callid;
	  
	 
	  @Column(name = "admin_id")
	  private Integer adminId;
	  
	  @Column(name="role")
	  private Integer role;
	 
	  
	  @Column(name = "idgreivance",nullable =false)
	  private Integer idgrevance;
	  
	  @Column(name = "type_of_greivance")
	  private String greivanceType;
	  
	  @Column(name = "status")
	  private String status;
	  
	  @Column(name = "description", columnDefinition ="varchar(255) default ''")
	  private String description;
	  
	  @Column(name = "created_date", columnDefinition ="TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , nullable =false)
	  private LocalDateTime createdDate;
	  
	  @Column(name = "priority",columnDefinition ="varchar(45) default 'Low'")
	  private String priority;
	  
	  @Column(name = "lastupdatedon", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime lastUpdatedOn;
	  
	  
	  @Column(name = "underreviewdate", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime underReviewDate;
	  
	  @Column(name = "resolveddate", columnDefinition ="TIMESTAMP DEFAULT NULL")
	  private LocalDateTime resolvedDate;
	  
	  @Column(name = "underreviewremarks", columnDefinition ="varchar(255) default ''")
	  private String underReviewRemarks;
	  
	  @Column(name = "resolved_remarks", columnDefinition ="varchar(255) default ''")
	  private String resolvedRemarks;
	  
	  @Column(name = "created_by",columnDefinition ="varchar(45) default NULL")
	  private String createdBy;
	  
	  @Column(name = "raisedby",columnDefinition ="varchar(50) default NULL")
	  private String raisedby;
	  
	  @Column(name = "reviewed_by",columnDefinition ="varchar(50) default NULL")
	  private String reviewedby;

	  @Column(name = "resolvedby",columnDefinition ="varchar(50) default NULL")
	  private String resolvedby;

	public Integer getGreivance_update_id() {
		return greivance_update_id;
	}

	public void setGreivance_update_id(Integer greivance_update_id) {
		this.greivance_update_id = greivance_update_id;
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

	public String getRaisedby() {
		return raisedby;
	}

	public void setRaisedby(String raisedby) {
		this.raisedby = raisedby;
	}

	public String getReviewedby() {
		return reviewedby;
	}

	public void setReviewedby(String reviewedby) {
		this.reviewedby = reviewedby;
	}

	public String getResolvedby() {
		return resolvedby;
	}

	public void setResolvedby(String resolvedby) {
		this.resolvedby = resolvedby;
	}
	


}
