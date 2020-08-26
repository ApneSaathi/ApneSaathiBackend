package com.kef.org.rest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.SeniorCitizen;

@Repository
public interface SeniorCitizenRepository extends JpaRepository<SeniorCitizen, Integer>{
			
	List<SeniorCitizen> fetchByStatus(String status);
	//for pagination
	List<SeniorCitizen> findAllByStatus(String status,Pageable pageable);

	
}
