package com.kef.org.rest.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.SeniorCitizen;
import com.kef.org.rest.model.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer>, PagingAndSortingRepository<Volunteer, Integer> {

	
	    Integer fetchByphoneNumber(String phoneNo);
	    
	    Integer findAdminId(Integer idvolunteer);
	    
	    Volunteer fetchVolunteerDetails(String phoneNo);
	    
	    Volunteer findbyidvolunteer(Integer idvolunteer);
	    
	    List<Volunteer> findAllVolunteerDetailsByAdminId(Integer adminId);
	    
	    List<Volunteer> fetchByStatus(String status);
	   
	   String fetchVolunteerNameById(Integer idvolunteer);
	   
	   //for pagination
	   List<Volunteer> findAllByStatusIgnoreCase(String status,Pageable pageable);
		List<Volunteer> findByStatusAndStateIgnoreCase(String status, String State,Pageable pageable);
		List<Volunteer> findByStatusAndDistrictIgnoreCase(String status, String District,Pageable pageable);
		List<Volunteer> findByStatusAndBlockIgnoreCase(String status, String Block,Pageable pageable);
		List<Volunteer> findByStatusAndStateAndDistrictIgnoreCase(String status, String State,String District,Pageable pageable);
		List<Volunteer> findByStatusAndDistrictAndBlockIgnoreCase(String status,String District,String Block,Pageable pageable);
		List<Volunteer> findByStatusAndStateAndBlockIgnoreCase(String status, String State,String Block,Pageable pageable);
		List<Volunteer> findByStatusAndStateAndDistrictAndBlockIgnoreCase(String status, String State,String District,String Block,Pageable pageable);

	   	
	    
	    
}

