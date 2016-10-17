package com.example.larvinloy.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;



@Entity
@Index
public class Vote 
{
	@Id
	Long voteId;

	Long sessionId;

	ArrayList<String> votes;

	int	modLength;
	
	
	public Long getVoteId() {
		return voteId;
	}
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) 
	{
		this.sessionId = sessionId;
	}
	public ArrayList<String> getVotes() 
	{
		return votes;
	}
	public void setVotes(ArrayList<String> votes) {
		this.votes = votes;
	}
	public int getModLength() {
		return modLength;
	}
	public void setModLength(int modLength) {
		this.modLength = modLength;
	}
	
}
