package com.kef.org.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

	private String uploadFilesDir;
	private String errorFilesDir;

	public String getUploadFilesDir() {
		return uploadFilesDir;
	}

	public void setUploadFilesDir(String uploadFilesDir) {
		this.uploadFilesDir = uploadFilesDir;
	}

	public String getErrorFilesDir() {
		return errorFilesDir;
	}

	public void setErrorFilesDir(String errorFilesDir) {
		this.errorFilesDir = errorFilesDir;
	}

}
