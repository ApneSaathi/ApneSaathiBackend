package com.kef.org.rest.enums;

public enum RelatedInfoTalkedAbout {
	
	PREVENTION("PREVENTION"),ACCESS("ACCESS"),DETECION("DETECION");
	
	private String value;
	
	public String getValue() 
    { 
        return this.value; 
    } 
  
    private RelatedInfoTalkedAbout(String value) 
    { 
        this.value = value; 
    } 
	
}
