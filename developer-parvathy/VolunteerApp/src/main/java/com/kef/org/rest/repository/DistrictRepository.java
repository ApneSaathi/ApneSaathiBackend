package com.kef.org.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kef.org.rest.model.District;

public interface DistrictRepository extends JpaRepository<District, Integer>{
	
	District fetchDistrictDetailsByDistrictId(Integer districtId, Integer adminId);
	
	Integer findDistrictId(Integer adminId);

}
