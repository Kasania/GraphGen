package com.graph.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.graph.Util;

public class FunctionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final String STR_NumOfNodeTitle = "노드 개수 관련";
	private static final String STR_NumOfNode = "노드 개수";
	private static final String STR_LineNumTitle = "간선 개수 관련";
	private static final String STR_MinLineNum = "노드당 최소 간선개수";
	private static final String STR_MaxLineNum = "노드당 최대 간선개수";
	private static final String STR_LineCostTitle = "비용 관련";
	private static final String STR_MinLineCost = "간선당 최소 비용";
	private static final String STR_MaxLineCost = "간선당 최대 비용";
	private static final String STR_Randomize = "무작위 값 설정";
	private static final String STR_RandomizeAll = "모든 값 무작위 설정";

	private static final Color COL_NumOfNode = new Color(214, 126, 214);
	private static final Color COL_LineNum = new Color(126, 149, 214);
	private static final Color COL_CostOnLine = new Color(126, 214, 126);

	private static final int INT_MinNode_Default = 2;
	private static final int INT_MaxNode_Default = 10;
	private static final int INT_MinLine_Default = 1;
	private static final int INT_MinCost_Default = 0;
	private static final int INT_MaxCost_Default = 10;

	private JPanel PNL_GeneralFunction;

	private TitledBorderPanel TBP_NumOfNode;
	private TitledBorderPanel TBP_LineNum;
	private TitledBorderPanel TBP_CostOnLine;

	private LabeledTextField LTF_NumOfNode;
	private LabeledTextField LTF_MinLineNum;
	private LabeledTextField LTF_MaxLineNum;
	private LabeledTextField LTF_MinLineCost;
	private LabeledTextField LTF_MaxLineCost;

	private JButton BTN_NumOfNodeRandomize;
	private JButton BTN_LineRandomize;
	private JButton BTN_LineCostRandomize;

	private JButton BTN_RandomizeAll;

	public FunctionPanel() {

		createComponent();
		setComponentActions();
		organizeComponent();
	}

	private void createComponent() {
		LTF_MinLineNum = new LabeledTextField(STR_MinLineNum);
		LTF_MinLineNum.textField.setText(String.valueOf(INT_MinLine_Default));
		
		TBP_NumOfNode = new TitledBorderPanel(1, COL_NumOfNode, 2, STR_NumOfNodeTitle);
		LTF_NumOfNode = new LabeledTextField(STR_NumOfNode);
		BTN_NumOfNodeRandomize = new JButton(STR_Randomize);

		PNL_GeneralFunction = new JPanel();

		TBP_LineNum = new TitledBorderPanel(1, COL_LineNum, 2, STR_LineNumTitle);
		
		LTF_MaxLineNum = new LabeledTextField(STR_MaxLineNum);
		BTN_LineRandomize = new JButton(STR_Randomize);

		TBP_CostOnLine = new TitledBorderPanel(1, COL_CostOnLine, 3, STR_LineCostTitle);
		LTF_MinLineCost = new LabeledTextField(STR_MinLineCost);
		LTF_MaxLineCost = new LabeledTextField(STR_MaxLineCost);
		BTN_LineCostRandomize = new JButton(STR_Randomize);

		BTN_RandomizeAll = new JButton(STR_RandomizeAll);

	}

	private void setComponentActions() {

		BTN_NumOfNodeRandomize.addActionListener(action -> {
			this.setRandomNodeNumber();
		});

		BTN_LineRandomize.addActionListener(action -> {
			this.setRandomLineNumber();
		});
		BTN_LineCostRandomize.addActionListener(action -> {
			this.setRandomLineCost();
		});

		BTN_RandomizeAll.addActionListener(action -> {
			this.setRandomNodeNumber();
			this.setRandomLineNumber();
			this.setRandomLineCost();
		});

	}

	private void organizeComponent() {

		this.setLayout(new BorderLayout(5, 5));

		TBP_NumOfNode.add(LTF_NumOfNode);
		TBP_NumOfNode.add(BTN_NumOfNodeRandomize);

//		TBP_LineNum.add(LTF_MinLineNum);
		TBP_LineNum.add(LTF_MaxLineNum);
		TBP_LineNum.add(BTN_LineRandomize);

		TBP_CostOnLine.add(LTF_MinLineCost);
		TBP_CostOnLine.add(LTF_MaxLineCost);
		TBP_CostOnLine.add(BTN_LineCostRandomize);

		PNL_GeneralFunction.setLayout(new GridLayout(2, 1, 5, 5));
		PNL_GeneralFunction.add(TBP_LineNum);
		PNL_GeneralFunction.add(TBP_CostOnLine);

		this.add(BorderLayout.NORTH, TBP_NumOfNode);
		this.add(BorderLayout.CENTER, PNL_GeneralFunction);
		this.add(BorderLayout.SOUTH, BTN_RandomizeAll);
	}

	private void setRandomNodeNumber() {
		int nodeNum = Util.randBetween(INT_MinNode_Default, INT_MaxNode_Default);
		LTF_NumOfNode.textField.setText(String.valueOf(nodeNum));
	}

	private void setRandomLineNumber() {
		int maxLine = 0;
		int nodeNum = 0;
		try {
			nodeNum = Integer.parseInt(LTF_NumOfNode.textField.getText());
		} catch (NumberFormatException e) {

		}

		if (nodeNum > 2) {
			maxLine = Util.randBetween(1, nodeNum - 1);
		} else {
			maxLine = 0;
		}

		LTF_MaxLineNum.textField.setText(String.valueOf(maxLine));
	}

	private void setRandomLineCost() {

		int maxCost = Util.randBetween(INT_MinCost_Default, INT_MaxCost_Default);
		int minCost = Util.randBetween(INT_MinCost_Default, maxCost);

		LTF_MinLineCost.textField.setText(String.valueOf(minCost));
		LTF_MaxLineCost.textField.setText(String.valueOf(maxCost));
	}

	public ArrayList<Integer> getValues() {

		ArrayList<Integer> valueList = new ArrayList<Integer>();

		ArrayList<LabeledTextField> labeldTextFields = new ArrayList<LabeledTextField>();

		labeldTextFields.add(LTF_NumOfNode);
		labeldTextFields.add(LTF_MinLineNum);
		labeldTextFields.add(LTF_MaxLineNum);
		labeldTextFields.add(LTF_MinLineCost);
		labeldTextFields.add(LTF_MaxLineCost);

		for (LabeledTextField ltf : labeldTextFields) {
			try {
				int value = Integer.parseInt(ltf.textField.getText());
				valueList.add(value);

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, ltf.label.getText() + "의 숫자 입력이 올바르지 않습니다.");
				ltf.textField.requestFocus();
				return null;
			}
		}
		return valueList;
	}

}
