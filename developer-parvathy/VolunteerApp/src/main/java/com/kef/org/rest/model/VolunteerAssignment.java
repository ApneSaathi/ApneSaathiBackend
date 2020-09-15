package com.kef.org.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "volunteer_assignment")
@NamedQueries({
@NamedQuery(name = "VolunteerAssignment.findAllByIdVolunteer",
query = "SELECT DISTINCT v FROM VolunteerAssignment v WHERE v.idvolunteer =:idvolunteer"
),
@NamedQuery(name = "VolunteerAssignment.findAllByAdminId",
query = "SELECT v FROM VolunteerAssignment v WHERE v.adminId =:adminId and role =:role"
),
@NamedQuery(name="VolunteerAssignment.findByVolAndSrCitizen",query="SELECT v FROM VolunteerAssignment v where v.idvolunteer=?1 and v.phonenosrcitizen=?2")
})
public class VolunteerAssignment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	@Column(name = "CALL_ID",nullable =false)
    private Integer callid;

	@Column(name = "idvolunteer")
	private Integer idvolunteer;
	
	@Column(name = "admin_id")
	private Integer adminId;
	
	@Column(name = "role",nullable =false)
	private Integer role;
	
	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}

	@Column(name = "name_srcitizen",nullable =false)
	private String namesrcitizen;
	
	@Column(name = "phoneno_srcitizen",nullable =false)
	private String phonenosrcitizen;
	
	@Column(name = "age_srcitizen",nullable =false)
	private Integer agesrcitizen;
	
	@Column(name = "gender_srcitizen",nullable =false)
	private String gendersrcitizen;
	
	@Column(name = "address_srcitizen",nullable =false)
	private String addresssrcitizen;
	
	@Column(name = "email_srcitizen",columnDefinition ="varchar(255) default ''")
	private String emailsrcitizen;
	
	@Column(name = "state_srcitizen",columnDefinition ="varchar(255) default ''")
	private String statesrcitizen;
	
	@Column(name = "district_srcitizen",columnDefinition ="varchar(255) default ''")
	private String districtsrcitizen;
	
	@Column(name = "blockname_srcitizen",columnDefinition ="varchar(255) default ''")
	private String blocknamesrcitizen;
	
	@Column(name = "village_srcitizen",columnDefinition ="varchar(255) default ''")
	private String villagesrcitizen;
	
	
	// 1.Pending 2.Not Picked 3.Not Reachable 4.Number Busy 5.Call Later 6.Call Dropped  7.Wrong Number 8.Number doesn't exist 9.Disconnected 10.Connected
	
	@Column(name = "call_status_code",columnDefinition = "integer default 2")
	private Integer callstatusCode;
	
	/*
	 * @Column(name = "call_status_subcode",columnDefinition = "integer default 1")
	 * private Integer callstatussubCode;
	 */
	
	@Column(name = "talked_with",columnDefinition ="varchar(255) default ''")
	private String talkedwith;
	
	public String getTalkedwith() {
		return talkedwith;
	}

	public void setTalkedwith(String talkedwith) {
		this.talkedwith = talkedwith;
	}

	public List<MedicalandGreivance> getMedicalandgreivance() {
		return medicalandgreivance;
	}

	public void setMedicalandgreivance(List<MedicalandGreivance> medicalandgreivance) {
		this.medicalandgreivance = medicalandgreivance;
	}

	@Column(name = "assigned_by_member",columnDefinition ="varchar(255) default ''")
	private String assignedbyMember;
	
	@Column(name = "remarks_after_call",columnDefinition ="varchar(255) default ''")
	private String remarks;
	
	@Column(name = "loggeddatetime",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime loggeddateTime;
	
	 @Column(name="STATUS",columnDefinition="varchar(255) default 'Assigned'")
	 private String status;
	
	
	 @OneToMany(cascade = CascadeType.ALL ,  fetch = FetchType.EAGER)
	 @JoinColumn(name = "CALL_ID")
	 private List <MedicalandGreivance> medicalandgreivance;
	 


	public Integer getCallid() {
		return callid;
	}

	public void setCallid(Integer callid) {
		this.callid = callid;
	}

	public String getNamesrcitizen() {
		return namesrcitizen;
	}

	public void setNamesrcitizen(String namesrcitizen) {
		this.namesrcitizen = namesrcitizen;
	}

	public String getPhonenosrcitizen() {
		return phonenosrcitizen;
	}

	public void setPhonenosrcitizen(String phonenosrcitizen) {
		this.phonenosrcitizen = phonenosrcitizen;
	}

	public Integer getAgesrcitizen() {
		return agesrcitizen;
	}

	public void setAgesrcitizen(Integer agesrcitizen) {
		this.agesrcitizen = agesrcitizen;
	}

	public String getGendersrcitizen() {
		return gendersrcitizen;
	}

	public void setGendersrcitizen(String gendersrcitizen) {
		this.gendersrcitizen = gendersrcitizen;
	}

	public String getAddresssrcitizen() {
		return addresssrcitizen;
	}

	public void setAddresssrcitizen(String addresssrcitizen) {
		this.addresssrcitizen = addresssrcitizen;
	}

	public String getEmailsrcitizen() {
		return emailsrcitizen;
	}

	public void setEmailsrcitizen(String emailsrcitizen) {
		this.emailsrcitizen = emailsrcitizen;
	}

	public String getStatesrcitizen() {
		return statesrcitizen;
	}

	public void setStatesrcitizen(String statesrcitizen) {
		this.statesrcitizen = statesrcitizen;
	}

	public String getDistrictsrcitizen() {
		return districtsrcitizen;
	}

	public void setDistrictsrcitizen(String districtsrcitizen) {
		this.districtsrcitizen = districtsrcitizen;
	}

	public String getBlocknamesrcitizen() {
		return blocknamesrcitizen;
	}

	public void setBlocknamesrcitizen(String blocknamesrcitizen) {
		this.blocknamesrcitizen = blocknamesrcitizen;
	}

	public String getVillagesrcitizen() {
		return villagesrcitizen;
	}

	public void setVillagesrcitizen(String villagesrcitizen) {
		this.villagesrcitizen = villagesrcitizen;
	}

	public Integer getCallstatusCode() {
		return callstatusCode;
	}

	public void setCallstatusCode(Integer callstatusCode) {
		this.callstatusCode = callstatusCode;
	}

	/*
	 * public Integer getCallstatussubCode() { return callstatussubCode; }
	 * 
	 * public void setCallstatussubCode(Integer callstatussubCode) {
	 * this.callstatussubCode = callstatussubCode; }
	 */

	public String getAssignedbyMember() {
		return assignedbyMember;
	}

	public void setAssignedbyMember(String assignedbyMember) {
		this.assignedbyMember = assignedbyMember;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDateTime getLoggeddateTime() {
		return loggeddateTime;
	}

	public void setLoggeddateTime(LocalDateTime loggeddateTime) {
		this.loggeddateTime = loggeddateTime;
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


	

	
	
	


}
