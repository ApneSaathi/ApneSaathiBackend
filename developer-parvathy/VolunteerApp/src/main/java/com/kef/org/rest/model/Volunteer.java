package com.kef.org.rest.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;


@Entity
@Table(name = "volunteer")
@NamedQueries({
@NamedQuery(name = "Volunteer.fetchByphoneNumber",
query = "SELECT v.idvolunteer FROM Volunteer v WHERE v.phoneNo =?1 "
),
@NamedQuery(name = "Volunteer.fetchVolunteerDetails",
query = "SELECT v FROM Volunteer v WHERE v.phoneNo =?1 "
),
@NamedQuery(name = "Volunteer.findbyidvolunteer",
query = "SELECT v FROM Volunteer v WHERE v.idvolunteer =:idvolunteer "
),
@NamedQuery(name = "Volunteer.findAllVolunteerDetailsByAdminId",
query = "SELECT v FROM Volunteer v WHERE v.adminId =:adminId "
),
@NamedQuery(name = "Volunteer.findAdminId",
query = "SELECT v.adminId FROM Volunteer v WHERE v.idvolunteer =:idvolunteer "
),
@NamedQuery(name = "Volunteer.fetchVolunteerNameById",
query = "SELECT v.firstName FROM Volunteer v WHERE v.idvolunteer =:idvolunteer "
),
@NamedQuery(name="Volunteer.fetchByStatus",query="SELECT v FROM Volunteer v where v.status=?1")
}) 
public class Volunteer  {
	

	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "IDVOLUNTEER")
	  
private Integer idvolunteer;
   
	
	@Column(name="MOBILENO",nullable = false, unique = true)
	  private String phoneNo;
	
	@Column(name = "adminId")
	private Integer adminId;
	
	@Column(name="FIRSTNAME")
	private String firstName;

	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="DISTRICT")
	private String district;
	
	@Column(name="BLOCK_NAME")
	private String block;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="VILLAGE")
	private String Village;



	@Column(name="ASSIGNED_TO_FELLOW")
	private String assignedtoFellow;
	
	@Column(name="ASSIGNED_TO_FELLOW_CONTACT")
	private String assignedtoFellowContact;
	
	@Lob
	@Column(name="pic")
	private byte[] pic;
	
	//1=Volunteer 2=Staff Member 3=District Admin 4=Master Admin
	
	@Column(name="role")
	private Integer role;
	
	 @Column(name="STATUS")
	 private String status;
 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 @JoinColumn(name = "idvolunteer")
   private List <VolunteerAssignment> volunteercallList;
 
 
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "idvolunteer")
	private List<VolunteerRating> volunteerRatingList;
	 
	

	public Integer getIdvolunteer() {
	return idvolunteer;
}
public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
public void setIdvolunteer(Integer idvolunteer) {
	this.idvolunteer = idvolunteer;
}
public List<VolunteerAssignment> getVolunteercallList() {
	return volunteercallList;
}
public void setVolunteercallList(List<VolunteerAssignment> volunteercallList) {
	this.volunteercallList = volunteercallList;
}
	public String getphoneNo() {
		return phoneNo;
	}
	public void setphoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	public String getBlock() {
		return block;
	}


	public void setBlock(String block) {
		this.block = block;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getVillage() {
		return Village;
	}
  
	public void setVillage(String village) {
		Village = village;
	}
    
	public String getAssignedtoFellow() {
		return assignedtoFellow;
	}
	public void setAssignedtoFellow(String assignedtoFellow) {
		this.assignedtoFellow = assignedtoFellow;
	}
	public String getAssignedtoFellowContact() {
		return assignedtoFellowContact;
	}
	public void setAssignedtoFellowContact(String assignedtoFellowContact) {
		this.assignedtoFellowContact = assignedtoFellowContact;
		
		
		
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	

	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<VolunteerRating> getVolunteerRatingList() {
		return volunteerRatingList;
	}
	public void setVolunteerRatingList(List<VolunteerRating> volunteerRatingList) {
		this.volunteerRatingList = volunteerRatingList;
	}
}
