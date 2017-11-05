//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// Board.java
//

//
//  class description...
//

package View;

import javax.swing.*;
import java.awt.*;

public class Board extends ImagePanel{

    private Button[][] buttons;
    private JLabel infoLabel;


    public Board(String labelTitle) {
        setupButtons();
        setupInfoLabel(labelTitle);
    }

    private void setupButtons() {
        buttons = new Button[10][10];

        for (int i = 0; i < 10; ++i ) {
            for (int k = 0; k < 10; ++k) {
                buttons[i][k] = new Button(i, k);
                buttons[i][k].setBackground(Color.white);
                //buttons[i][k].setOpacity(0.5);
                buttons[i][k].repaint();
                buttons[i][k].setOpaque(false);
                this.add(buttons[i][k]);
            }
        }
    }

    private void setupInfoLabel(String labelTitle) {
        infoLabel = new JLabel(labelTitle);
    }
}










