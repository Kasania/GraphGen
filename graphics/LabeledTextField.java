package com.graph.graphics;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledTextField extends JPanel {

	private static final long serialVersionUID = 1L;
	
	String labelText;
	JLabel label;
	JTextField textField;

	public LabeledTextField() {
		this("");
	}

	public LabeledTextField(String text) {
		labelText = text;
		label = new JLabel(labelText);
		textField = new JTextField();
		this.setLayout(new GridLayout(2,1,2,1));
		this.add(label);
		this.add(textField);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY.brighter(), 1));
	}
	

}
