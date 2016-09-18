package com.ratermate.rating.entities;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Session 
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long sessionId;
	@Persistent
	ArrayList<String> categories;
	@Persistent
	String n;
	@Persistent
	String g;
	@Persistent
	int modLength;

	public Long getSessionId() 
	{
		return sessionId;
	}
	
	public void setSessionId(Long sessionId) 
	{
		this.sessionId = sessionId;
	}
	
	public ArrayList<String> getCategories() 
	{
		return categories;
	}
	
	public void setCategories(ArrayList<String> categories) 
	{
		this.categories = categories;
	}
	
	public String getN() 
	{
		return n;
	}
	
	public void setN(String n) 
	{
		this.n = n;
	}
	
	public String getG() 
	{
		return g;
	}
	
	public void setG(String g) 
	{
		this.g = g;
	}
	
	public int getModLength() 
	{
		return modLength;
	}
	
	public void setModLength(int modLength) 
	{
		this.modLength = modLength;
	}
	
	
	
}
