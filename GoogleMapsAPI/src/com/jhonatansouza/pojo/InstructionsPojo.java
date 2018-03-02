package com.jhonatansouza.pojo;

import java.util.List;

public class InstructionsPojo {

	private List<RoutePojo> instructions;
	private String polyLine;
	
	public List<RoutePojo> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<RoutePojo> instructions) {
		this.instructions = instructions;
	}
	public String getPolyLine() {
		return polyLine;
	}
	public void setPolyLine(String polyLine) {
		this.polyLine = polyLine;
	}
	
	
}
