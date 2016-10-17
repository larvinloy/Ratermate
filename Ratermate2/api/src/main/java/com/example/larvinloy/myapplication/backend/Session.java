package com.example.larvinloy.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
public class Session 
{
	@Id
	Long sessionId;

	ArrayList<String> categories;

	String passkey;

	String n;

	String g;

	int modLength;

	ArrayList<String> averages;

	Long voteCount;

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
	public String getPasskey() 
	{
		return passkey;
	}

	public void setPasskey(String passkey) 
	{
		this.passkey = passkey;
	}
	public ArrayList<String> getAverages() 
	{
		return averages;
	}

	public void setAverages(ArrayList<String> averages) 
	{
		this.averages = averages;
	}

	
}
