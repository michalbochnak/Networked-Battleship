//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// Board.java
//

//
//  class description...
//

package View;


import Model.Coordinates;

import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class BoardView extends JPanel{

	private static final long serialVersionUID = 1L;
	private ImagePanel backgroundPanel;
	private BoardCell[][] buttons;
    private Color bgColor = Color.white;

    // Default constructor:
    
    public BoardView() {
    		this.backgroundPanel = new ImagePanel();
    	
    		this.buttons = new BoardCell[10][10];

        this.initialize();
    }

    // Getter methods:
    
    public BoardCell[][] getButtons() {
        return this.buttons;
    }
    
    // Setter methods:
    
    
    // Class methods:

    private void initialize() {
    		setBackground(bgColor);
    		setBounds(0, 0, 500, 501);
    		setLayout(null);
    		
    		this.backgroundPanel.setBounds(50, 50, 450, 450);
  
        this.attTopLabels();
        this.addSideLabels();    
        this.addButtons();
        add(this.backgroundPanel);
    }
    
    private void attTopLabels() {
    		JPanel bar = new JPanel();
    		bar.setBackground(this.bgColor);
    		bar.setBounds(50, 0, 450, 50);
    		bar.setLayout(new GridLayout(1, 10));
    		for (int i = 0; i < 10; ++i) {
    			JLabel label = new JLabel(Character.toString((char)('A' + i)));
    			label.setHorizontalAlignment(SwingConstants.CENTER);
    			label.setVerticalAlignment(SwingConstants.CENTER);
    			bar.add(label);
    		}
    		add(bar);
    }

    private void addSideLabels() {
		JPanel bar = new JPanel();
		bar.setBackground(this.bgColor);
		bar.setBounds(0, 50, 40, 450);
		bar.setLayout(new GridLayout(10, 1));
		for (int i = 1; i <= 10; ++i) {
			JLabel label = new JLabel(Integer.toString(i));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			bar.add(label);
		}
		add(bar);
    }
    
    private void addButtons() {
    		int borderWidth = 1;
    		Color borderColor = Color.BLACK; 
        this.backgroundPanel.setLayout(new GridLayout(10,10, 0, 0));
  
        for (int i = 0; i < 10; ++i ) {
            for (int k = 0; k < 10; ++k) {
            	 	this.buttons[i][k] = new BoardCell(i, k);
  
            		if (i == 0) {
            			if (k == 0) {
            				this.buttons[i][k].setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, borderColor));
            			} else {
            				this.buttons[i][k].setBorder(BorderFactory.createMatteBorder(borderWidth, 0, borderWidth, borderWidth, borderColor));
            			}
            		} else {
            			if (k == 0) {
            				this.buttons[i][k].setBorder(BorderFactory.createMatteBorder(0, borderWidth, borderWidth, borderWidth, borderColor));
            			} else {
            				this.buttons[i][k].setBorder(BorderFactory.createMatteBorder(0, 0, borderWidth, borderWidth, borderColor));
            			}
            		}
            	
            		this.backgroundPanel.add(this.buttons[i][k]);
            }
        }       
    }

    
    public void addCellsMouseListener(MouseListener mouseListener) {
    		for(int i = 0; i < 10; i++) {
    			for(int j = 0; j < 10; j++) {
    				this.buttons[i][j].addMouseListener(mouseListener);
    			}
    		}
    }
    
    public void removeCellsMouseListener(MouseListener mouseListener) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.buttons[i][j].removeMouseListener(mouseListener);
			}
		}
    }



    
}









