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
)
}) 
public class Volunteer  {
	


	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY) 
	  @Column(name = "IDVOLUNTEER")
	  
private Integer idvolunteer;
   
	
	@Column(name="MOBILENO",nullable = false, unique = true)
	  private String phoneNo;
	
	@Column(name="FIRSTNAME")
	private String firstName;

	@Column(name="LASTNAME")
	private String lastName;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="STATE")
	private String State;
	
	@Column(name="DISTRICT")
	private String District;
	
	@Column(name="BLOCK_NAME")
	private String Block;
	
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
	
	
 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 @JoinColumn(name = "idvolunteer")
   private List <VolunteerAssignment> volunteercallList;
	

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


	public String getBlock() {
		return Block;
	}


	public void setBlock(String block) {
		Block = block;
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
	

	
	
	
}
