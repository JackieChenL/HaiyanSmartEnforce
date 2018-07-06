package com.kas.clientservice.haiyansmartenforce.Entity;

public class Small {
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

	public Small(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Small() {
		super();
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
