package com.kef.org.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kef.org.rest.model.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	Admin fetchAdminDetails(String mobileNo);
	
	Integer fetchByphoneNumber(String mobileNo);
}