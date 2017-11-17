//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// CreateHostView.java
//

//
//  class description...
//



package View;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateHostView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel connectionTypeLabel;
	private JLabel serverIPLabel;
	private JLabel serverPortLabel;
	private JLabel connectionMessageLabel;
	private JLabel connectionStatusIconTypeLabel;
	private JLabel connectionStatusTextTypeLabel;
	private JLabel opponentMessageLabel;
	private JLabel opponentStatusLabel;
	private JTextField serverIPTextField;
	private JTextField serverPortTextField;
	private JCheckBox serverRandomPortCheckBox;
	private JButton createHostButton;
	private JButton goBackButton;
	private JButton startGameButton;
	private ImageIcon connectedImageIcon;
	private ImageIcon disconnectedImageIcon;
	
	private boolean isConnected;
	
	
	// Default constructor:
	public CreateHostView() {
		this.connectionTypeLabel = new JLabel("Create New Host:");
		this.serverIPLabel = new JLabel("Server IP address:");
		this.serverPortLabel = new JLabel("Server Port#:");
		this.connectionMessageLabel = new JLabel("Server Status:");
		this.connectionStatusIconTypeLabel = new JLabel();
		this.connectionStatusTextTypeLabel = new JLabel("Disconnected");
		this.opponentMessageLabel = new JLabel("Opponent:");
		this.opponentStatusLabel = new JLabel("None");
		this.serverIPTextField = new JTextField();
		this.serverPortTextField = new JTextField();
		this.serverRandomPortCheckBox = new JCheckBox("Select Random Port");
		this.createHostButton = new JButton("Create Server");
		this.goBackButton = new JButton("Go Back ...");
		this.startGameButton = new JButton("Start Game");
		this.connectedImageIcon = new ImageIcon("images/network_connected.png");
		this.disconnectedImageIcon = new ImageIcon("images/network_disconnected.png");
		
		this.isConnected = false;
		
		this.initialize();
	}
	
	// Getter methods:
	public String getIPAddress() {
		return this.serverIPTextField.getText();
	}
	
	public String getPortNumber() {
		return this.serverPortTextField.getText();
	}
	
	public boolean isRandomChecked() {
		return this.serverRandomPortCheckBox.isSelected();
	}
	
	public String getOpponentStatusLabel() {
		return this.opponentStatusLabel.getText();
	}
	

	// Setter Methods:
	public void setIPAddress(String ipAddress) {
		this.serverIPTextField.setText(ipAddress);
	}
	
	public void setPortNumber(String portNumber) {
		this.serverPortTextField.setText(portNumber);
	}
	
	public void setRandomPortCheckBox(boolean state) {
		this.serverRandomPortCheckBox.setSelected(state);
	}

	public void setConnectionStatus(String status) {
		if(isConnected == false) {
			this.isConnected = true;
			this.connectionStatusIconTypeLabel.setIcon(connectedImageIcon);
			this.connectionStatusTextTypeLabel.setText(status);
			
		} else {
			this.isConnected = false;
			this.connectionStatusIconTypeLabel.setIcon(disconnectedImageIcon);
			this.connectionStatusTextTypeLabel.setText(status);
		}
	}
	
	public void setCreateServerButtonState(boolean state) {
		this.createHostButton.setEnabled(state);
	}
	
	public void setServerPortTextFieldState(boolean state) {
		this.serverPortTextField.setEnabled(state);
	}
	
	public void setStartButtonState(boolean state) {
		this.startGameButton.setEnabled(state);
	}
	
	public void setOpponentStatusLabel(String status) {
		this.opponentStatusLabel.setText(status);
	}
	
	// Class methods:
	private void initialize() {
		setBounds(10, 10, 580, 180);
		setLayout(null);
		
		this.connectionTypeLabel.setBounds(220, 0, 150, 20);
		this.connectionTypeLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.serverIPLabel.setBounds(0, 35, 120, 20);
		this.serverIPTextField.setBounds(125, 35, 150, 20);
		this.serverPortLabel.setBounds(0, 60, 120, 20);
		this.serverPortTextField.setBounds(125, 60, 150, 20);
		this.serverRandomPortCheckBox.setBounds(120, 85, 160, 20);
		this.createHostButton.setBounds(50, 115, 150, 20);
		this.goBackButton.setBounds(210, 115, 150, 20);
		this.startGameButton.setBounds(370, 115, 150, 20);
		this.startGameButton.setEnabled(false);
		this.connectionMessageLabel.setBounds(300, 35, 90, 20);
		this.connectionStatusIconTypeLabel.setBounds(395, 35, 25, 25);
		this.connectionStatusIconTypeLabel.setIcon(disconnectedImageIcon);
		this.connectionStatusTextTypeLabel.setBounds(420, 35, 100, 20);
		this.opponentMessageLabel.setBounds(300, 60, 90, 20);
		this.opponentStatusLabel.setBounds(400, 60, 100, 20);
		
		
		this.serverRandomPortCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(serverRandomPortCheckBox.isSelected()) {
					serverPortTextField.setEnabled(false);
				} else {
					serverPortTextField.setEnabled(true);
				}
			}
		});
	}
	
	public void setDefault() {
		this.serverIPTextField.setText("");
		this.serverPortTextField.setText("");
		this.serverPortTextField.setEnabled(true);
		this.serverRandomPortCheckBox.setSelected(false);
		this.connectionStatusIconTypeLabel.setIcon(disconnectedImageIcon);
		this.connectionStatusTextTypeLabel.setText("Disconnected");
		this.opponentStatusLabel.setText("None");
		this.createHostButton.setEnabled(true);
		this.startGameButton.setEnabled(false);
		this.isConnected = false;
	}
	
	public CreateHostView getView() {
		add(this.connectionTypeLabel);
		add(this.serverIPLabel);
		add(this.serverPortLabel);
		add(this.connectionStatusIconTypeLabel);
		add(this.connectionStatusTextTypeLabel);
		add(this.serverIPTextField);
		add(this.serverPortTextField);
		add(this.serverRandomPortCheckBox);
		add(this.connectionMessageLabel);
		add(this.createHostButton);
		add(this.goBackButton);
		add(this.startGameButton);
		add(this.opponentMessageLabel);
		add(this.opponentStatusLabel);
		return this;
	}

	public void addCreateHostButtonActionListener(ActionListener actionListener) {
		this.createHostButton.addActionListener(actionListener);
	}
	
	public void addGoBackButtonActionListener(ActionListener actionListener) {
		this.goBackButton.addActionListener(actionListener);
	}
	
	public void addStartGameButtonActionListener(ActionListener actionListener) {
		this.startGameButton.addActionListener(actionListener);
	}
	
	
	
	
}
