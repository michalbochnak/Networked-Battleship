package Controller;

import View.GameboardView;

public class GameboardController {

	private GameController gamecontroller;
	private GameboardView gameboardView;
	
	private int shipSpace;
	
	// Default constructor:
	
	public GameboardController(GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
		this.gameboardView = new GameboardView();
		
		this.shipSpace = 17;
		
		this.initialize();
	}
	
	// Getter methods:
	
	public GameboardView getGameboardView( ) {
		return this.gameboardView;
	}
	
	// Setter methods:
	
	// Class methods:
	
	private void initialize() {
		
	}
	
	public void setPlayerNames() {
		this.gameboardView.setPlayerName(this.gamecontroller.getPlayerName());
		this.gameboardView.setOpponentName(this.gamecontroller.getOpponentName());
		this.gameboardView.updatePlayeNames();
	}
	
}
