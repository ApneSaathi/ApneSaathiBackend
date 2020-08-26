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
@Table(name = "emergency_contacts")
@NamedQueries({
@NamedQuery(name = "EmergencyContacts.fetchEmergencyContactsByDistrictId",
query = "SELECT E FROM EmergencyContacts E WHERE E.districtId =:districtId "
)
})
public class EmergencyContacts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMERGENCY_ID")

	private Integer emergencyId;
	
	@Column(name = "districtId")
	private Integer districtId;

	@Column(name = "HOSPITAL")
	private String hospital;

	@Column(name = "POLICE")
	private String police;

	@Column(name = "AMBULANCE")
	private String ambulance;
	
	@Column(name = "COVID_CTRL_ROOM")
	private String covidCtrlRoom;

	@Column(name = "APNI_SATHI_CONSULTANT")
	private String apnisathiContact;

	@Column(name = "CONSULTANT_NAME")
	private String consultantName;

	@Column(name = "CUSTOM_CONTACT")
	private String customeContact;

	public Integer getEmergencyId() {
		return emergencyId;
	}

	public void setEmergencyId(Integer emergencyId) {
		this.emergencyId = emergencyId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getAmbulance() {
		return ambulance;
	}

	public void setAmbulance(String ambulance) {
		this.ambulance = ambulance;
	}

	public String getCovidCtrlRoom() {
		return covidCtrlRoom;
	}

	public void setCovidCtrlRoom(String covidCtrlRoom) {
		this.covidCtrlRoom = covidCtrlRoom;
	}

	public String getApnisathiContact() {
		return apnisathiContact;
	}

	public void setApnisathiContact(String apnisathiContact) {
		this.apnisathiContact = apnisathiContact;
	}

	public String getConsultantName() {
		return consultantName;
	}

	public void setConsultantName(String consultantName) {
		this.consultantName = consultantName;
	}

	public String getCustomeContact() {
		return customeContact;
	}

	public void setCustomeContact(String customeContact) {
		this.customeContact = customeContact;
	}
}
