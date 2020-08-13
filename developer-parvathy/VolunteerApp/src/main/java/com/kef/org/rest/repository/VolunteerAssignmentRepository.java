package com.kef.org.rest.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.VolunteerAssignment;

@Repository
public interface VolunteerAssignmentRepository  extends JpaRepository<VolunteerAssignment, Integer>{
	
	List<VolunteerAssignment> findAllByIdVolunteer(Integer idvolunteer);
	
	List<VolunteerAssignment> findAllByAdminId(Integer adminId, Integer role);

}
