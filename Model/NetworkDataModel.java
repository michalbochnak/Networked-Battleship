package Model;

import java.io.Serializable;

public class NetworkDataModel implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String serverPlayerName;
	private String clientPlayerName;
	
	private int row;
	private int col;
	private boolean hitStatus;
	
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
	
	public void setHit(boolean hit) {
		this.hitStatus = hit;
	}

}
