package com.graph.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.graph.Graph;
import com.graph.GraphGenerator;

public class MainFrame {

	private GraphGenerator graphGenerator;

	private static final String title = "그래프 생성기";

	private static final int frameSize_X_Default = 1600;
	private static final int frameSize_Y_Default = 900;
	private static final String STR_CreateDiagram = "그래프 생성하기";
	private static final String STR_ImportDiagram = "그래프 읽어오기";
	private static final String STR_ExportDiagram = "그래프 저장하기";

	private static Dimension screenSize;

	private JFrame frame;
	private Canvas canvas;
	private FunctionPanel funcPanel;

	private JPanel funcAdaptPanel;
	private JPanel IOfunctionPanel;
	private JButton BTN_CreateDiagram;
	private JButton BTN_ImportDiagram;
	private JButton BTN_ExportDiagram;

	private ArrayList<Graph> currentGraphs;

	public MainFrame() {

		createComponent();
		organizeComponent();

	}

	private void createComponent() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		graphGenerator = new GraphGenerator();

		funcPanel = new FunctionPanel();
		canvas = new Canvas();
		GraphShape.setScreenSize(canvas.getSize());

		canvas.setBackground(Color.WHITE);
		funcAdaptPanel = new JPanel();
		IOfunctionPanel = new JPanel();

		BTN_CreateDiagram = new JButton(STR_CreateDiagram);
		BTN_CreateDiagram.addActionListener(action -> {
			long start1 = System.currentTimeMillis();
			currentGraphs = graphGenerator.generate(funcPanel.getValues());
			long start2 = System.currentTimeMillis();
			drawGraphs(currentGraphs);
			long end1 = System.currentTimeMillis();
			System.out.println("Generate : " + (start2 - start1) + ", Draw : " + (end1 - start2) + ", Total : " + (end1 - start1));
		});

		BTN_ImportDiagram = new JButton(STR_ImportDiagram);
		BTN_ImportDiagram.addActionListener(action -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
			jfc.setSelectedFile(new File("graph"));
			
			int result = jfc.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				currentGraphs =  graphGenerator.parseGraph(file);
				drawGraphs(currentGraphs);
			}
		});

		BTN_ExportDiagram = new JButton(STR_ExportDiagram);
		BTN_ExportDiagram.addActionListener(action -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
			jfc.setSelectedFile(new File("graph"));
			
			int result = jfc.showSaveDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				graphGenerator.saveGraph(currentGraphs, file);

			}

		});

		frame = new JFrame(title);
		frame.setBounds((screenSize.width - frameSize_X_Default) / 2, (screenSize.height - frameSize_Y_Default) / 2,
				frameSize_X_Default, frameSize_Y_Default);

	}

	private void organizeComponent() {

		funcAdaptPanel.setLayout(new BorderLayout(5, 5));

		IOfunctionPanel.setLayout(new GridLayout(3, 1));
		IOfunctionPanel.add(BTN_CreateDiagram);
		IOfunctionPanel.add(BTN_ImportDiagram);
		IOfunctionPanel.add(BTN_ExportDiagram);

		funcAdaptPanel.add(BorderLayout.NORTH, funcPanel);
		funcAdaptPanel.add(BorderLayout.CENTER, IOfunctionPanel);

		frame.getContentPane().setLayout(new BorderLayout(15, 15));

		frame.getContentPane().add(BorderLayout.CENTER, canvas);
		frame.getContentPane().add(BorderLayout.EAST, funcAdaptPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public void drawGraphs(ArrayList<Graph> graphs) {

		GraphShape.setScreenSize(canvas.getSize());
		GraphShape.setNodeNum(graphs.size());
		GraphShape.calcNodeRadius();
		;

		Graphics2D g2d = (Graphics2D) canvas.getGraphics();

		g2d.clearRect(0, 0, canvas.getSize().width, canvas.getSize().height);

		graphs.forEach(graph -> {
			GraphShape.drawGraph(graph, g2d);
			GraphShape.drawLine(graph, g2d);
		});

	}

}
