//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// GameBoardView.java
//

//
// GameBoardView is a class which allows the view of the Board pertaining to the
// opponent & player. It also gets the info of names and labels and initializes everything
// as well as gets and sets the name of the player and opponent
//




package View;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameboardView extends JPanel{

	private static final long serialVersionUID = 1L;
	private ControlsView controlsView;
	private PlayerBoardView playerBoardView;
	private OpponentBoardView opponentBoardView;
	private StatusBarView statusBarView;
	private JLabel playerNameLabel;
	private JLabel opponentNameLabel;
	private String playerName;
	private String opponentName;

	

	// Default constructor
	
	public GameboardView() {
		
		this.controlsView = new ControlsView();
		this.playerBoardView = new PlayerBoardView();
		this.opponentBoardView = new OpponentBoardView();
		//this.statusBarView = new StatusBarView();
		
		this.playerNameLabel = new JLabel();
		this.opponentNameLabel = new JLabel();
		this.playerName = null;
		this.opponentName = null;
		
		this.initialize();
	}
	
	
	// Getter methods:
		
	public GameboardView getGameboardView() {
		return this;
	}
	
	public ControlsView getControlsView() {
		return this.controlsView;
	}
	
	public PlayerBoardView getPlayerBoardView() {
		return this.playerBoardView;
	}
	
	public OpponentBoardView getOpponentBoardView() {
		return this.opponentBoardView;
	}
	
	// Setter methods:
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
	// Class methods:

	private void initialize() {
		setBackground(Color.WHITE);
		setBounds(0,0,1400, 700);
		setLayout(null);
		
		this.playerNameLabel.setBounds(340, 10, 500, 20);
		this.playerNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.playerNameLabel);
		this.opponentNameLabel.setBounds(890, 10, 500, 20);
		this.opponentNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.opponentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.opponentNameLabel);
		
		add(this.controlsView);
		add(this.playerBoardView);
		opponentBoardView.setVisible(false);
		add(this.opponentBoardView);
	}
	
	public void updatePlayerNames() {
		this.playerNameLabel.setText("Player " + this.playerName + ":");
		this.opponentNameLabel.setText("Opponent " + this.opponentName + ":");
	}




           
}
