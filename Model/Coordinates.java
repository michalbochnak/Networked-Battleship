
//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// Coordinates.java
//

//
//  class description...
//

package Model;

public class Coordinates {

    private int myRow, myCol;

    public Coordinates(int row, int col) {
        myRow = row;
        myCol = col;
    }

    public int getMyRow() {
        return myRow;
    }

    public int getMyCol() {
        return myCol;
    }
}
