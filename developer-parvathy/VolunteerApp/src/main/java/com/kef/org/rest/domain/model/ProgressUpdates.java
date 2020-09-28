package com.kef.org.rest.domain.model;

import java.time.LocalDateTime;

public class ProgressUpdates {

	private String underReviewRemarks;
	private LocalDateTime underReviewDate;
	
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
}
