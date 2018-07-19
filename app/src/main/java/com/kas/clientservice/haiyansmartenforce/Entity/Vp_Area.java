package com.kas.clientservice.haiyansmartenforce.Entity;

import java.io.Serializable;
import java.util.List;

public class Vp_Area implements Serializable{
	private String depName;

	private List<Area_Item> areas;

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public List<Area_Item> getAreas() {
		return areas;
	}

	public void setAreas(List<Area_Item> areas) {
		this.areas = areas;
	}

	public Vp_Area(String depName, List<Area_Item> areas) {
		super();
		this.depName = depName;
		this.areas = areas;
	}

	public Vp_Area() {
		super();
	}

}
