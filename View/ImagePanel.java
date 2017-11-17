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
//  ImagePanel class is a class in which the image is loaded in as the background
// image. This class intializes, sets and then stores the graphics of a given selected
// image.
//

package View;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;


public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage backgroundImage;

    // Default Constructor:
    
    public ImagePanel() {
    		this.backgroundImage = new BufferedImage(450, 450, BufferedImage.TYPE_INT_ARGB);
  
        this.initialize();
    }

    // Getter methods:
    
    // Setter methods:
    
    // Class methods:
    
    private void initialize() {
    	BufferedImage tempImage = null;
    	try {
    		tempImage = ImageIO.read(new File("images/water_00.jpg"));
     } catch (IOException e) {
    	 	System.out.println("Image loading failed: " + e.getMessage());
     }
    	
    Graphics2D g2d = this.backgroundImage.createGraphics();
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.6));
    g2d.drawImage(tempImage.getScaledInstance(450, 450, Image.SCALE_DEFAULT), 0, 0, null);
    g2d.dispose();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(this.backgroundImage, 0, 0, null);
  }

}
