package View;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinHostView extends JPanel{
	
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
	private JButton joinHostButton;
	private JButton goBackButton;
	private JButton startGameButton;
	private ImageIcon connectedImageIcon;
	private ImageIcon disconnectedImageIcon;
	
	private boolean isConnected;
	
	
	// Default constructor:
	public JoinHostView() {
		this.connectionTypeLabel = new JLabel("Join to Host:");
		this.serverIPLabel = new JLabel("Server IP address:");
		this.serverPortLabel = new JLabel("Server Port#:");
		this.connectionMessageLabel = new JLabel("Server Status:");
		this.connectionStatusIconTypeLabel = new JLabel();
		this.connectionStatusTextTypeLabel = new JLabel("Disconnected");
		this.opponentMessageLabel = new JLabel("Opponent:");
		this.opponentStatusLabel = new JLabel("None");
		this.serverIPTextField = new JTextField();
		this.serverPortTextField = new JTextField();
		
		this.joinHostButton = new JButton("Join Host");
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
		
		public void setJoinServerButtonState(boolean state) {
			this.joinHostButton.setEnabled(state);
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
			
			this.joinHostButton.setBounds(50, 115, 150, 20);
			this.goBackButton.setBounds(210, 115, 150, 20);
			this.startGameButton.setBounds(370, 115, 150, 20);
			this.startGameButton.setEnabled(false);
			this.connectionMessageLabel.setBounds(300, 35, 90, 20);
			this.connectionStatusIconTypeLabel.setBounds(395, 35, 25, 25);
			this.connectionStatusIconTypeLabel.setIcon(disconnectedImageIcon);
			this.connectionStatusTextTypeLabel.setBounds(420, 35, 100, 20);
			this.opponentMessageLabel.setBounds(300, 60, 90, 20);
			this.opponentStatusLabel.setBounds(400, 60, 100, 20);
			
			
			
		}
		
		public void setDefault() {
			this.serverIPTextField.setText("");
			this.serverPortTextField.setText("");
			this.serverPortTextField.setEnabled(true);
			
			this.connectionStatusIconTypeLabel.setIcon(disconnectedImageIcon);
			this.connectionStatusTextTypeLabel.setText("Disconnected");
			this.opponentStatusLabel.setText("None");
			this.joinHostButton.setEnabled(true);
			this.startGameButton.setEnabled(false);
			this.isConnected = false;
			
		}
		
		public JoinHostView getView() {
			add(this.connectionTypeLabel);
			add(this.serverIPLabel);
			add(this.serverPortLabel);
			add(this.connectionStatusIconTypeLabel);
			add(this.connectionStatusTextTypeLabel);
			add(this.serverIPTextField);
			add(this.serverPortTextField);
			
			add(this.connectionMessageLabel);
			add(this.joinHostButton);
			add(this.goBackButton);
			add(this.startGameButton);
			add(this.opponentMessageLabel);
			add(this.opponentStatusLabel);
			return this;
		}

		public void addJoinHostButtonActionListener(ActionListener actionListener) {
			this.joinHostButton.addActionListener(actionListener);
		}
		
		public void addGoBackButtonActionListener(ActionListener actionListener) {
			this.goBackButton.addActionListener(actionListener);
		}
		
		public void addStartGameButtonActionListener(ActionListener actionListener) {
			this.startGameButton.addActionListener(actionListener);
		}

}
