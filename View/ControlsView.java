package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlsView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton[] shipSelectionButtons;
	private JButton placeModeButton;
	private Color bgColor = Color.white;

	
	// Default constructor:
	
	public ControlsView() {
		
		this.shipSelectionButtons = new JButton[5];
		this.placeModeButton = null;
		
		this.initialize();
	}
	
	// Getter methods:
	
	
	// Setter methods:
		
	// Class methods:
	
	private void initialize() {
		setLayout(null);
		setBounds(0, 0, 300, 700);
		setBackground(bgColor);
	    //setBorder(BorderFactory.createLineBorder(Color.white, 8));
	    
        for (int i = 0, rowCounter = 55; i< 5; ++i, rowCounter += 84) {
            this.shipSelectionButtons[i] = new JButton();
            this.shipSelectionButtons[i].setBounds(10, rowCounter, 290, 80);
            this.shipSelectionButtons[i].setBackground(bgColor);
            add(shipSelectionButtons[i]);
            
        }
       
        this.addShips();
        this.addPlaceModeButton();
	}
	
	private void addShips() {
		this.addAircraft();
	    this.addBattleship();
	    this.addDestroyer();
	    this.addSubmarine();
	    this.addPatrolBoat();
	}
	
	private BufferedImage loadImage(String filepath) {
		BufferedImage image = null;
        try {
            File file = new File(filepath);
            FileInputStream fileStream = new FileInputStream(file);
            image = ImageIO.read(fileStream);
        } catch (Exception e) {
            System.err.println("Loading image error: " + e.getMessage());
        }
        return image;
	}
	
	private BufferedImage resizeImage(BufferedImage img, int width, int height) {
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage tempImage = new BufferedImage(scaledImg.getWidth(null), scaledImg.getHeight(null), BufferedImage.TYPE_INT_ARGB );

        // draw the image
        Graphics2D temp = tempImage.createGraphics();
        temp.drawImage(scaledImg, 0, 0, null);
        temp.dispose();

        return tempImage;
    }

	private void addAircraft() {
		BufferedImage image = this.resizeImage(this.loadImage("images/russia-kuznetsov.png"), 260, 60);
	    ImageIcon icon = new ImageIcon(image, "Aircraft Carrier");
	    this.shipSelectionButtons[0].setIcon(icon);
	}

	private void addBattleship() {
	    BufferedImage image = this.resizeImage(this.loadImage("images/yamato.png"), 208, 60);
	    ImageIcon icon = new ImageIcon(image, "Battleship");
	    this.shipSelectionButtons[1].setIcon(icon);
	}

	private void addDestroyer() {
		BufferedImage image = this.resizeImage(this.loadImage("images/destroyer.png"), 154, 60);
	    ImageIcon icon = new ImageIcon(image, "Destroyer");
	    this.shipSelectionButtons[2].setIcon(icon);
	}

	private void addSubmarine() {
	    BufferedImage image = this.resizeImage(this.loadImage("images/typhoon.png"), 154, 60);
	    ImageIcon icon = new ImageIcon(image, "Submarine");
	    shipSelectionButtons[3].setIcon(icon);
	}

	private void addPatrolBoat() {
	    BufferedImage image = this.resizeImage(this.loadImage("images/patrol_boat.png"), 102, 50);
	    ImageIcon icon = new ImageIcon(image, "Patrol Boat");
	    shipSelectionButtons[4].setIcon(icon);
	}

	private void addPlaceModeButton() {
        placeModeButton = new JButton("Place Ships Horizontally");
        placeModeButton.setBounds(10, 475, 290, 80);
        placeModeButton.setFont(new Font("Arial", Font.BOLD, 17));
        add(placeModeButton);
    }    

	public void setEnabledControls(boolean state) {
		for(int i = 0; i < 5; i++) {
			this.shipSelectionButtons[i].setEnabled(state);
		}
		this.placeModeButton.setEnabled(state);
	}
	
	public void addShipsActionListener(ActionListener actionListener) {
		for(int i = 0; i < 5; i++) {
			this.shipSelectionButtons[i].addActionListener(actionListener);
		}
		
	}
	
	public void addPlaceModeButtonActionListener(ActionListener actionListener) {
		this.placeModeButton.addActionListener(actionListener);;
	}
	
	
}
