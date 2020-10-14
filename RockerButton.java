/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author jab607
 */
public class RockerButton extends JPanel implements ActionListener {
	
    private int buttonValue;
	private int lowValue;
	private int highValue;
	private JButton upButton;
	private JButton downButton;
	private JLabel valueLbl;
	private boolean showText;
	private Color[] palette = { Color.RED, Color.YELLOW, Color.MAGENTA, Color.GREEN, Color.BLUE };
    
    public RockerButton(int value) {
        buttonValue = value;
		lowValue = 1;
		highValue = 5;
        initGUI();
    }
    
	public RockerButton(int low, int high) {
		setRange(low, high);
		buttonValue = low;
		initGUI();
	}
	
    public RockerButton() {
        buttonValue = 1;
        lowValue = 1;
		highValue = 5;
        initGUI();
    }
	
	//public void setOpaque(boolean tf) {
	//	valueLbl.setOpaque(tf);
	//}
	
	public void enableVisibleText(boolean tf) {
		showText = tf;
		if (showText) {
			valueLbl.setForeground(Color.BLACK);
		} else {
			valueLbl.setForeground(valueLbl.getBackground());
		}
	}
	
	private void initGUI() {
		setLayout(new GridLayout(3,1));
		upButton = new JButton();
		upButton.addActionListener(this);
		downButton = new JButton();
		downButton.addActionListener(this);
		valueLbl = new JLabel();
		enableVisibleText(true);
		//valueLbl.setBackground(Color.black);
        //valueLbl.setForeground(Color.green);
		valueLbl.setHorizontalAlignment(SwingConstants.CENTER);
		setValue(buttonValue);
		
		add(upButton);
		add(valueLbl);
		add(downButton);
	}
    
	public void setRange(int low, int hi) {
		lowValue = low;
		highValue = hi;
	}
	
	public void setEnabled(boolean b) {
		upButton.setEnabled(b);
		downButton.setEnabled(b);
	}
	
    public void setValue(int value) {
        buttonValue = value;
        showValue();
    }
    
    public int getValue() {
        return buttonValue;
    }
	
	public void setText(String s) {
		valueLbl.setText(s);
	}
	
	public void setColor() {
		valueLbl.setOpaque(true);
		valueLbl.setBackground(palette[buttonValue-1]);
		if (showText) {
			valueLbl.setForeground(Color.BLACK);
		} else {
			valueLbl.setForeground(palette[buttonValue-1]);
		}
	}
	
	public void showValue() {
		setColor();
		setText(Integer.toString(buttonValue));
	}
	
	@Override
    public void actionPerformed(ActionEvent ae) {
		Object tmp = ae.getSource();
		if (tmp==upButton) {
			buttonValue++;
			if (buttonValue>highValue) {
				buttonValue = lowValue;
			}
			setValue(buttonValue);
		} else if (tmp==downButton) {
			buttonValue--;
			if (buttonValue<lowValue) {
				buttonValue = highValue;
			}
			setValue(buttonValue);
		}
	}
}
