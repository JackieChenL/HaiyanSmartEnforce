package com.kas.clientservice.haiyansmartenforce.Entity;

public class Big {
	private String name;
	private int id;
	public String getName() {
		return name; 
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Big(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public Big(String name) {
		super();
		this.name = name;
	}
	public Big() {
		super();
	}
	@Override
	public String toString() {
		return name;
	}

	
	
}