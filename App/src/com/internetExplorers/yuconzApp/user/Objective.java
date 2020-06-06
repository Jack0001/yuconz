package com.internetExplorers.yuconzApp.user;

public class Objective {
	private int oId;
	private String username;
	private int rId;
	private String objective;
	private String date_created;
	
	
	public Objective(int oId, String username, int rId, String objective, String date_created) {
		this.oId = oId;
		this.username = username;
		this.rId = rId;
		this.objective = objective;
		this.date_created = date_created;
	}
	
	public int getoId() {
		return oId;
	}


	public String getrUsername() {
		return username;
	}
	
	public int getrId() {
		return rId;
	}


	public String getObjective() {
		return objective;
	}


	public String getDate_created() {
		return date_created;
	}

	
}
