package com.test.tests;

import java.util.List;
import java.util.Map;

public class Country {
	
	String messages;
	List<Map<String,String>> result ;
	
	
	public void setMessage(String n)
	{
		messages = n;		
	}
	
	public String getMessage()
	{
		return messages;
	}
	
	
	public void setResult ( List<Map<String,String>> n ) {
		result = n;
		}
	
	public List<Map<String,String>> getResult() {
		   return result;
		} 

}
