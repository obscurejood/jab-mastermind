/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author jab607
 */
public class GuessPanel extends JPanel {

	private Color[] palette = { Color.RED, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.BLUE };
	private JLabel[] guessLbl;
	private int size;
	private boolean showText;
	
    
	public GuessPanel() {
		showText = false;
		int[] colors = { 1, 1, 1, 1, 1 };
		size = colors.length;
		guessLbl = new JLabel[size];
		GridLayout gl = new GridLayout(1, size);
		gl.setHgap(2);
		setLayout(gl);
		for (int i=0; i<size; i++) {
			 guessLbl[i] = new JLabel("0");
                         guessLbl[i].setPreferredSize(new Dimension(20, 20));
			 setColor(i, colors[i]);
			 add(guessLbl[i]);
		}
	}
	
	public GuessPanel(int[] colors) {
		showText = false;
		size = colors.length;
		guessLbl = new JLabel[size];
		GridLayout gl = new GridLayout(1, size);
		gl.setHgap(2);
		setLayout(gl);
		for (int i=0; i<size; i++) {
			 guessLbl[i] = new JLabel("0");
                         guessLbl[i].setPreferredSize(new Dimension(30, 30));
			 setColor(i, colors[i]-1);
			 add(guessLbl[i]);
		}
    }
    
	public void enableVisibleText(boolean tf) {
		showText = tf;
		for (int i = 0; i < size; i++) {
			if (showText) {
				guessLbl[i].setForeground(Color.BLACK);
			} else {
				guessLbl[i].setForeground(guessLbl[i].getBackground());
			}
		}
	}
	
	public void setColor(int lblNo, int paletteNo) {
		guessLbl[lblNo].setOpaque(true);
		guessLbl[lblNo].setBackground(palette[paletteNo]);
		if (showText) {
			guessLbl[lblNo].setForeground(Color.BLACK);
		} else {
			guessLbl[lblNo].setForeground(palette[paletteNo]);
		}
	}
	
	public void setText(int lblNo, String s) {
		guessLbl[lblNo].setText(s);
	}
	
	public void setPalette(Color[] c) {
		palette = new Color[c.length];
		for (int i = 0; i<c.length; i++) {
			palette[i] = new Color(c[i].getRGB());
		}
	}
}
