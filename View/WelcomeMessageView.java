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
	private JButton submitNameButton;
	
	public WelcomeMessageView() {
		this.welcomeLabel = new JLabel("Welcome to Network Batleship Game!");
		this.usernameLabel = new JLabel("Please enter you name: ");
		this.playerNameTextField = new JTextField();
		this.submitNameButton = new JButton("Next");
	
		this.initialize();
	}
	
	private void initialize() {
		setBounds(30, 30, 540, 140);
		setLayout(null);
		
		this.welcomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.welcomeLabel.setBounds(130, 0, 300, 20);
		this.usernameLabel.setBounds(70, 50, 150, 20);
		this.playerNameTextField.setBounds(220, 50, 200, 20);
		this.submitNameButton.setBounds(420, 50, 100, 20);
		
	}
	
	public String getPlayerName() {
		return this.playerNameTextField.getText();
	}
	
	
	public void setPlayerName(String playerName) {
		this.playerNameTextField.setText(playerName);
	}
	
	public WelcomeMessageView getView() {
		add(welcomeLabel);
		add(usernameLabel);
		add(playerNameTextField);
		add(submitNameButton);
		this.revalidate();
		return this;
	}
	
	public void addSubmitButtonActionListener(ActionListener actionListener) {
		this.submitNameButton.addActionListener(actionListener);
	}
}
