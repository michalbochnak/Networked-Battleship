//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// OpponentBoardView.java
//

//
//  OpponentBoardView class is a class in which we view the opponents board.
//  we initialize it here
//



package View;

public class OpponentBoardView extends BoardView {
	
	// Default constructor:
	
	private static final long serialVersionUID = 1L;

	public OpponentBoardView() {
		
		this.initialize();
	}
	
	// Getter methods:
		
	// Setter methods:
			
	// Class methods:
	
	private void initialize() {
		setBounds(890, 30, 500, 500);
	}

}
