package com.kas.clientservice.haiyansmartenforce.Entity;

import java.io.Serializable;

public class ActionList implements Serializable{
	private int ActionID;
	private String ActCaA;
	private int CodeCaA;

	public int getActionID() {
		return ActionID;
	}

	public void setActionID(int actionID) {
		ActionID = actionID;
	}

	public String getActCaA() {
		return ActCaA;
	}

	public void setActCaA(String actCaA) {
		ActCaA = actCaA;
	}

	public int getCodeCaA() {
		return CodeCaA;
	}

	public void setCodeCaA(int codeCaA) {
		CodeCaA = codeCaA;
	}

	public ActionList(int actionID, String actCaA, int codeCaA) {
		super();
		ActionID = actionID;
		ActCaA = actCaA;
		CodeCaA = codeCaA;
	}

	public ActionList() {
		super();
	}

}
