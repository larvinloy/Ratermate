package com.ratermate.rating.entities;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Vote 
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	Long voteId;
	@Persistent
	Long sessionId;
	@Persistent
	HashMap<String,String> votes;
	@Persistent
	int modLength;
	
	
	public Long getVoteId() {
		return voteId;
	}
	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public HashMap<String, String> getVotes() {
		return votes;
	}
	public void setVotes(HashMap<String, String> votes) {
		this.votes = votes;
	}
	public int getModLength() {
		return modLength;
	}
	public void setModLength(int modLength) {
		this.modLength = modLength;
	}
	
}
