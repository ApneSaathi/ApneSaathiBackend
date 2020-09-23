package com.kef.org.rest.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kef.org.rest.model.GreivanceTracking;

@Repository
public interface GreivanceTrackingRepository extends JpaRepository<GreivanceTracking, Integer>{
	
	List<GreivanceTracking> findAllbyidvolunteer(Integer idvolunteer,String createdBy);
	
	List<GreivanceTracking> findAllbyadminId(Integer adminId, String createdBy);
	
	GreivanceTracking findbytrackingid(Integer trackingId);
	
	List<GreivanceTracking> findAllbyDistrictName(String districtName);
	
	List<GreivanceTracking> findByCallid(Integer callid);
	

}
