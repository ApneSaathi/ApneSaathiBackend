package com.kef.org.rest.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.SeniorCitizen;

@Repository
public interface SeniorCitizenRepository extends JpaRepository<SeniorCitizen, Integer>, PagingAndSortingRepository<SeniorCitizen, Integer>{
			
	List<SeniorCitizen> fetchByStatus(String status);
	//for pagination

	Page<SeniorCitizen> findAllByStatusIgnoreCase(String status,Pageable pageable);
	Page<SeniorCitizen> findByStatusAndStateIgnoreCase(String status, String state,Pageable pageable);
	Page<SeniorCitizen> findByStatusAndStateAndDistrictIgnoreCase(String status, String state,String district,Pageable pageable);
	Page<SeniorCitizen> findByStatusAndStateAndDistrictAndBlockNameIgnoreCase(String status, String state,String district,String block,Pageable pageable);

	//for update status
	@Transactional
	@Modifying
	@Query(value="update SeniorCitizen sc set sc.status=?1 where sc.srCitizenId=?2")
	void updateStatus(String status,Integer id);
	
	Optional<SeniorCitizen> findAllByPhoneNoAndFirstNameIgnoreCase(String phoneNo,String firstName);
	
}
