package com.kef.org.rest.enums;

public enum TalkedWith {
	
	SENIOR_CITIZEN("SENIOR CITIZEN"),FAMILY_MEMBER_OF_SR_CITIZEN("FAMILY MEMBER OF SR CITIZEN"),COMMUNITY_MEMBER("COMMUNITY MEMBER");
	
	private String value;
	
	public String getValue() 
    { 
        return this.value; 
    } 
  
    private TalkedWith(String value) 
    { 
        this.value = value; 
    } 
	
}