package com.kas.clientservice.haiyansmartenforce.Entity;

public class Vp_Totality_Item {
	private String depName;
	private int caseNum;

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public int getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(int caseNum) {
		this.caseNum = caseNum;
	}

	public Vp_Totality_Item(String depName, int caseNum) {
		super();
		this.depName = depName;
		this.caseNum = caseNum;
	}

	public Vp_Totality_Item() {
		super();
	}

}
