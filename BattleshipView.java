//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// BattleshipView.java
//

//
//  class description...
//

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class BattleshipView {

    private JFrame frame;
    private JPanel bgPanel;
    private BufferedImage img;

    BattleshipView() {
        try {
            img = ImageIO.read(new File("images/water_bg.jpg"));
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        setupFrame();
        setupPanel();
    }

    private void setupFrame() {
        frame = new JFrame("Networked Battleship");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setupPanel() {

        bgPanel = new MyPanel();
        //bgPanel.setLayout(new GridLayout(10,10,2, 2));;
        frame.add(bgPanel);

        /*
        bgPanel  = new ImagePanel();

        bgPanel.setSize(500, 500);
        bgPanel.setLayout(new GridLayout(10, 10, 1, 1));

        frame.add(bgPanel);
        */
    }

    private class MyPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
