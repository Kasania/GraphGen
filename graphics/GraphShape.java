package com.graph.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

import com.graph.Graph;

public class GraphShape {

	private static int maxNodeNum;
	private static double anglePerNode;

	private static int screenWidth;
	private static int screenHeight;

	private static double radOfNode = 0;
	private static double radOfOuterCircle = 0;

	public static void setNodeNum(int nodeNum) {
		maxNodeNum = nodeNum;
		anglePerNode = 360.0 / nodeNum;
	}

	public static void setScreenSize(Dimension size) {
		screenWidth = size.width;
		screenHeight = size.height;
	}

	public static void calcNodeRadius() {
		if (screenWidth > screenHeight) {
			radOfOuterCircle = screenHeight / 2;
		} else {
			radOfOuterCircle = screenWidth / 2;
		}

		radOfNode = (radOfOuterCircle * Math.sin(Math.toRadians(anglePerNode / 2)))
				/ (Math.sin(Math.toRadians(anglePerNode / 2)) + 1);

	}

	public static void drawGraph(Graph graph, Graphics2D g2d) {
		int id = graph.id;

		g2d.setColor(new Color((((id % 65535) * 31 * (maxNodeNum % 256) + 241) % 256),
				(((id % 65535) * 61 * (maxNodeNum % 499) + 128) % 256),
				(((id % 65535) * 17 * (maxNodeNum % 997) + 128) % 256)));

		Point currentNode = getPosOfGraph(id);

		double nodePosX = currentNode.x;
		double nodePosY = currentNode.y;

		g2d.fillOval((int) (nodePosX - radOfNode + screenWidth / 2),
				(int) (nodePosY - radOfNode + screenHeight / 2) + 16, (int) radOfNode, (int) radOfNode);

		g2d.setFont(new Font("Consolas", Font.PLAIN, (int) (radOfNode / 1.3)));
		g2d.setColor(Color.WHITE);
		g2d.drawString(String.valueOf(id), (int) (nodePosX - radOfNode * 2 / 3 + screenWidth / 2),
				(int) (nodePosY - radOfNode / 3 + screenHeight / 2));

	}

	public static void drawLine(Graph graph, Graphics2D g2d) {
		int id = graph.id;
		HashMap<Integer, Integer> lines = graph.getAllConnection();
		g2d.setColor(new Color((((id % 65535) * 31 * (maxNodeNum % 256) + 241) % 256),
				(((id % 65535) * 61 * (maxNodeNum % 499) + 128) % 256),
				(((id % 65535) * 17 * (maxNodeNum % 997) + 128) % 256)));

		Point src = getPosOfGraph(id);

		lines.forEach((tid, cost) -> {
			Point dst = getPosOfGraph(tid);
			int srcLineX = (int) (src.x - radOfNode / 2 + screenWidth / 2);
			int srcLineY = (int) (src.y - radOfNode / 2 + screenHeight / 2) + 16;
			int dstLineX = (int) (dst.x - radOfNode / 2 + screenWidth / 2);
			int dstLineY = (int) (dst.y - radOfNode / 2 + screenHeight / 2) + 16;

			g2d.drawLine(srcLineX, srcLineY, dstLineX, dstLineY);

			int midofLineX = (int) (srcLineX * 0.8 + dstLineX * 0.2);
			int midofLineY = (int) (srcLineY * 0.8 + dstLineY * 0.2);

			g2d.setFont(new Font("Consolas", Font.PLAIN, 15));
			g2d.drawString(String.valueOf(cost), midofLineX, midofLineY + 16);
		});

	}

	private static Point getPosOfGraph(int ID) {

		double angleOfCurrentNode = Math.toRadians(anglePerNode * ID);

		double nodePosX = Math.cos(angleOfCurrentNode) * (screenWidth - radOfNode * 2) / 2;
		double nodePosY = Math.sin(angleOfCurrentNode) * (screenHeight - radOfNode * 2) / 2;

		return new Point((int) nodePosX, (int) nodePosY);
	}

}
