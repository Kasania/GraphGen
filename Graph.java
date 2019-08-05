package com.graph;

import java.util.HashMap;

import com.graph.graphics.Line;

public class Graph {

	public final int id;

	private HashMap<Integer, Line> lines;

	public Graph(int ID) {
		this.id = ID;
		lines = new HashMap<Integer, Line>();
	}

	public void connectTo(Graph target, int Cost) {
		if (!lines.containsKey(target.id)) {
			Line line = new Line(this.id, target.id, Cost);
			lines.put(target.id, line);
			target.connectTo(this, Cost);
		}
	}
	
	public boolean hasConnection(int ID) {
		if(ID == this.id) {
			return true;
		}
		
		if(lines.containsKey(ID)) {
			return true;
		}
		return false;
	}
	
	public HashMap<Integer, Line> getAllConnection() {
		return this.lines;
	}

}
