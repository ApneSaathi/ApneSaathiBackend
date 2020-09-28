package com.kef.org.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.UploadFilesInfo;

@Repository
public interface UploadFilesInfoRepository extends JpaRepository<UploadFilesInfo, Integer> {

}
