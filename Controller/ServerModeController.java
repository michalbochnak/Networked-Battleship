//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// ServerModeController.java
//

//
//  ServerModeController is a class in which it checks the beginning of the game
// and tells user "waiting" and such to let them know it is trying to connect in
// order to be able to play the game.
//



package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Model.NetworkDataModel;
import Model.NetworkModel;
import View.CreateHostView;

public class ServerModeController {

	private GameController gameController;
	
	private NetworkDataModel txData;
	private NetworkDataModel rxData;
	
	private NetworkModel networkConnection;
	private String serverIP;
	private int serverPort;	
	private InetAddress hostIPAddress;

	private CreateHostView createHostView;
	
	// Default constructor:
	
	public ServerModeController(GameController gameController) {
		
		this.gameController = gameController;
		this.txData = new NetworkDataModel();
		this.rxData = null;
		this.networkConnection = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.hostIPAddress = null;
		
		this.createHostView = new CreateHostView();
		
		this.initialize();
	}
	
	// Getter methods:
	
	public CreateHostView getView() {
		return createHostView.getView();
	}
	
	// Setter methods:
	
	// Class Methods:
	
	private void initialize() {
		
		try {
			hostIPAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.err.println("Cannot find host: " + e.getMessage());
		}
		
		this.createHostView.setIPAddress(hostIPAddress.getHostAddress());
		
		this.createHostView.addCreateHostButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createHost();
			}
		});
		
		this.createHostView.addGoBackButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				networkConnection.closeConnection();
				createHostView.setDefault();
				gameController.startGame(2);
			}
		});
	
		this.createHostView.addStartGameButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.startGame(5);
			}
		});
	}
	
	private void createHost() {
		this.networkConnection = new NetworkModel();
		
		if(this.gameController.getPlayerName() == null) {
			this.networkConnection.setClientName("Server");
		} else {
			this.networkConnection.setClientName(this.gameController.getPlayerName());
		}
		
		if(this.createHostView.isRandomChecked() == true || this.createHostView.getPortNumber().length() < 1) {
			this.serverPort = 0;
		} else {
			this.serverPort = Integer.parseInt(this.createHostView.getPortNumber());
		}
		
		this.networkConnection.setServerIP(this.serverIP);
		this.networkConnection.setServerPort(this.serverPort);
		this.networkConnection.createConnection();
		
		this.gameController.setNetworkConnection(this.networkConnection);
		
		this.createHostView.setCreateServerButtonState(false);
		this.createHostView.setServerPortTextFieldState(false);
		
		if(this.serverPort == 0) {
			this.createHostView.setPortNumber(Integer.toString(this.networkConnection.getServerPort()));
		}
		
		this.txData.setServerPlayerName(this.gameController.getPlayerName());
		
		Thread establishConnectionThred = new Thread(new EstablishLientConnection());
		establishConnectionThred.start();
	}
	
	// Inner Classes:
	
	class EstablishLientConnection implements Runnable {

		@Override
		public void run() {
			int waitingCounter = 0;
			while(true) {
				if(networkConnection.isClientConnected() == true) {
					createHostView.setConnectionStatus("Connected");
					createHostView.setStartButtonState(true);
					serverIP = createHostView.getIPAddress();
					networkConnection.sendData(txData);
					rxData = networkConnection.getData();							
					createHostView.setOpponentStatusLabel(rxData.getClientPlayerName());
					gameController.setOpponentName(rxData.getClientPlayerName());
					break;
				} else {
					if((waitingCounter % 3) == 0) {
						createHostView.setOpponentStatusLabel("Waiting .");
					} else if((waitingCounter % 3) == 1) {
						createHostView.setOpponentStatusLabel("Waiting ..");
					} else {
						createHostView.setOpponentStatusLabel("Waiting ...");
					}
					waitingCounter++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						System.err.println("Thread sleep got interupted: " + e.getMessage());
					}
				}	
			}
		}
	}
}
