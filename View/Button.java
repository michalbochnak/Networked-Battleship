//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// Button.java
//

//
//  class description...
//

package View;


import Model.Coordinates;

import javax.swing.*;

public class Button extends JButton {

    // 0 - empty, 1 - ship, 2 - miss, 3 - hit
    private int status;
    private Coordinates coords;
    private double opacity;

    protected  Button(int row, int col) {
        coords = new Coordinates(row, col);
    }

    protected void setOpacity(double value) {
        opacity = value;
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    protected double getOpacity() {
        return opacity;
    }

}
