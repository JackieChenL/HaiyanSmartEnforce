package com.kas.clientservice.haiyansmartenforce.Entity;

import java.io.Serializable;

public class Area_Item implements Serializable{

	private String actName;
	private int caseNum;
	private int depID;
	private int actID;
	private int id;

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

	public int getDepID() {
		return depID;
	}

	public void setDepID(int depID) {
		this.depID = depID;
	}

	public int getActID() {
		return actID;
	}

	public void setActID(int actID) {
		this.actID = actID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Area_Item(String actName, int caseNum, int depID, int actID, int id) {
		super();
		this.actName = actName;
		this.caseNum = caseNum;
		this.depID = depID;
		this.actID = actID;
		this.id = id;
	}

	public Area_Item() {
		super();
	}

}
