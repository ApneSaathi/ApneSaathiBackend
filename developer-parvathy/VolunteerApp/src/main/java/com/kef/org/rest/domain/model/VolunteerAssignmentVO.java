package com.kef.org.rest.domain.model;

public class VolunteerAssignmentVO {
	
    private Integer callid;

	private Integer idvolunteer;
	
	private Integer adminId;
	
	private Integer role;
	
	private Integer id;

	private String namesrcitizen;
	
	private String phonenosrcitizen;
	
	private Integer agesrcitizen;
	
	private String gendersrcitizen;
	
	private String addresssrcitizen;
	
	private String emailsrcitizen;
	
	private String statesrcitizen;
	
	private String districtsrcitizen;
	
	private String blocknamesrcitizen;
	
	private String villagesrcitizen;
	
	
	// 1.Pending 2.Not Picked 3.Not Reachable 4.Number Busy 5.Call Later 6.Call Dropped  7.Wrong Number 8.Number doesn't exist 9.Disconnected 10.Connected
	
	private Integer callstatusCode;
	
	 //private List <MedicalandGreivance> medicalandgreivance;
	
	/*
	 * @Column(name = "call_status_subcode",columnDefinition = "integer default 1")
	 * private Integer callstatussubCode;
	 */
	
	private String talkedwith;
	
	
	private String assignedbyMember;
	
	private String remarks;
	
	private String loggeddateTime;

	public Integer getCallid() {
		return callid;
	}

	public void setCallid(Integer callid) {
		this.callid = callid;
	}

	public Integer getIdvolunteer() {
		return idvolunteer;
	}

	public void setIdvolunteer(Integer idvolunteer) {
		this.idvolunteer = idvolunteer;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
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

	public String getTalkedwith() {
		return talkedwith;
	}

	public void setTalkedwith(String talkedwith) {
		this.talkedwith = talkedwith;
	}

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

	public String getLoggeddateTime() {
		return loggeddateTime;
	}

	public void setLoggeddateTime(String loggeddateTime) {
		this.loggeddateTime = loggeddateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	 




	

	
	
	


}
