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
	private boolean hitStatus;		   // flag to see if hit was succersfull

	private boolean hitAttempt;		   // opponent was tryin g to hit
	private boolean respond;		       // response about attempted hit



	private  boolean playAgainRespond;

	private boolean disconectSignal;
	private boolean winner;
	private boolean playAgain;

	// Default constructor:
	
	public NetworkDataModel() {
		this.serverPlayerName = null;
		this.clientPlayerName = null;
		int row = 0;
		int col = 0;
		this.hitStatus = false;
		this.hitAttempt = false;
		this.respond = false;
		this.disconectSignal = false;
		this.winner = false;
		this.playAgain = false;
		this.playAgainRespond = false;
	}
	
	// Getter methods:
	
	public boolean isDisconected() {
		return this.disconectSignal;
	}

	public boolean isWinner() {
		return this.winner;
	}

	public boolean isPlayAgain() {
		return this.playAgain;
	}
	
	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public void setDisconectSignal(boolean disconectSignal) {
		this.disconectSignal = disconectSignal;
	}
	
	public void setPlayAgain(boolean playAgain) {
		this.playAgain = playAgain;
	}
	
	public void setCoordinates(Coordinates c) {
		this.row = c.getRow();
		this.col = c.getCol();
	}
	
	public boolean getHitStatus( ) {
		return this.hitStatus;
	}


	public boolean isPlayAgainRespond() {
		return playAgainRespond;
	}


	
	// Setter methods:
	
	public String getServerPlayerName() {
		return this.serverPlayerName;
	}
	
	public String getClientPlayerName() {
		return this.clientPlayerName;
	}
	
	public Coordinates getCoordinates() {
		return new Coordinates(this.row, this.col);
	}
	
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
		return this.hitAttempt;
	}

	public boolean getRespond () {
		return this.respond;
	}

	public void setHitAttempt (boolean flag) {
		this.hitAttempt =  flag;
	}

	public void setRespond (boolean flag) {
		this.respond = flag;
	}

	public void setPlayAgainRespond(boolean flag) {
		this.playAgainRespond = flag;
	}

	public  void  resetFlags() {
		this.hitStatus = false;
		this.hitAttempt = false;
		this.respond = false;
		this.disconectSignal = false;
		this.winner = false;
		this.playAgain = false;
		this.playAgainRespond = false;
	}

}
