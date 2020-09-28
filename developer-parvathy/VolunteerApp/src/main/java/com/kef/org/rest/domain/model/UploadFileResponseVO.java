package com.kef.org.rest.domain.model;

public class UploadFileResponseVO {

	private String uploadFileName;
	private String uploadFileDownloadUrl;
	private String errorFileName;
	private String errorFileDownloadUrl;
	private Integer successCount;
	private Integer failureCount;
	private String message;
	private Integer statusCode;

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadFileDownloadUrl() {
		return uploadFileDownloadUrl;
	}

	public void setUploadFileDownloadUrl(String uploadFileDownloadUrl) {
		this.uploadFileDownloadUrl = uploadFileDownloadUrl;
	}

	public String getErrorFileName() {
		return errorFileName;
	}

	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	public String getErrorFileDownloadUrl() {
		return errorFileDownloadUrl;
	}

	public void setErrorFileDownloadUrl(String errorFileDownloadUrl) {
		this.errorFileDownloadUrl = errorFileDownloadUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

}
