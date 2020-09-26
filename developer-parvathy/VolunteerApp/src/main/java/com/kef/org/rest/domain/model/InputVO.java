package com.kef.org.rest.domain.model;

public class InputVO {

	private Integer id;
	
	private Integer filterBy;
	
	private String phoneNo;
	
	private String deboardReasons;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(Integer filterBy) {
		this.filterBy = filterBy;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDeboardReasons() {
		return deboardReasons;
	}

	public void setDeboardReasons(String deboardReasons) {
		this.deboardReasons = deboardReasons;
	}
	
	
}
