package com.kef.org.rest.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.VolunteerRating;

@Repository
public interface VolunteerRatingRepository extends JpaRepository<VolunteerRating, Integer> {

	Float getAvgRating(Integer idvolunteer);
	
	List<VolunteerRating>getRatingByAdmin_id(Integer idvolunteer, Integer admin_id);
}
