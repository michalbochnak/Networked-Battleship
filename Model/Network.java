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
import java.net.ServerSocket;

public class Network {
	
	ServerSocket serverSocket;
	int socketPort;
	
	
	// Default constructor:
	
	public Network() {
	}
	
	public ServerSocket createSocket() throws IOException {
		
		try {
			serverSocket = new ServerSocket(socketPort);
			return serverSocket;
			
		} catch (IOException ioe) {
			throw ioe;
		}
		
	}
	
	
	
}
