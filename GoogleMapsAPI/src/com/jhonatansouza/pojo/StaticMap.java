package com.jhonatansouza.pojo;

import java.util.Collections;
import java.util.List;

public class StaticMap {

	private String image;
	private List<RoutePojo> instructions;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<RoutePojo> getInstructions() {
		return Collections.unmodifiableList(instructions);
	}
	public void setInstructions(List<RoutePojo> instructions) {
		this.instructions = instructions;
	}
	
	
}
