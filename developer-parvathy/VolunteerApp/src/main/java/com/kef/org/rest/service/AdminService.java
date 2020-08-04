package com.kef.org.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kef.org.rest.interfaces.AdminInterface;
import com.kef.org.rest.model.Admin;
import com.kef.org.rest.repository.AdminRepository;

@Service("adminService")
public class AdminService implements AdminInterface{
	
	@Autowired
	private AdminRepository adminRespository;

	@Override
	public Admin fetchAdminDetails(String mobileNo) {
		
		return adminRespository.fetchAdminDetails(mobileNo);
	}

	@Override
	public Integer findAdminId(String mobileNo) {
		
		return adminRespository.fetchByphoneNumber(mobileNo);
	}

}
