package com.kef.org.rest.domain.model;

import java.time.LocalDateTime;

public class ProgressUpdates {

	private String underReviewRemarks;
	private LocalDateTime underReviewDate;
	private String reviewedBy;
	private String resolvedRemarks;
	private String resolvedBy;
	private LocalDateTime resolvedDate;
	
	public String getUnderReviewRemarks() {
		return underReviewRemarks;
	}
	public void setUnderReviewRemarks(String underReviewRemarks) {
		this.underReviewRemarks = underReviewRemarks;
	}
	public LocalDateTime getUnderReviewDate() {
		return underReviewDate;
	}
	public void setUnderReviewDate(LocalDateTime underReviewDate) {
		this.underReviewDate = underReviewDate;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public String getResolvedRemarks() {
		return resolvedRemarks;
	}
	public void setResolvedRemarks(String resolvedRemarks) {
		this.resolvedRemarks = resolvedRemarks;
	}
	public String getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(String resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public LocalDateTime getResolvedDate() {
		return resolvedDate;
	}
	public void setResolvedDate(LocalDateTime resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
}
