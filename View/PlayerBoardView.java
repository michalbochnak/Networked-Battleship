//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// PlayerBoardView.java
//

//
//  PlayerBoardView class is a class in which we view the player board.
//  We initialize it here.
//



package View;

public class PlayerBoardView extends BoardView {

	private static final long serialVersionUID = 1L;

	// Default constructor:
	public PlayerBoardView() {
		
		this.initialize();
	}
	
	// Getter methods:
		
	// Setter methods:
			
	// Class methods:
	
	private void initialize() {
		setBounds(340, 30, 500, 500);
	}
}
