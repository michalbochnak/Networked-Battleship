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
    private JLabel aircraft;


    public Board(String labelTitle) {
        //addNumberIndicators();
        setupButtons();
        setupInfoLabel(labelTitle);
        addLetterIndicators();
        //setLayout(new GridLayout(10,10));
        addIndicatorBars();
    }


    public Button[][] getButtons() {
        return buttons;
    }

    private void addIndicatorBars() {
        //add_ABC_Bar();
        //add_123_Bar();
    }


    private void add_ABC_Bar() {
        JPanel bar = new JPanel();
        bar.setSize(600, 50);
        bar.setBackground(Color.orange);
        add(bar);
    }

    private void add_123_Bar() {
        JPanel bar = new JPanel();
        bar.setSize(600, 50);
        bar.setBackground(Color.orange);
        add(bar);
    }


    private void addLetterIndicators() {

    }

    /*
    private void addNumberIndicators() {
        JPanel tempPanel = new JPanel();
        tempPanel.setSize(50, 600);
        // add 10 labels
        for (int i = 0; i < 10; ++i) {
            JLabel tempLabel = new JLabel();
            tempLabel.setText("1");
            tempPanel.add(tempLabel);
        }
        add(tempPanel, BorderLayout.WEST);
    }
    */

    private void setupButtons() {
        buttons = new Button[10][10];
        //JPanel grid = new JPanel();
        //grid.setSize(500,500);
        //grid.setBackground(Color.cyan);
        //grid.setLayout(new GridLayout(10,10));

        for (int i = 0; i < 10; ++i ) {
            for (int k = 0; k < 10; ++k) {
                buttons[i][k] = new Button(i, k);
                buttons[i][k].setBackground(Color.white);
                //buttons[i][k].setOpacity(0.5);
                buttons[i][k].repaint();
                buttons[i][k].setOpaque(false);
                add(buttons[i][k]);
            }
        }

        //add(grid, BorderLayout.SOUTH);
    }

    private void setupInfoLabel(String labelTitle) {
        infoLabel = new JLabel(labelTitle);
    }

}









