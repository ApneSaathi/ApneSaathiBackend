package com.kef.org.rest.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.VolunteerAssignment;

@Repository
public interface VolunteerAssignmentRepository  extends JpaRepository<VolunteerAssignment, Integer>{
	
	List<VolunteerAssignment> findAllByIdVolunteer(Integer idvolunteer);
	
	List<VolunteerAssignment> findAllByAdminId(Integer adminId, Integer role);
	
	@Query("select distinct va.namesrcitizen, va.phonenosrcitizen from VolunteerAssignment va where va.idvolunteer=?1")
	List<Object> countSrCitizen(Integer idvolunteer);
	
	List<VolunteerAssignment> findByVolAndSrCitizen(Integer idvolunteer,String phoneNo);

}
