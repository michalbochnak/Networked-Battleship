
//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// BattleshipModel.java
//

//
//  This class creates an enum for each ship, giving it a uniquie number to later
// use when creating the game
//

package Model;

/*
Todo:
    - Please, make sure you store ships in the grid as we talked
        so I can verify which part of ship was hit to update the
        proper image on the grid
 */

public class BattleshipModel {

    private int board[][];
    private enum ships {aircraft, battleship, destroyer, submarine, patrol};


    public BattleshipModel() {
        board = new int[10][10];
    }



}


