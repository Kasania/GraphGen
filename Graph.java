package com.graph;

import java.util.HashMap;

public class Graph {

	public final int id;

	private HashMap<Integer, Integer> lines;

	public Graph(int ID) {
		this.id = ID;
		lines = new HashMap<Integer, Integer>();
	}
	

	public void connectTo(int tid, int Cost) {
		lines.put(tid, Cost);
	}

	public boolean hasConnection(int ID) {
		if (ID == this.id) {
			return true;
		}

		if (lines.containsKey(ID)) {
			return true;
		}
		return false;
	}

	public HashMap<Integer, Integer> getAllConnection() {
		return this.lines;
	}

}
