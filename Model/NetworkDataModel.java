package Model;

import java.io.Serializable;

public class NetworkDataModel implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String serverPlayerName;
	private String clientPlayerName;
	
	private Coordinates fireCoordinates;
	private boolean hitStatus;
	
	public NetworkDataModel() {
		this.serverPlayerName = null;
		this.clientPlayerName = null;
		this.fireCoordinates = new Coordinates(0,0);
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
		return this.fireCoordinates;
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
	
	public void setCoordinates(Coordinates coordinates) {
		this.fireCoordinates = coordinates;
	}
	
	public void setHit(boolean hit) {
		this.hitStatus = hit;
	}

}
