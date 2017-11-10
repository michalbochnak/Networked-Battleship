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
    private JPanel mainPanel;
    private JLabel infoLabel;
    private JLabel aircraft;
    private Color myColor = Color.white;


    public Board(String labelTitle) {
        //addNumberIndicators();
        setupMainPanel();
        setupButtons();
        setupInfoLabel(labelTitle);
        //addLetterIndicators();
        //setLayout(new GridLayout(10,10));
        //addIndicatorBars();
        addIndicatorBars();
    }


    public Button[][] getButtons() {
        return buttons;
    }

    private void addIndicatorBars() {
        add_ABC_Bar();
        add_123_Bar();
    }


    private void add_ABC_Bar() {
        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(50, 500));
        bar.setLayout(new GridLayout(10, 1));
        bar.setBackground(myColor);
        for (int i = 1; i <= 10; ++i) {
            JLabel label = new JLabel(Integer.toString(i));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            //label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
            bar.add(label);
        }
        mainPanel.add(bar, BorderLayout.WEST);
    }

    private void add_123_Bar() {
        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(500, 50));
        bar.setLayout(new GridLayout(1, 10));
        bar.setBackground(myColor);
        for (int i = 64; i <= 74; ++i) {
            JLabel label = new JLabel(Character.toString((char) i));
            if (i == 64) { label.setText(""); };
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            //label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
            bar.add(label);
        }
        mainPanel.add(bar, BorderLayout.NORTH);
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
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(10,10));
        grid.setOpaque(false);

        for (int i = 0; i < 10; ++i ) {
            for (int k = 0; k < 10; ++k) {
                buttons[i][k] = new Button(i, k);
                buttons[i][k].setBackground(Color.white);
                buttons[i][k].repaint();
                buttons[i][k].setOpaque(false);
                grid.add(buttons[i][k]);
            }
        }

        //GridBagConstraints gbc = new GridBagConstraints();
        //gbc.gridx = 0;
        //gbc.gridy = 0;
        //gbc.gridwidth = 400;
        //gbc.gridheight = 400;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(grid, BorderLayout.CENTER);
    }

    private void setupInfoLabel(String labelTitle) {
        infoLabel = new JLabel(labelTitle);
    }

    public void setupMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(500, 500);
        mainPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
    }

}









