package com.kef.org.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	    
	    Optional<Volunteer> findByPhoneNo(String phoneNo);
	   
	   String fetchVolunteerNameById(Integer idvolunteer);
	   
	 
	   	//query for getVolunteerList
		@Query(value="SELECT v.idvolunteer,v.firstName,v.lastName,v.phoneNo,v.email,v.gender,v.state,v.district,v.block,v.address,"
				+ "v.Village, v.assignedtoFellow,v.assignedtoFellowContact,v.pic,v.role,v.adminId,v.status,"
				+ "avg(COALESCE(vr.rating,0)) as average_rating,count(distinct va.phonenosrcitizen) as countsr FROM "
				+ "Volunteer v LEFT OUTER JOIN VolunteerRating vr ON  v.idvolunteer=vr.idvolunteer LEFT OUTER JOIN"
				+ " VolunteerAssignment va on va.idvolunteer=vr.idvolunteer where v.status=?1 group by v.idvolunteer")
	   	List<Object> queryFunction(String status,Pageable pagable);
		
		@Query(value="SELECT v.idvolunteer,v.firstName,v.lastName,v.phoneNo,v.email,v.gender,v.state,v.district,v.block,v.address,"
				+ "v.Village, v.assignedtoFellow,v.assignedtoFellowContact,v.pic,v.role,v.adminId,v.status,"
				+ "avg(COALESCE(vr.rating,0)) as average_rating,count(distinct va.phonenosrcitizen) as countsr FROM "
				+ "Volunteer v LEFT OUTER JOIN VolunteerRating vr ON  v.idvolunteer=vr.idvolunteer LEFT OUTER JOIN"
				+ " VolunteerAssignment va on va.idvolunteer=vr.idvolunteer where v.status=?1  and v.state=?2 group by v.idvolunteer")
		List<Object> fetchByStatusAndState(String status,String state,Pageable pageable);
		
		@Query(value="SELECT v.idvolunteer,v.firstName,v.lastName,v.phoneNo,v.email,v.gender,v.state,v.district,v.block,v.address,"
				+ "v.Village, v.assignedtoFellow,v.assignedtoFellowContact,v.pic,v.role,v.adminId,v.status,"
				+ "avg(COALESCE(vr.rating,0)) as average_rating,count(distinct va.phonenosrcitizen) as countsr FROM "
				+ "Volunteer v LEFT OUTER JOIN VolunteerRating vr ON  v.idvolunteer=vr.idvolunteer LEFT OUTER JOIN"
				+ " VolunteerAssignment va on va.idvolunteer=vr.idvolunteer where v.status=?1  and v.state=?2 and v.district=?3 group by v.idvolunteer")
		List<Object> fetchByStatusAndStateAndDistrict(String status,String state,String district,Pageable pageable);
		
		
		@Query(value="SELECT v.idvolunteer,v.firstName,v.lastName,v.phoneNo,v.email,v.gender,v.state,v.district,v.block,v.address,"
				+ "v.Village, v.assignedtoFellow,v.assignedtoFellowContact,v.pic,v.role,v.adminId,v.status,"
				+ "avg(COALESCE(vr.rating,0)) as average_rating,count(distinct va.phonenosrcitizen) as countsr FROM "
				+ "Volunteer v LEFT OUTER JOIN VolunteerRating vr ON  v.idvolunteer=vr.idvolunteer LEFT OUTER JOIN"
				+ " VolunteerAssignment va on va.idvolunteer=vr.idvolunteer where v.status=?1  and v.state=?2 and v.district=?3 and v.block=?4 group by v.idvolunteer")
		List<Object> fetchByStatusAndStateAndDistrictAndBlock(String status,String state,String district,String block,Pageable pageable);
	    
	    
}

