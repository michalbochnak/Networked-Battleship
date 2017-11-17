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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkModel {
	
	private String clientName;
	

	private ServerSocket serverSocket;
	private String serverIP;
	private int serverPort;
	
	private Socket clientSocket;
	
	private ObjectInputStream dataIn;
	private ObjectOutputStream dataOut;
	
	private boolean clientConnectionStatus;
	
	// Default constructor:
	
	public NetworkModel() {
		this.clientName = null;
		this.serverSocket = null;
		this.clientSocket = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.dataIn = null;
		this.dataOut = null;
		this.clientConnectionStatus = false;
	}
	
	// Getter methods:
	
	public String getClientName() {
		return this.clientName;
	}

	public boolean isClientConnected() {
		return this.clientConnectionStatus;
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
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	// Class methods:
	public void createConnection() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
			Thread connectionThread = new Thread(new ClientConnection());
			connectionThread.start();
		} catch (IOException e) {
			System.err.println(this.clientName +": Cannot create new server socket: " + e.getMessage());
		}
	}
	
	public void joinConnection() {
		try {
			this.clientSocket = new Socket(this.serverIP, this.serverPort);
			System.out.println(this.clientName +": Client joined to socket.");
			this.clientConnectionStatus = true;
			this.setDataOut();
			this.setDataIn();
		} catch (UnknownHostException e) {
			System.err.println(this.clientName +": Cannot find host to open socket: " + e.getMessage());
			this.clientConnectionStatus = false;
		} catch (IOException e) {
			this.clientConnectionStatus = false;
		}
	}
	
	private void setDataIn() {
		try {
			this.dataIn = new ObjectInputStream(this.clientSocket.getInputStream());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} 
	}
	
	private void setDataOut() {
		try {
			this.dataOut = new ObjectOutputStream(this.clientSocket.getOutputStream());
		}catch (IOException e) {
			this.clientConnectionStatus = false;
			System.err.println(e.getMessage());
		} 
	}

	
	public NetworkDataModel getData() {
		NetworkDataModel data = null;
		try {
			System.out.println(this.clientName +": Receiving object...");
			data = (NetworkDataModel)dataIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println(this.clientName +": Receiving objData failed. " + e.getMessage());
		}
		System.out.println(this.clientName +": Object has been received.");
		return data;
	}
	
	public void sendData(NetworkDataModel data) {
		try {
			System.out.println(this.clientName +": Sending object...");
			this.dataOut.writeObject(data);
			this.dataOut.flush();
			this.dataOut.reset();
		} catch (IOException e) {
			System.err.println(this.clientName +": Sending objData failed. " + e.getMessage());
		} 
		System.out.println(this.clientName +": Object has been sent.");
	}
	
	public void closeConnection() {
		
		System.out.println(this.clientName +": Closing sockets:");
		System.out.println(this.clientName +": Server socket: " + this.serverSocket);
		System.out.println(this.clientName +": Client socket: " + this.clientSocket);
		System.out.println(this.clientName +": Data out stream: " + this.dataOut);
		System.out.println(this.clientName +": Data in stream: " + this.dataIn);
		
		if(this.dataOut != null) {
			try {
				System.out.println(this.clientName +": Closing data out stream.");
				this.dataOut.close();
				this.dataOut = null;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} 
		}
		
		if(this.dataIn != null) {
			try {
				System.out.println(this.clientName +": Closing data in stream.");
				this.dataIn.close();
				this.dataIn = null;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			} 
		}
		
		if(this.serverSocket != null) {
			try {
				System.out.println(this.clientName +": Closing Server socket.");
				this.serverSocket.close();
				this.serverSocket = null;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		
		if(this.clientSocket != null) {
			try {	
				System.out.println(this.clientName +": Closing Client socket.");
				this.clientConnectionStatus = false;
				this.clientSocket.close();
				this.clientSocket = null;
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	// Nested helper classes:
	class ClientConnection implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println(clientName +": Waiting for client to connect ...");
				clientSocket = serverSocket.accept(); 
				clientConnectionStatus = true;
				setDataOut();
				setDataIn();	
			} catch (IOException e) { 
				clientConnectionStatus = false;
				System.err.println(e.getMessage());
			} 		
		}
    }	
}
