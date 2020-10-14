/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author jab607
 */
public class ProjectGUI3 extends JFrame implements ActionListener {
    
    private Container guiContentPane;
    private JPanel mainDisplay;
    private RockerButton[] buttons;
    private JButton enterButton;
    private JButton newGameButton;
    private JButton instructionButton;
    private int maxVal;
    private Guess computerGuess;
    private int totalGuesses;
    private JPanel lastGuess;
    private JScrollPane centerPane;
    private SpringLayout mainDisplayLayout;
    
	public ProjectGUI3() {
            maxVal = 5;
            totalGuesses = 0;
            computerGuess = new Guess(5, 5);
            computerGuess.generateRandom();
            lastGuess = null;
            initFrame();
            buildGUI();
	}
	
    public ProjectGUI3(int mv) {
        maxVal = mv;
        totalGuesses = 0;
        computerGuess = new Guess(5, mv);
        computerGuess.generateRandom();
        lastGuess = null;
        initFrame();
        buildGUI();
    }
    
    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Project: Code Detective");
        setSize(600, 400);
        setLocation(100, 100);

        guiContentPane = this.getContentPane();
    }
    
    private void buildGUI() {
		
		buildNorthGUI();
        buildCenterGUI();
        buildSouthGUI();
        buildEastGUI();
        
		makePopup();
		
        setVisible(true);
        
   }

	private void buildNorthGUI() {
		JPanel tmpPanel = new JPanel();
		tmpPanel.setBorder(new EmptyBorder(5,0,0,0));
		
		JLabel jl = new JLabel("Code Detective");
		jl.setFont(new Font("Serif", Font.PLAIN, 20));
		jl.setAlignmentX(CENTER_ALIGNMENT);
		
		tmpPanel.add(jl);		
		
		guiContentPane.add(BorderLayout.NORTH, tmpPanel);
	}
	
	private void buildEastGUI() {
            JPanel eastPane = new JPanel();

            newGameButton = new JButton("New Game");
            newGameButton.addActionListener(this);
            newGameButton.setAlignmentX(CENTER_ALIGNMENT);
                        
            instructionButton = new JButton("Instructions");
            instructionButton.addActionListener(this);
            instructionButton.setAlignmentX(CENTER_ALIGNMENT);
            
            eastPane.setBorder(new EmptyBorder(60, 5, 0, 20));
            eastPane.setLayout(new BoxLayout(eastPane, BoxLayout.Y_AXIS));
            eastPane.add(newGameButton);
            eastPane.add(Box.createVerticalStrut(10));
            eastPane.add(instructionButton);
            
            guiContentPane.add(BorderLayout.EAST, eastPane);
            
	}
	
    private void buildCenterGUI() {
        
        mainDisplay = new JPanel();
        mainDisplayLayout = new SpringLayout();
		mainDisplay.setLayout(mainDisplayLayout);
                
		centerPane = new JScrollPane(mainDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        centerPane.setViewportBorder(new LineBorder(Color.black));
		
		guiContentPane.add(BorderLayout.CENTER, centerPane);
        
    }
    
    private void buildSouthGUI() {
        
        JPanel tmp = new JPanel();
		tmp.setBorder(new EmptyBorder(0,0,10,0));
        
        buttons = new RockerButton[5];
        for (int i = 0; i<5; i++) {
            buttons[i] = new RockerButton(1, maxVal);
            buttons[i].enableVisibleText(false);
            tmp.add(buttons[i]);
        }
        
        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        tmp.add(enterButton);
        guiContentPane.add(BorderLayout.SOUTH, tmp);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object tmp = ae.getSource();
        
        if (tmp==enterButton) {
            totalGuesses++;
            Guess userGuess = getGuess();
            int em = userGuess.exactMatches(computerGuess);
            int nm = userGuess.numberMatches(computerGuess);
            printGuess(userGuess.getGuess(), em, nm);
            if (userGuess.isExactMatch(computerGuess)) {
                disableSouth();
                JOptionPane.showMessageDialog(this, "Congratulations! You managed to guess the code in " + totalGuesses + " guesses.", "YOU WON!", JOptionPane.INFORMATION_MESSAGE);
            } 
        } else if (tmp==newGameButton) {
			totalGuesses = 0;
            for (int i=0; i<buttons.length; i++) {
                buttons[i].setValue(1);
            }
            enableSouth();
            computerGuess.generateRandom();
            clearDisplay();
 	} else {
			makePopup();
            //System.out.println(instructionButton.getSize().toString());
            //System.out.println(instructionButton.getPreferredSize().toString());
        }
    }
    
	public void clearDisplay() {
            mainDisplay.removeAll();
            rep();
	}
	
	public void printGuess(int[] g, int em, int nm) {

        JPanel tmpPanel = new JPanel();
		GuessPanel guessPnl = new GuessPanel(g);
		String s = " Colors: " + Integer.toString(nm) + "  Locations: " + Integer.toString(em);
		
		JLabel lbl = new JLabel(s);
		tmpPanel.add(guessPnl);
		tmpPanel.add(lbl);
                
		mainDisplay.add(tmpPanel);

		
		if (lastGuess != null) {
			mainDisplayLayout.putConstraint(SpringLayout.NORTH, lastGuess, 5, SpringLayout.SOUTH, tmpPanel);
		}
		mainDisplayLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tmpPanel, 0, SpringLayout.HORIZONTAL_CENTER, mainDisplay);
		mainDisplayLayout.putConstraint(SpringLayout.NORTH, tmpPanel, 5, SpringLayout.NORTH, mainDisplay);
		
		lastGuess = tmpPanel;
		
		rep();
                
	}
	
  
    private Guess getGuess() {
        int[] tmp = new int[5];
        for (int i=0; i<5; i++) {
            //System.out.println(buttons[i].getValue());
            tmp[i] = buttons[i].getValue();
        }
        return new Guess(tmp);
    }
	
	private void rep() {
		int width = centerPane.getViewportBorderBounds().width;
		mainDisplay.setPreferredSize(new Dimension(width, 50 * totalGuesses));
		mainDisplay.revalidate();
		mainDisplay.repaint();
 	}
        
        private void disableSouth() {
            for (int i=0; i<buttons.length; i++) {
                buttons[i].setEnabled(false);
            }
            enterButton.setEnabled(false);
        }
        
        private void enableSouth() {
            for (int i=0; i<buttons.length; i++) {
                buttons[i].setEnabled(true);
            }
            enterButton.setEnabled(true);
        }
		
		private void makePopup() {
			JDialog dialog = new JDialog(this, "Welcome!");
			SpringLayout sl = new SpringLayout();

			JLabel label = new JLabel("Welcome to Code Detective!");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setFont(new Font("Serif", Font.PLAIN, 20));
			
			JTextArea instructionArea = new JTextArea();
			String s = 
				"Thank you for playing Code Detective! The objective of the " +
				"game is to try to determine the code of 5 colors the computer " +
				"chooses at random. Use the buttons at the bottom of screen to " +
				"select your colors, and the enter button to enter your guess. " +
				"If the colors match, you won! If they don't the computer will " +
				"give you two clues. The first clue is the number of colors in " +
				"your guess that matches the colors the computer chose. The " +
				"second clue tells you how many of your colors exactly match " +
				"the locations of the colors that the computer chose. For example "+
				"if the computer chose \"red, red, blue, yellow, and green\", and " +
				"you chose \"red, blue, green, blue, and blue\", the colors clue " +
				"would be 3 because you matched red, blue, and green, and the " +
				"location clue would only be 1 because you matched the first " +
				"location the computer chose with red. Use these clues to guess " +
				"the code and win the game. You can view these instructions at " +
				"any time by clicking the Instruction button or start a new game " +
				"by clicking the New Game button. Good Luck!";
			instructionArea.setEditable(false);
			instructionArea.setLineWrap(true);
			instructionArea.setWrapStyleWord(true);
			instructionArea.setText(s);
			
			JScrollPane popScrollPane = new JScrollPane(instructionArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			popScrollPane.setViewportBorder(new LineBorder(Color.black));
			//popScrollPane.setPreferredSize(290, 220);
			
			JButton closeButton = new JButton("Close");
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
			
			JPanel contentPane = new JPanel(sl);
			contentPane.add(label);
			contentPane.add(popScrollPane);
			contentPane.add(closeButton);
			contentPane.setOpaque(true);
			
			sl.putConstraint(SpringLayout.HORIZONTAL_CENTER, label,
				0,
				SpringLayout.HORIZONTAL_CENTER, contentPane);
			sl.putConstraint(SpringLayout.NORTH, label, 
				5,
				SpringLayout.NORTH, contentPane);
				
			sl.putConstraint(SpringLayout.NORTH, popScrollPane, 
				5,
				SpringLayout.SOUTH, label);
			sl.putConstraint(SpringLayout.HORIZONTAL_CENTER, popScrollPane,
				0,
				SpringLayout.HORIZONTAL_CENTER, contentPane);
			sl.putConstraint(SpringLayout.EAST, popScrollPane,
				-5,
				SpringLayout.EAST, contentPane);
			sl.putConstraint(SpringLayout.WEST, popScrollPane,
				5,
				SpringLayout.WEST, contentPane);
			sl.putConstraint(SpringLayout.SOUTH, popScrollPane,
				-5,
				SpringLayout.NORTH, closeButton);
				
			sl.putConstraint(SpringLayout.EAST, closeButton, 
				-5,
				SpringLayout.EAST, contentPane);
			sl.putConstraint(SpringLayout.SOUTH, closeButton,
				-5,
				SpringLayout.SOUTH, contentPane);
			dialog.setContentPane(contentPane);

			//Show it.
			dialog.setSize(new Dimension(420, 300));
			dialog.setLocationRelativeTo(this);
			dialog.setVisible(true);
		}
}
