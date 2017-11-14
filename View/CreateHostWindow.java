/* CreateHostWindow.java
 *
 * Michal Bochnak, Netid: mbochn2
 * Alex Viznytsya, Netid: avizny2
 * Jakub Glebocki, Netid: jglebo2
 *
 * CS 342 Project #4 - Networked Battleship
 * Nov 16, 2017
 * UIC, Pat Troy
 *
 * Class description:
 * 
 * This class is GUI implementation of "Create Host" menu item.
 * 
 */

package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CreateHostWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel connectionStatus, connectionIconLabel;
	private JTextField serverIPText, serverPortText;
	private JCheckBox randomPortCheckBox;
	private JButton createHostButton, closeWindowButton;
	private ImageIcon connectedIcon, disconnectedIcon;
	boolean isConnected;

	public CreateHostWindow() {
		connectedIcon = new ImageIcon("images/network_connected.png");
		disconnectedIcon = new ImageIcon("images/network_disconnected.png");
		isConnected = false;
		initWindow();
		addElements();
	}
	
	private void initWindow() {
		setTitle("Create Host");
		setSize(320, 160);
		setLocationByPlatform(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addElements() {
		JPanel componentsPanel = new JPanel();
		componentsPanel.setLayout(null);
		
		JLabel serverIPLabel = new JLabel("Host IP Address:");
		serverIPLabel.setBounds(10,10,150,20);
		componentsPanel.add(serverIPLabel);
		
		serverIPText = new JTextField();
		serverIPText.setBounds(160, 10, 150, 20);
		componentsPanel.add(serverIPText);

		JLabel serverPortLabel = new JLabel("Host Port:");
		serverPortLabel.setBounds(10,35,150,20);
		componentsPanel.add(serverPortLabel);
		
		serverPortText = new JTextField();
		serverPortText.setBounds(160, 35, 150, 20);
		componentsPanel.add(serverPortText);

		randomPortCheckBox = new JCheckBox("Select Random Port", false);
		randomPortCheckBox.setBounds(155, 55, 200, 20);
		componentsPanel.add(randomPortCheckBox);
		
		JLabel connectionStatusLabel = new JLabel("Connection Status:");
		connectionStatusLabel.setBounds(10,80,150,20);
		componentsPanel.add(connectionStatusLabel);
		
		connectionIconLabel = new JLabel(disconnectedIcon);
		connectionIconLabel.setBounds(165, 80, 20, 20);
		componentsPanel.add(connectionIconLabel);
		
		connectionStatus = new JLabel("Disonnected");
		connectionStatus.setBounds(190, 80, 150, 20);
		componentsPanel.add(connectionStatus);
		
		createHostButton = new JButton("Create Host");
		createHostButton.setBounds(105, 110, 100, 20);
		componentsPanel.add(createHostButton);
		
		closeWindowButton = new JButton("Close");
		closeWindowButton.setBounds(210, 110, 100, 20);
		componentsPanel.add(closeWindowButton);

		closeWindowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		getContentPane().add(componentsPanel);
	}
	
	// Getter functions:
	public String getIPAddressField() {
		return serverIPText.getText();
	}
	
	public String getPortField() {
		return serverPortText.getText();
	}
	
	public boolean isRandomBoxChecked() {
		return randomPortCheckBox.isSelected();
	}
	
	// Setter functions:
	
	public void setIPAddress(String ipAddress) {
		serverIPText.setText(ipAddress);
	}
	
	public void setConnectionStatus(String status) {
		connectionStatus.setText(status);
	}
	
	public void setConnectionIncon(boolean status) {
		if(isConnected == false && status == true) {
			isConnected = true;
			connectionIconLabel.setIcon(connectedIcon);
		} else if (isConnected == true && status == false) {
			isConnected = false;
			connectionIconLabel.setIcon(disconnectedIcon);
		}
	}
	
	public void addCreateHostButttonEventListener(ActionListener actionListener) {
		createHostButton.addActionListener(actionListener);
	}
	
	
}
