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

package View;


/*
 Notes:

*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImagePanel extends JPanel {

    private BufferedImage bgImg;


    public ImagePanel() {
        bgImg = readImageIn("images/water_02_a.png");
        resizeImage();
        this.setLayout(new BorderLayout());
    }


    public void setBgImage(String filepath) {
        bgImg = readImageIn(filepath);
        resizeImage();
    }

    private BufferedImage readImageIn(String filepath) {
        BufferedImage tempImg = null;

        try {
            tempImg = ImageIO.read(new File(filepath));
        } catch (IOException exc) {
            System.out.println("Image loading failed");
            exc.printStackTrace();
        }

        return tempImg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, null);
    }

    // resizeImage the image to 600 x 600
    private void resizeImage() {
        Image img = bgImg.getScaledInstance
                (600, 600, Image.SCALE_DEFAULT);

        BufferedImage scaledImg = new BufferedImage(img.getWidth(null),
                img.getHeight(null), BufferedImage.TYPE_INT_ARGB );

        // draw the image
        Graphics2D temp = scaledImg.createGraphics();
        temp.drawImage(img, 0, 0, null);
        temp.dispose();

        bgImg = scaledImg;
    }

}
