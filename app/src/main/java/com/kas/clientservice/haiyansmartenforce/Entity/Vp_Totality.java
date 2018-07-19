package com.kas.clientservice.haiyansmartenforce.Entity;

public class Vp_Totality {
	private String actName;
	private int caseNum;
	private int actID;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public int getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(int caseNum) {
		this.caseNum = caseNum;
	}
	
	

	public int getActID() {
		return actID;
	}

	public void setActID(int actID) {
		this.actID = actID;
	}

	public Vp_Totality(String actName, int caseNum, int actID) {
		super();
		this.actName = actName;
		this.caseNum = caseNum;
		this.actID = actID;
	}

	public Vp_Totality() {
		super();
	}

}