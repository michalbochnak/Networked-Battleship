//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// ImagePanel.java
//

//
//  class description...
//


// Notes:
//  - not used as for now

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImagePanel extends JPanel {

    private JPanel panel;
    private BufferedImage bgImg;

    ImagePanel() {
        bgImg = readImageIn();

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImg, 0, 0, null);
            }
        };

    }


    private BufferedImage readImageIn() {
        BufferedImage tempImg = null;

        try {
            tempImg = ImageIO.read(new File("images/water_bg.jpg"));
        } catch (IOException exc) {
            System.out.println("Image read in failed");
            exc.printStackTrace();
        }

        return tempImg;
    }


}
