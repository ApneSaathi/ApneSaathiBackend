package com.kef.org.rest.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "districtId")
	private List <EmergencyContacts> emergencyContactList;

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

	public List<EmergencyContacts> getEmergencyContactList() {
		return emergencyContactList;
	}

	public void setEmergencyContactList(List<EmergencyContacts> emergencyContactList) {
		this.emergencyContactList = emergencyContactList;
	}


}
