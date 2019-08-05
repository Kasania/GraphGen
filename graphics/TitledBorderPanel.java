package com.graph.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class TitledBorderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String Default_FontName = "µ¸¿ò";

	private Border border;

	public TitledBorderPanel() {
		this(1, Color.BLACK, 1, "");
	}

	public TitledBorderPanel(int thickness, Color color, int maxNumOfComponent, String title) {
		this.border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color, thickness), title,
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, Font.getFont(Default_FontName),
				color.darker());
		this.setBorder(border);
		this.setLayout(new GridLayout(maxNumOfComponent, 1, 1, 1));
	}

}
