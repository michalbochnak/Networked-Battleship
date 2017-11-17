//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// SelectGameModeView.java
//

//
//  class description...
//




package View;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class SelectGameModeView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ImageIcon createHostImage;
	private ImageIcon joinHostImage;
	private JLabel selectGameLabel;
	private JLabel createhostLabel;
	private JLabel joinHostLabel;
	private JButton createHostButton;
	private JButton joinHostButton;
	
	
	// Default constructor:
	public SelectGameModeView() {
		this.createHostImage = new ImageIcon("images/create_host.png");
		this.joinHostImage = new ImageIcon("images/join_host.png");
		this.selectGameLabel = new JLabel("Please Select Game:");
		this.createhostLabel = new JLabel(this.createHostImage);
		this.joinHostLabel = new JLabel(this.joinHostImage);
		this.createHostButton = new JButton("Create Host");
		this.joinHostButton = new JButton("Join Host");
		this.initialize();
	}
	
	private void initialize() {
		setBounds(30, 15, 540, 140);
		setLayout(null);
		
		this.selectGameLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.selectGameLabel.setBounds(185, 0, 300, 20);
		this.createhostLabel.setBounds(145, 40, 50, 50);
		this.joinHostLabel.setBounds(345, 40, 50, 50);
		this.createHostButton.setBounds(120, 100, 100, 20);
		this.joinHostButton.setBounds(320, 100, 100, 20);
		
	}
	
	public SelectGameModeView getView() {
		add(this.selectGameLabel);
		add(this.createhostLabel);
		add(this.joinHostLabel);
		add(this.createHostButton);
		add(this.joinHostButton);
		return this;
	}
	
	public void addCreateHostButtonActionListener(ActionListener actionListener) {
		this.createHostButton.addActionListener(actionListener);
	}
	
	public void addJoinHostButtonActionListener(ActionListener actionListener) {
		this.joinHostButton.addActionListener(actionListener);
	}
}
