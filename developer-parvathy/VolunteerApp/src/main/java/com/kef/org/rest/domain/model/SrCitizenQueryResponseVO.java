package com.kef.org.rest.domain.model;

import java.util.List;

public class SrCitizenQueryResponseVO {

	private String message;
	private String statusCode;
	private Integer totalQueriesCount;
	private List<SeniorCitizenQueryResponse> queries;
	
	public Integer getTotalQueriesCount() {
		return totalQueriesCount;
	}
	public void setTotalQueriesCount(Integer totalQueriesCount) {
		this.totalQueriesCount = totalQueriesCount;
	}
	public List<SeniorCitizenQueryResponse> getQueries() {
		return queries;
	}
	public void setQueries(List<SeniorCitizenQueryResponse> queries) {
		this.queries = queries;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
