//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// ClientModeController.java
//

//
//  class description...
//


package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Model.NetworkDataModel;
import Model.NetworkModel;
import View.JoinHostView;

public class ClientModeController {

	private NetworkDataModel txData;
	private NetworkDataModel rxData;
	
	private NetworkModel networkConnection;
	private String serverIP;
	private int serverPort;
	private InetAddress hostIPAddress;
	
	private JoinHostView joinHostView;
	private GameController gameController;
	
	public ClientModeController(GameController gameController) {
		
		this.gameController = gameController;
		
		this.networkConnection = new NetworkModel();
		this.rxData = null;
		this.txData = new NetworkDataModel();
		this.serverIP = null;
		this.serverPort = 0;
		this.hostIPAddress = null;
		
		this.joinHostView = new JoinHostView();
		
		this.initialize();
	}
	
	
	private void initialize() {
		
		
		
		try {
			hostIPAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.err.println("Cannot find host: " + e.getMessage());
		}
		
		this.joinHostView.setIPAddress(hostIPAddress.getHostAddress());
		
		this.joinHostView.addJoinHostButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				joinHost();
			}
		});
		
		this.joinHostView.addGoBackButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				networkConnection.closeConnection();
				joinHostView.setDefault();
				joinHostView.setIPAddress(hostIPAddress.getHostAddress());
				gameController.startGame(2);
			}
		});
		
		this.joinHostView.addStartGameButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.startGame(5);
			}
		});
		
	}
	
	private void joinHost() {
		this.gameController.setNetworkConnection(this.networkConnection);
		this.txData.setClientPlayerName(this.gameController.getPlayerName());
		this.serverIP = this.joinHostView.getIPAddress();
		this.serverPort = Integer.parseInt(this.joinHostView.getPortNumber());
		this.networkConnection.setServerIP(this.serverIP);
		this.networkConnection.setServerPort(this.serverPort);
		if(this.networkConnection.joinConnection() == true) {
			this.joinHostView.setJoinServerButtonState(false);
			this.joinHostView.setConnectionStatus("Connected");
			this.joinHostView.setStartButtonState(true);
			this.networkConnection.sendData(this.txData);
			rxData = this.networkConnection.getData();
			joinHostView.setOpponentStatusLabel(rxData.getServerPlayerName());
			gameController.setOpponentName(rxData.getServerPlayerName());
		}
	}
	
	public JoinHostView getView() {
		return this.joinHostView.getView();
	}
}
