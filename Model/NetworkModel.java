/* Network.java
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
 * This class is responsible for network interactions between
 * two games.
 * 
 */

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
	
	// Default constructor:
	
	public NetworkModel() {
		this.serverSocket = null;
		this.clientSocket = null;
		this.serverIP = null;
		this.serverPort = 0;
		this.dataIn = null;
		this.dataOut = null;
		this.clientConnectionStatus = false;
	}
	
	// Getter methods:
	
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
			Thread connectionThread = new Thread(new ClientConnection());
			connectionThread.start();
		} catch (IOException e) {
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
			return false;
		} catch (IOException e) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(this.clientSocket != null) {
			try {
				this.clientSocket.close();
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
