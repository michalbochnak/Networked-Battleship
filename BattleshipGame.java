//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// Controller.BattleshipGame.java
//

//
//  class description...
//



import Controller.GameController;

public class BattleshipGame {

    public static void main ( String args[]) {
        GameController battleshipGame = new GameController();
        battleshipGame.startGame(5);
    }
}
