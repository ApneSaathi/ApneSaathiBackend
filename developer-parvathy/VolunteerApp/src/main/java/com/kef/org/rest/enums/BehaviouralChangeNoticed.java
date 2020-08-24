package com.kef.org.rest.enums;

public enum BehaviouralChangeNoticed {
	
	YES("YES"),NO("NO"),MAY_BE("MAY BE"),NOT_APPLICABLE("NOT APPLICABLE");
	
	private String value;
	
	public String getValue() 
    { 
        return this.value; 
    } 
  
    private BehaviouralChangeNoticed(String value) 
    { 
        this.value = value; 
    } 
	
}
