/* ConnectToHostWindow.java
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
 * This class is GUI implementation of "Connect to Host" menu item.
 * 
 */

package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectToHostWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel connectionStatus, connectionIconLabel;
	private JTextField serverIPText, serverPortText;
	private JButton connectToHostButton, closeWindowButton;
	private ImageIcon connectedIcon, disconnectedIcon;
	boolean isConnected;
	
	public ConnectToHostWindow() {
		connectedIcon = new ImageIcon("images/network_connected.png");
		disconnectedIcon = new ImageIcon("images/network_disconnected.png");
		isConnected = false;
		initWindow();
		addElements();
	}
	
	private void initWindow() {
		setTitle("Connect to Host");
		setSize(320, 140);
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
		
		JLabel connectionStatusLabel = new JLabel("Connection Status:");
		connectionStatusLabel.setBounds(10,60,150,20);
		componentsPanel.add(connectionStatusLabel);
		
		connectionIconLabel = new JLabel(disconnectedIcon);
		connectionIconLabel.setBounds(165, 60, 20, 20);
		componentsPanel.add(connectionIconLabel);
		
		connectionStatus = new JLabel("Disonnected");
		connectionStatus.setBounds(190, 60, 150, 20);
		componentsPanel.add(connectionStatus);
		
		connectToHostButton = new JButton("Connect to Host");
		connectToHostButton.setBounds(90, 90, 115, 20);
		componentsPanel.add(connectToHostButton);
		
		closeWindowButton = new JButton("Close");
		closeWindowButton.setBounds(210, 90, 100, 20);
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
	
	public JTextField getIPAddressField() {
		return serverIPText;
	}
	
	public JTextField getPortField() {
		return serverPortText;
	}
	
	// Setter functions:
	
	public void setConnectionStatus(String status) {
		connectionStatus.setText(status);
	}
	
	public void setConnectionIncon(boolean status) {
		if(isConnected == false && status == true) {
			connectionIconLabel.setIcon(connectedIcon);
		} else if (isConnected == true && status == false) {
			connectionIconLabel.setIcon(disconnectedIcon);
		}
	}
	
	public void addCreateHostButttonEventListener(ActionListener actionListener) {
		connectToHostButton.addActionListener(actionListener);
	}
	
}
