//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// NetworkModel.java
//

//
//  This class is the class that has getter and setter functions for all ports
// and connections that will be used for gameplay, and for them to interact
// between each other. This class sends and recivees data between the server
// in order to be able to communcaie correctly with each other.


package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkModel {
	
	private ServerSocket serverSocket;
	private String serverIP;
	private int serverPort;
	
	private Socket clientSocket;
	
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	
	private boolean clientConnectionStatus;
	private boolean serverConnectionStatus;
	
	// Default constructor:
	
	public NetworkModel() {
		this.serverSocket = null;
		this.clientSocket = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.dataIn = null;
		this.dataOut = null;
		this.clientConnectionStatus = false;
		this.serverConnectionStatus = false;
	}
	
	// Getter methods:
	
	public boolean isClientConnected() {
		return this.clientConnectionStatus;
	}
	
	public boolean isServerStarted() {
		return this.serverConnectionStatus;
	}
	
	public int getServerPort() {
		if(this.serverSocket != null) {
			return serverSocket.getLocalPort();
		} else {
			return 0;
		}
	}
	
	public ServerSocket getServerSocket() {
		return this.serverSocket; 
	}
	
	public Socket getClientSocket( ) {
		return this.clientSocket;
	}
	
	// Setter methods:
	
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	// Class methods:
	public boolean createConnection() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			this.serverConnectionStatus = true;
			Thread connectionThread = new Thread(new ClientConnection());
			connectionThread.start();
		} catch (IOException e) {
			this.serverConnectionStatus = false;
			System.err.println("Cannot create new server socket: " + e.getMessage());
			return false;
			
		}
		return true;
	}
	
	public boolean joinConnection() {
		try {
			this.clientSocket =  new Socket(this.serverIP, this.serverPort);
			
			System.out.println("Joined");
			this.clientConnectionStatus = true;
			this.setDataOut();
			this.setDataIn();

		} catch (UnknownHostException e) {
			System.err.println("Cannot find host to open socket: " + e.getMessage());
			this.clientConnectionStatus = false;
			return false;
		} catch (IOException e) {
			this.clientConnectionStatus = false;
			System.err.println("Cannot create new client socket: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	private void setDataIn() {
		try {
			this.dataIn = new ObjectInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void setDataOut() {
		try {
			this.dataOut = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	public NetworkDataModel getData() {
		NetworkDataModel data = null;
		try {
			data = (NetworkDataModel)dataIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void sendData(NetworkDataModel data) {
		try {
			this.dataOut.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	    try {
	    		this.dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		System.out.println("I am here");
		System.out.println("Client socket: " + this.clientSocket);
		System.out.println("Data out socket: " + this.dataOut);
		
		if(this.dataOut != null) {
			try {
				dataOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		if(this.dataIn != null) {
			try {
				dataIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
		if(this.serverSocket != null) {
			try {
				this.serverSocket.close();
				this.serverConnectionStatus = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(this.clientSocket != null) {
			try {
				System.out.println("Closing Connection");
				this.clientSocket.close();
				this.clientConnectionStatus = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Nested helper classes:
	class ClientConnection implements Runnable {
		@Override
		public void run() {
			try { 
				clientSocket = serverSocket.accept(); 
				clientConnectionStatus = true;
				setDataOut();
				setDataIn();	
			} catch (IOException e) { 
				e.printStackTrace(); 	
			} 		
		}
    }
	
	
	
}
