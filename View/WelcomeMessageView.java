//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// WelcomeMessageView.java
//

//
//  WelcomeMessageView is a class in which welcome message and user and opponent
// names are initialized and displayed tot he board
//


package View;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class WelcomeMessageView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel welcomeLabel;
	private JLabel usernameLabel;
	private JTextField playerNameTextField;
	private JButton nextButton;
	
	public WelcomeMessageView() {
		this.welcomeLabel = new JLabel("Welcome to Network Batleship Game!");
		this.usernameLabel = new JLabel("Please enter you name: ");
		this.playerNameTextField = new JTextField();
		this.nextButton = new JButton("Next");
	
		this.initialize();
	}
	
	private void initialize() {
		setBounds(30, 30, 540, 140);
		setLayout(null);
		
		this.welcomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.welcomeLabel.setBounds(130, 0, 300, 20);
		this.usernameLabel.setBounds(70, 50, 150, 20);
		this.playerNameTextField.setBounds(220, 50, 200, 20);
		this.nextButton.setBounds(420, 50, 100, 20);
		
		add(welcomeLabel);
		add(usernameLabel);
		add(playerNameTextField);
		add(nextButton);	
		this.revalidate();
	}

	//
	// Getter methods:
	//
	public String getPlayerName() {
		return this.playerNameTextField.getText();
	}

	//
	// Setter methods:
	//
	public void setPlayerName(String playerName) {
		this.playerNameTextField.setText(playerName);
	}

	//
	// Class methods:
	//
	public WelcomeMessageView getView() {
		return this;
	}
	
	public void addNextButtonActionListener(ActionListener actionListener) {
		this.nextButton.addActionListener(actionListener);
	}
}
