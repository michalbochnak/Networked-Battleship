package Model;

import java.io.Serializable;

public class NetworkDataModel implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String serverPlayerName;
	private String clientPlayerName;
	
	public NetworkDataModel() {
		this.serverPlayerName = null;
		this.clientPlayerName = null;
	}
	
	// Getter methods:
	
	public String getServerPlayerName() {
		return this.serverPlayerName;
	}
	
	public String getClientPlayerName() {
		return this.clientPlayerName;
	}
	
	// Setter methods:
	
	public void setServerPlayerName(String playerName) {
		this.serverPlayerName = playerName;
	}
	
	public void setClientPlayerName(String playerName) {
		this.clientPlayerName = playerName;
	}
	
	

}
