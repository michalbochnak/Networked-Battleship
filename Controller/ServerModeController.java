package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import Model.NetworkDataModel;
import Model.NetworkModel;
import View.CreateHostView;

public class ServerModeController {

	private GameController gameController;
	
	private NetworkDataModel txData;
	private NetworkDataModel rxData;
	
	private NetworkModel networkConnection;
	private ServerSocket serverSocket;
	private String serverIP;
	private int serverPort;
	private Socket clientSocket;
	private boolean clientConnectionStatus;
	private InetAddress hostIPAddress;
	private Thread waitConnectionThred;
	
	private CreateHostView createHostView;
	
	// Default constructor:
	
	public ServerModeController(GameController gameController) {
		
		this.gameController = gameController;
		
		this.txData = new NetworkDataModel();
		this.rxData = null;
		
		this.networkConnection = new NetworkModel();
		this.serverSocket = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.clientSocket = null;
		this.clientConnectionStatus = false;
		this.hostIPAddress = null;
		this.waitConnectionThred = null;
		
		this.createHostView = new CreateHostView();
		
		this.initialize();
	}
	
	// Getter methods:
	
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
				createHostView.setIPAddress(hostIPAddress.getHostAddress());
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
		this.txData.setServerPlayerName(this.gameController.getPlayerName());
		serverIP = createHostView.getIPAddress();
		if(this.createHostView.isRandomChecked() == true || this.createHostView.getPortNumber().length() < 1) {
			this.serverPort = 0;
		} else {
			this.serverPort = Integer.parseInt(this.createHostView.getPortNumber());
		}
		
		this.networkConnection.setServerIP(this.serverIP);
		this.networkConnection.setServerPort(this.serverPort);
		if(this.networkConnection.createConnection() == true) {
			this.createHostView.setCreateServerButtonState(false);
			if(this.serverPort == 0) {
				this.createHostView.setPortNumber(Integer.toString(this.networkConnection.getServerPort()));
			}
			this.createHostView.setServerPortTextFieldState(false);
			
			if(waitConnectionThred == null) {
				waitConnectionThred = new Thread(new Runnable() {
				@Override
				public void run() {
					int waitCounter = 0;
					while(true && !Thread.currentThread().isInterrupted()) {
						if(networkConnection.isClientConnected() == true) {
							clientConnectionStatus = true;
							createHostView.setConnectionStatus("Connected");
							createHostView.setStartButtonState(true);
							networkConnection.sendData(txData);
							rxData = networkConnection.getData();							
							createHostView.setOpponentStatusLabel(rxData.getClientPlayerName());
							gameController.setOpponentName(rxData.getClientPlayerName());
							break;
						} else {
							if((waitCounter % 3) == 0) {
								createHostView.setOpponentStatusLabel("Waiting .");
							} else if((waitCounter % 3) == 1) {
								createHostView.setOpponentStatusLabel("Waiting ..");
							} else {
								createHostView.setOpponentStatusLabel("Waiting ...");
							}
							waitCounter++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								System.err.println("Thread sleep got interupted: " + e.getMessage());
							}
						}	
					}
				}
				});
				waitConnectionThred.start();
			}
		}
	}
	
	public CreateHostView getView() {
		return createHostView.getView();
	}
}
