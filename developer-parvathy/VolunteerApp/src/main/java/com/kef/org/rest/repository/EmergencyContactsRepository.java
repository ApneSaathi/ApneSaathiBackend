package com.kef.org.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kef.org.rest.model.EmergencyContacts;

public interface EmergencyContactsRepository extends JpaRepository<EmergencyContacts, Integer>{
	
	List<EmergencyContacts> fetchEmergencyContactsByDistrictId(Integer districtId);

}
