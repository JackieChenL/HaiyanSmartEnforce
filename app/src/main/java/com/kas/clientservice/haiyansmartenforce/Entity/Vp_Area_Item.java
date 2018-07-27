package com.kas.clientservice.haiyansmartenforce.Entity;

public class Vp_Area_Item {
	private String empName;
	private int allNum;
	private int endNum;
	private int EmployeeID;
	
	public Vp_Area_Item(String empName, int allNum, int endNum, int employeeID) {
		super();
		this.empName = empName;
		this.allNum = allNum;
		this.endNum = endNum;
		EmployeeID = employeeID;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
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
	public int getEmployeeID() {
		return EmployeeID;
	}
	public void setEmployeeID(int employeeID) {
		EmployeeID = employeeID;
	}
	
}
