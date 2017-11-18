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
// ClientModeController class is a class in which it calls instances of other
// variables to nicely have ports connected between each other. This class mostly
// focuses on JoinHost
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
	private InetAddress hostIPAddress;
	private GameController gameController;
	private String serverIP;
	private JoinHostView joinHostView;
	private int serverPort;

	//
	// Default constructor
	//
	public ClientModeController(GameController gameController) {
		
		this.gameController = gameController;
		
		this.networkConnection = null;
		this.rxData = null;
		this.txData = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.hostIPAddress = null;
		this.joinHostView = new JoinHostView();
		
		this.initialize();
	}

	//
	// Getter methods
	//
	public JoinHostView getView() {
		return this.joinHostView.getView();
	}
	
	//
	// Class methods:
	//
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
				if(networkConnection != null) {
					networkConnection.closeConnection();
				}
				joinHostView.setDefault();
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
		this.networkConnection = new NetworkModel();
		this.txData = new NetworkDataModel();
		
		if(this.gameController.getPlayerName() == null) {
			this.networkConnection.setClientName("Client");
		} else {
			this.networkConnection.setClientName(this.gameController.getPlayerName());
			this.txData.setClientPlayerName(this.gameController.getPlayerName());
		}
		
		this.serverIP = this.joinHostView.getIPAddress();
		
		if(this.joinHostView.getPortNumber().length() > 0) {
			this.serverPort = Integer.parseInt(this.joinHostView.getPortNumber());
		} 
		
		this.networkConnection.setServerIP(this.serverIP);
		this.networkConnection.setServerPort(this.serverPort);
		this.networkConnection.joinConnection();
		if(this.networkConnection.isClientConnected() == true) {
			
			this.gameController.setNetworkConnection(this.networkConnection);
			
			this.joinHostView.setJoinServerButtonState(false);
			this.joinHostView.setConnectionStatus("Connected");
			this.joinHostView.setStartButtonState(true);
			
			this.txData.setClientPlayerName(this.gameController.getPlayerName());
			this.networkConnection.sendData(this.txData);
			
			this.rxData = this.networkConnection.getData();
			
			this.joinHostView.setOpponentStatusLabel(rxData.getServerPlayerName());
			this.gameController.setOpponentName(rxData.getServerPlayerName());
		}
	}
}
