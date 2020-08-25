package com.kef.org.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.CascadeType;


@Entity
@Table(name = "admin")
@NamedQueries({
@NamedQuery(name = "Admin.fetchAdminDetails",
query = "SELECT A FROM Admin A WHERE A.mobileNo =:mobileNo "
),
@NamedQuery(name = "Admin.fetchAdminNameByAdminId",
query = "SELECT A.firstName FROM Admin A WHERE A.adminId =:adminId "
),
@NamedQuery(name = "Admin.fetchByphoneNumber",
query = "SELECT a.adminId FROM Admin a WHERE a.mobileNo =:mobileNo "
)
}) 
public class Admin {
	
	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "ADMIN_ID")
	  
	  private Integer adminId;
	
	@Column(name="MOBILENO",nullable = false, unique = true)
	  private String mobileNo;
	
	@Column(name="FIRSTNAME")
	private String firstName;

	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="STATE")
	private String State;
	
	@Column(name="DISTRICT")
	private String District;
	
	@Column(name="ROLE")
	private Integer role;
	
	@Column(name="PASSWORD")
	private char[] password;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "ADDRESS")
	private String address;
	
	 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	 @JoinColumn(name = "adminId")
	   private List <Volunteer> volunteerList;
	 
	 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	 @JoinColumn(name = "adminId")
	   private List <District> districtList;
	
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String district) {
		District = district;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public List<Volunteer> getVolunteerList() {
		return volunteerList;
	}

	public void setVolunteerList(List<Volunteer> volunteerList) {
		this.volunteerList = volunteerList;
	}

	public List<District> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<District> districtList) {
		this.districtList = districtList;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	
	
	

}
