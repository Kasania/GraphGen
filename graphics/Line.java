package com.graph.graphics;

public class Line {
	
	private int FromID;
	private int ToID;
	private int Cost;
	
	public Line(int FromID, int ToID, int Cost) {
		this.setFromID(FromID);
		this.setToID(ToID);
		this.setCost(Cost);
	}

	public int getFromID() {
		return FromID;
	}

	public void setFromID(int fromID) {
		FromID = fromID;
	}

	public int getToID() {
		return ToID;
	}

	public void setToID(int toID) {
		ToID = toID;
	}

	public int getCost() {
		return Cost;
	}

	public void setCost(int cost) {
		Cost = cost;
	}
	
	

}
