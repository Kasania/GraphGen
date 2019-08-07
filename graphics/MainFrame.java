package com.graph.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private static final String STR_DrawOptionTitle = "그래프 렌더링 관련";
	private static final String STR_DrawProgressTitle = "그리기 진행상황";
	private static final String STR_RealTimeDarwTitle = "실시간 렌더링 사용";

	private static Dimension screenSize;

	private AtomicInteger currentNodeNum;
	private AtomicBoolean isDrawing;

	private JFrame frame;
	private Canvas canvas;
	private FunctionPanel FPN_GraphValues;

	private JPanel JPN_FunctionRootPanel;
	private JPanel JPN_OptionsPanel;
	private JPanel JPN_IOFunctionPanel;
	private JButton BTN_CreateDiagram;
	private JButton BTN_ImportDiagram;
	private JButton BTN_ExportDiagram;
	private TitledBorderPanel TBP_DrawOptions;
	private LabeledTextField LTF_DrawPrograss;
	private JCheckBox CKB_DrawRealtime;

	private ArrayList<Graph> currentGraphs;

	public MainFrame() {

		createComponent();
		organizeComponent();

	}

	private void createComponent() {

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		graphGenerator = new GraphGenerator();

		FPN_GraphValues = new FunctionPanel();
		canvas = new Canvas();

		isDrawing = new AtomicBoolean(false);

		GraphShape.setScreenSize(canvas.getSize());

		canvas.setBackground(Color.WHITE);
		JPN_FunctionRootPanel = new JPanel();
		JPN_IOFunctionPanel = new JPanel();

		JPN_OptionsPanel = new JPanel();

		BTN_CreateDiagram = new JButton(STR_CreateDiagram);
		BTN_CreateDiagram.addActionListener(action -> {
			currentGraphs = graphGenerator.generate(FPN_GraphValues.getValues());
			drawGraphs(currentGraphs);
		});

		BTN_ImportDiagram = new JButton(STR_ImportDiagram);
		BTN_ImportDiagram.addActionListener(action -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
			jfc.setSelectedFile(new File("graph"));

			int result = jfc.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				currentGraphs = graphGenerator.parseGraph(file);
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

		TBP_DrawOptions = new TitledBorderPanel(1, Color.YELLOW.darker(), 2, STR_DrawOptionTitle);

		LTF_DrawPrograss = new LabeledTextField(STR_DrawProgressTitle);
		LTF_DrawPrograss.textField.setEditable(false);

		CKB_DrawRealtime = new JCheckBox(STR_RealTimeDarwTitle);
		CKB_DrawRealtime.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		frame = new JFrame(title);
		frame.setBounds((screenSize.width - frameSize_X_Default) / 2, (screenSize.height - frameSize_Y_Default) / 2,
				frameSize_X_Default, frameSize_Y_Default);
		frame.getContentPane().setBackground(Color.WHITE);

	}

	private void organizeComponent() {

		JPN_OptionsPanel.setLayout(new BorderLayout(5, 5));

		JPN_FunctionRootPanel.setLayout(new BorderLayout(5, 5));

		JPN_IOFunctionPanel.setLayout(new GridLayout(3, 1));

		JPN_IOFunctionPanel.add(BTN_CreateDiagram);
		JPN_IOFunctionPanel.add(BTN_ImportDiagram);
		JPN_IOFunctionPanel.add(BTN_ExportDiagram);

		TBP_DrawOptions.add(CKB_DrawRealtime);
		TBP_DrawOptions.add(LTF_DrawPrograss);

		JPN_OptionsPanel.add(BorderLayout.NORTH, TBP_DrawOptions);
		JPN_OptionsPanel.add(BorderLayout.CENTER, FPN_GraphValues);

		JPN_FunctionRootPanel.add(BorderLayout.NORTH, JPN_OptionsPanel);
		JPN_FunctionRootPanel.add(BorderLayout.CENTER, JPN_IOFunctionPanel);

		frame.getContentPane().setLayout(new BorderLayout(15, 15));

		frame.getContentPane().add(BorderLayout.CENTER, canvas);
		frame.getContentPane().add(BorderLayout.EAST, JPN_FunctionRootPanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas.createBufferStrategy(2);
	}

	public void drawGraphs(ArrayList<Graph> graphs) {

		GraphShape.setScreenSize(canvas.getSize());
		GraphShape.setNodeNum(graphs.size());
		GraphShape.calcNodeRadius();

		int totalNodeNum = graphs.size();

		currentNodeNum = new AtomicInteger(0);

		BufferStrategy bs = canvas.getBufferStrategy();
		Graphics2D g2d;

		if (CKB_DrawRealtime.isSelected()) {
			g2d = (Graphics2D) canvas.getGraphics();
		} else {
			g2d = (Graphics2D) bs.getDrawGraphics();
		}

		g2d.clearRect(0, 0, canvas.getSize().width, canvas.getSize().height);

		Thread progressThread = new Thread(() -> {
			while (isDrawing.get()) {
				LTF_DrawPrograss.textField.setText(currentNodeNum.get() + " / " + totalNodeNum);
			}
		});

		Thread drawThread = new Thread(() -> {
			isDrawing.set(true);
			graphs.forEach(graph -> {
				GraphShape.drawGraph(graph, g2d);
				GraphShape.drawLine(graph, g2d);
				currentNodeNum.getAndIncrement();
			});
			
			if (!CKB_DrawRealtime.isSelected()) {
				g2d.dispose();
				bs.show();
				
			}
			isDrawing.lazySet(false);
		});

		drawThread.start();
		progressThread.start();

	}

}
