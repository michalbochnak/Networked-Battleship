//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// NetworkDataController.java
//

//
//  This class gets all needed information for player and opponent and then sets
// a hit when there is a hit, as well as settingt he coordinates of where the hit
// is
//



package Model;

import java.io.Serializable;

public class NetworkDataModel implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String serverPlayerName;
	private String clientPlayerName;
	
	private int row;
	private int col;
	private boolean hitStatus;			// flag to see if hit was succersfull
	private boolean hitAttempt;		// opponent was tryin g to hit
	private boolean respond;			// response about attempted hit




	public NetworkDataModel() {
		this.serverPlayerName = null;
		this.clientPlayerName = null;
		this.hitStatus = false;
	}
	
	// Getter methods:
	
	public String getServerPlayerName() {
		return this.serverPlayerName;
	}
	
	public String getClientPlayerName() {
		return this.clientPlayerName;
	}
	
	public Coordinates getCoordinates() {
		return new Coordinates(this.row, this.col);
	}

	public void setCoordinates(Coordinates c) {
		this.row = c.getRow();
		this.col = c.getCol();
	}
	
	public boolean getHitStatus( ) {
		return this.hitStatus;
	}
	
	// Setter methods:
	
	public void setServerPlayerName(String playerName) {
		this.serverPlayerName = playerName;
	}
	
	public void setClientPlayerName(String playerName) {
		this.clientPlayerName = playerName;
	}
	
	public void setCoordinates(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setHitStatus(boolean hit) {
		this.hitStatus = hit;
	}

	public boolean getHitAttempt () {
		return hitAttempt;
	}

	public boolean getRespond () {
		return respond;
	}

	public void setHitAttempt (boolean flag) {
		hitAttempt =  flag;
	}


	public void setRespond (boolean flag) {
		respond = flag;
	}

}
