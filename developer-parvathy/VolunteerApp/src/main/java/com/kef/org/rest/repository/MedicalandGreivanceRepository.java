package com.kef.org.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.GreivanceTracking;
import com.kef.org.rest.model.MedicalandGreivance;


@Repository
public interface MedicalandGreivanceRepository extends JpaRepository<MedicalandGreivance, Integer>{
	
}
