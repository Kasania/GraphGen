package com.graph;

public class Util {

	// int ? ~ ?

	public static int randBetween(int from, int to) {
		return (int) (Math.random() * (to - from + 1) + from);
		// from ~ (to+from)
	}

}
