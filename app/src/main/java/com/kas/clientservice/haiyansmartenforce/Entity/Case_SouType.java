package com.kas.clientservice.haiyansmartenforce.Entity;

public class Case_SouType {
	private String souTypeName;
	private int allNum;
	private int endNum;

	public String getSouTypeName() {
		return souTypeName;
	}

	public void setSouTypeName(String souTypeName) {
		this.souTypeName = souTypeName;
	}

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getEndNum() {
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public Case_SouType(String souTypeName, int allNum, int endNum) {
		super();
		this.souTypeName = souTypeName;
		this.allNum = allNum;
		this.endNum = endNum;
	}

	public Case_SouType() {
		super();
	}

}
