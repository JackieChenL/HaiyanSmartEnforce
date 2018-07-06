package com.kas.clientservice.haiyansmartenforce.Entity;

public class Department {
	 String depName;
	 int simple;
	 int generic;
	 int initiative;
	 int passivity;
	 int DepartmentID;
	public int getDepartmentID() {
		return DepartmentID;
	}

	public void setDepartmentID(int departmentID) {
		DepartmentID = departmentID;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public int getSimple() {
		return simple;
	}

	public void setSimple(int simple) {
		this.simple = simple;
	}

	public int getGeneric() {
		return generic;
	}

	public void setGeneric(int generic) {
		this.generic = generic;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getPassivity() {
		return passivity;
	}

	public void setPassivity(int passivity) {
		this.passivity = passivity;
	}
	public Department(String depName, int simple, int generic, int initiative,
			int passivity, int departmentID) {
		super();
		this.depName = depName;
		this.simple = simple;
		this.generic = generic;
		this.initiative = initiative;
		this.passivity = passivity;
		DepartmentID = departmentID;
	}

	public Department() {
		super();
	}

}
