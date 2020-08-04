package com.kef.org.rest.interfaces;

import com.kef.org.rest.model.Admin;

public interface AdminInterface {
	
	public Admin fetchAdminDetails(String mobileNo);
	
	public Integer  findAdminId(String mobileNo);

}
