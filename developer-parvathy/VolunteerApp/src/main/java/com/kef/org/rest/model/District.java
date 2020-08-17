package com.kef.org.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;



@Entity
@Table(name = "district")
@NamedQueries({
@NamedQuery(name = "District.fetchDistrictDetailsByDistrictId",
query = "SELECT D FROM District D WHERE D.adminId =:adminId and D.districtId =:districtId "
),
@NamedQuery(name = "District.findDistrictId",
query = "SELECT D.districtId FROM District D WHERE D.adminId =:adminId "
)
})
public class District {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DISTRICTID")

	private Integer districtId;

	@Column(name = "STATE")
	private String state;

	@Column(name = "DISTRICT_NAME")
	private String districtName;

	@Column(name = "adminId")
	private Integer adminId;

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}


}
