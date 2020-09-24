package com.kef.org.rest.domain.model;

public class SeniorCitizenQueryResponse {

	private String name;
	private Integer issueId;
	private String issuesRaised;
	private String createdOn;
	private String lastUpdatedOn;
	private String state;
	private String district;
	private String block;
	private String priority;
	private String resolvedOn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIssueId() {
		return issueId;
	}
	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}
	public String getIssuesRaised() {
		return issuesRaised;
	}
	public void setIssuesRaised(String issuesRaised) {
		this.issuesRaised = issuesRaised;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getResolvedOn() {
		return resolvedOn;
	}
	public void setResolvedOn(String resolvedOn) {
		this.resolvedOn = resolvedOn;
	}
	
}
