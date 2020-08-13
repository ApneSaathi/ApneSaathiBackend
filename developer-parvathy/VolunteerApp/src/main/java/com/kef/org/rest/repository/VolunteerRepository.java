package com.kef.org.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {

	
	    Integer fetchByphoneNumber(String phoneNo);
	    
	    Volunteer fetchVolunteerDetails(String phoneNo);
	    
	    Volunteer findbyidvolunteer(Integer idvolunteer);
	    
	    List<Volunteer> findAllVolunteerDetailsByAdminId(Integer adminId);

}

