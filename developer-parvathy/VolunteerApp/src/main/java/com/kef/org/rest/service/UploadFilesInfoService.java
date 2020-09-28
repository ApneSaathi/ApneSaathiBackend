package com.kef.org.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kef.org.rest.model.UploadFilesInfo;
import com.kef.org.rest.repository.UploadFilesInfoRepository;

@Service
public class UploadFilesInfoService {

	@Autowired
	UploadFilesInfoRepository repository;
	
	public void saveInfo(String uploadFile, String errorFile, Integer uploadedId, Integer successCount, Integer failureCount) {
		UploadFilesInfo info = new UploadFilesInfo();
		info.setUploadedFileName(uploadFile);
		info.setErrorFileName(errorFile);
		info.setUploadedById(uploadedId);
		info.setSuccessCount(successCount);
		info.setFailureCount(failureCount);
		repository.save(info);
	}
}
