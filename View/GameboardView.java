package View;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Model.Coordinates;

public class GameboardView extends JPanel{

	private static final long serialVersionUID = 1L;
	private ControlsView controlsView;
	private PlayerBoardView playerBoardView;
	private OpponentBoardView opponentBoardView;
	private StatusBarView statusBarView;
	private JLabel playerNameLabel;
	private JLabel opponentNameLabel;
	private String playerName;
	private String opponentName;
	private Set shipsOnBoard;
	private int gameStage;
	private BoardCellsMouseLisener boardCellsMouseListener;
	
	private int shipSelected;
	private int shipDirection;
	

	// Default constructor
	
	public GameboardView() {
		
		this.controlsView = new ControlsView();
		this.playerBoardView = new PlayerBoardView();
		this.opponentBoardView = new OpponentBoardView();
		//this.statusBarView = new StatusBarView();
		
		this.playerNameLabel = new JLabel();
		this.opponentNameLabel = new JLabel();
		this.playerName = null;
		this.opponentName = null;
		this.shipSelected = 0;
		this.shipsOnBoard = new HashSet<Integer>(5);
		this.shipDirection = 0;
		this.gameStage = 1;
		this.boardCellsMouseListener = new BoardCellsMouseLisener();
		
		this.initialize();
	}
	
	
	// Getter methods:
		
	public GameboardView getGameboardView() {
		return this;
	}
	
	// Setter methods:
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}
	
	// Class methods:

	private void initialize() {
		setBackground(Color.WHITE);
		setBounds(0,0,1400, 700);
		setLayout(null);
		
		this.playerNameLabel.setBounds(340, 10, 500, 20);
		this.playerNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.playerNameLabel);
		this.opponentNameLabel.setBounds(890, 10, 500, 20);
		this.opponentNameLabel.setFont(new Font("TimesRoman", Font.BOLD, 18));
		this.opponentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.opponentNameLabel);
		
		this.controlsView.addShipsActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String shipType  = ((JButton) e.getSource()).getIcon().toString();
	            switch (shipType) {
	                case "Aircraft Carrier":
	                    shipSelected = 5;
	                    break;
	                case "Battleship":
	                    shipSelected = 4;
	                    break;
	                case "Destroyer":
	                    shipSelected = 3;
	                    break;
	                case "Submarine":
	                    shipSelected = 2;
	                    break;
	                case "Patrol Boat":
	                    shipSelected = 1;
	                    break;
	            }

	            removeSelectionShipBorders();
	            resetShipSelectionButtonsBorders();
	            if (!shipsOnBoard.contains(shipSelected)) {
	                ((JButton) e.getSource()).setBorder(BorderFactory.
	                        createLineBorder(Color.DARK_GRAY, 4));
	            }

	            System.out.println(shipSelected + " was selected.");	
			}
		});
		
		this.controlsView.addPlaceModeButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (shipDirection == 1){
	                shipDirection = 0;
	                controlsView.getPlaceModeButton().setText("Placing horizontally");
	            }
	            else if (shipDirection == 0) {
	                shipDirection = 1;
	                controlsView.getPlaceModeButton().setText("Placing vertically");
	            }				
			}
		});
		
		this.playerBoardView.addCellsMouseListener(boardCellsMouseListener);
		
		add(this.controlsView);
		add(this.playerBoardView);
		opponentBoardView.setVisible(false);
		add(this.opponentBoardView);
	}
	
	public void updatePlayeNames() {
		this.playerNameLabel.setText("Player " + this.playerName + ":");
		this.opponentNameLabel.setText("Opponent " + this.opponentName + ":");
	}
	
	private void removeSelectionShipBorders() {
        for (JButton button : this.controlsView.getShipSelectionButtons())
        		button.setBorder( BorderFactory.createEmptyBorder());
        this.controlsView.getPlaceModeButton().setBorder( BorderFactory.createEmptyBorder());
    }
	
	private void resetShipSelectionButtonsBorders() {
		for (JButton button : this.controlsView.getShipSelectionButtons())
			button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		this.controlsView.getPlaceModeButton().setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
	}
	
	
	 private void highlightHorizontally(int shipSize, Coordinates c) {
		 if ((10 - c.getCol()) >= shipSize) {
			 int row = c.getRow();
	         for (int col = c.getCol(); col < c.getCol() + shipSize; ++col) {
	                highlighButton(new Coordinates(row, col));
	            }
	        }
	    }

	    private void highlightVertically(int shipSize, Coordinates c) {
	        if ((10 - c.getRow()) >= shipSize) {
	            int col = c.getCol();
	            for (int row = c.getRow(); row < c.getRow() + shipSize; ++row) {
	                highlighButton(new Coordinates(row, col));
	            }
	        }
	    }

	    private void highlighButton(Coordinates c) {
	    		playerBoardView.getButtons()[c.getRow()][c.getCol()].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
	    }
	
	private int findShipSize() {
	        // size same as ID
	    if (shipSelected == 5 || shipSelected == 4 || shipSelected == 3)
	            return shipSelected;
	        // size one more that ID
	        else if (shipSelected == 2 || shipSelected == 1)
	            return shipSelected + 1;
	        else
	            return -1;
	    }
	    
	private void clearHighlightsFromAllButtons() {
		
		int borderWidth  = 1;
		Color borderColor = Color.BLACK;
		
		int i = 0, j = 0; 
		for (BoardCell row[] : playerBoardView.getButtons()) {
			i++;
            for (BoardCell b : row) {
            	if (i == 0) {
        			if (j == 0) {
        				b.setBorder(BorderFactory.createMatteBorder(borderWidth, borderWidth, borderWidth, borderWidth, borderColor));
        			} else {
        				b.setBorder(BorderFactory.createMatteBorder(borderWidth, 0, borderWidth, borderWidth, borderColor));
        			}
        		} else {
        			if (j == 0) {
        				b.setBorder(BorderFactory.createMatteBorder(0, borderWidth, borderWidth, borderWidth, borderColor));
        			} else {
        				b.setBorder(BorderFactory.createMatteBorder(0, 0, borderWidth, borderWidth, borderColor));
        			}
        		}
            	j++;
            }
		}
        
	}
	
	private boolean fitsOnBoard(Coordinates c) {
        int bound = ((shipDirection == 0) ? c.getCol() : c.getRow());
        return (9 - bound) + 1 >= findShipSize();
    }
	
	private boolean enoughSpaceForShip(Coordinates c) {
        return fitsOnBoard(c) && doNotCollideWithOtherShip(c);
    }
	
	private int findClosestShipHorizontally(Coordinates c) {
        int freeSpots = 0;
        int row = c.getRow();
        BoardCell buttons[][] = this.playerBoardView.getButtons();
        for (int col = c.getCol(); col < 10; ++col) {
            if (buttons[row][col].getIcon() == null)
                freeSpots++;
            else
                break;
        }
        return freeSpots;
    }
	
	private int findClosestShipVertically(Coordinates c) {
        int freeSpots = 0;
        int col = c.getCol();
        BoardCell buttons[][] = this.playerBoardView.getButtons();
        for (int row = c.getRow(); row < 10; ++row) {
            if (buttons[row][col].getIcon() == null)
                freeSpots++;
            else
                break;
        }
        return freeSpots;
    }

	
	private boolean doNotCollideWithOtherShip(Coordinates c) {
        if (shipDirection == 0) { // horizontal
            return findClosestShipHorizontally(c) >= findShipSize();
        }
        else {      // vertical
            return findClosestShipVertically(c) >= findShipSize();
        }
    }

	public BufferedImage resize(BufferedImage img, int width, int height) {

	      Image scaledImg = img.getScaledInstance
	              (width, height, Image.SCALE_DEFAULT);


	      BufferedImage tempImage = new BufferedImage(scaledImg.getWidth(null),
	              scaledImg.getHeight(null), BufferedImage.TYPE_INT_ARGB );

	      // draw the image
	      Graphics2D temp = tempImage.createGraphics();
	      temp.drawImage(scaledImg, 0, 0, null);
	      temp.dispose();

	      return tempImage;
	  }
	
	
	public BufferedImage loadImage (String filepath) {
		  BufferedImage img = null;
		  try {
		      File f = new File(filepath);
		      FileInputStream fs = new FileInputStream(f);
		      img = ImageIO.read(fs);
		  } catch (Exception ex) {
		      System.out.println("Loading image error");
		      System.out.println(ex);
		  }

		  return img;
	}
	
	private String findShipFilepath() {
        String filepath = "Filepath not found";

        switch (shipSelected) {
            case 5:
                filepath = "images/russia-kuznetsov.png";
                break;
            case 4:
                filepath = "images/yamato.png";
                break;
            case 3:
                filepath = "images/destroyer.png";
                break;
            case 2:
                filepath = "images/typhoon.png";
                break;
            case 1:
                filepath = "images/patrol_boat.png";
                break;
        }
        return filepath;
    }
	
	private BufferedImage generateScaledShip() {
        BufferedImage tempImg = null;

        switch (shipSelected) {
            case 5:
                tempImg = this.resize(this.loadImage
                        (findShipFilepath()), 270, 60);
                break;
            case 4:
                tempImg = this.resize(this.loadImage
                        (findShipFilepath()), 218, 60);
                break;
            case 3:
                tempImg = this.resize(this.loadImage
                        (findShipFilepath()), 164, 60);
                break;
            case 2:
                tempImg = this.resize(this.loadImage
                        (findShipFilepath()), 164, 60);
                break;
            case 1:
                tempImg = this.resize(this.loadImage
                        (findShipFilepath()), 110, 50);
                break;
        }
        return tempImg;
    }
	
	
	private BufferedImage rotateImage(BufferedImage img) {
        BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Graphics2D gr = (Graphics2D)temp.getGraphics();
        gr.rotate(Math.toRadians(90), img.getWidth() / 2, img.getHeight() / 2);
        gr.drawImage(img, 0, 0, null);
        gr.dispose();
        return temp;
    }

	
	private ImageIcon[] cutIcon(int pieces) {
        BufferedImage origImg = generateScaledShip();
        BufferedImage imgs[] = new BufferedImage[pieces];
        int pieceW = origImg.getWidth() / pieces;
        int pieceH = origImg.getHeight();

        for (int i = 0; i < pieces; ++i) {
            imgs[i] = new BufferedImage(pieceW, pieceH, origImg.getType());
            // draw the image chunk
            Graphics2D gr = imgs[i].createGraphics();
            gr.drawImage(origImg, 0,0, pieceW, pieceH,
                    pieceW * i, 0, pieceW*i +pieceW, pieceH,
                    null);
            gr.dispose();
        }

        ImageIcon icons[] = new ImageIcon[pieces];
        for (int i = 0; i < imgs.length; ++i) {
            // placing vertically -> rotate
            if (shipDirection == 1) { imgs[i] = rotateImage(imgs[i]); };
            icons[i] = new ImageIcon(imgs[i]);
        }
        return icons;
    }
	
	private int findIndex (int shipID) {
        int index = -1;
        switch (shipID) {
            case 5:
                index = 0;
                break;
            case 4:
                index = 1;
                break;
            case 3:
                index = 2;
                break;
            case 2:
                index = 3;
                break;
            case 1:
                index = 4;
                break;
        }
        return index;
    }
	
	public void placeShipHorizontally(ImageIcon shipIcons[], Coordinates c) {
        int row = c.getRow();
        int col = c.getCol();
        for (int i = 0; i < shipIcons.length; ++i) {
            this.playerBoardView.getButtons()[row][col++].setIcon(shipIcons[i]);
        }
    }

    public void placeShipVertically(Icon shipIcons[], Coordinates c) {
        int row = c.getRow();
        int col = c.getCol();
        for (Icon i : shipIcons) {
        		this.playerBoardView.getButtons()[row++][col].setIcon(i);
        }
    }
	
	
	private void tryToPlaceShip(MouseEvent e) {
        Coordinates c = ((BoardCell) e.getSource()).getCoordinates();
        if (shipDirection == 0) {       // horizontal
            if (enoughSpaceForShip(c)) {
                ImageIcon shipPieces[] = cutIcon(findShipSize());
                this.placeShipHorizontally(shipPieces, c);
                shipsOnBoard.add(shipSelected);
                this.controlsView.getShipSelectionButtons()[findIndex(shipSelected)].setEnabled(false);
            }
        }
        else  {             // vertical
            if (enoughSpaceForShip(c)) {
                ImageIcon shipPieces[] = cutIcon(findShipSize());
                this.placeShipVertically(shipPieces, c);
                shipsOnBoard.add(shipSelected);
                this.controlsView.getShipSelectionButtons()[findIndex(shipSelected)].setEnabled(false);
            }
        }
        if (shipsOnBoard.size() == 5) {
            gameStage = 2;
            this.removeSelectionShipBorders();
            this.playerBoardView.removeCellsMouseListener(boardCellsMouseListener);
            this.clearHighlightsFromAllButtons();
            this.controlsView.setEnabledControls(false);
        }
    }
	
	class BoardCellsMouseLisener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (gameStage == 0) {
	            // ignore click
	            System.out.println("Click ignored...");
	        }
	        // Ship placement stage
	        else if (gameStage == 1 && !shipsOnBoard.contains(shipSelected) && shipSelected != -1) {
	            tryToPlaceShip(e);
	            if (shipsOnBoard.size() == 5) {
	            	opponentBoardView.setVisible(true);
	            }
	        }
	        // Game stage
	        else if (gameStage == 2){
	            System.out.println("Game in progress");
	        }
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			clearHighlightsFromAllButtons();
	        if (shipDirection == 0){
	            highlightHorizontally(findShipSize(),
	                    ((BoardCell)e.getSource()).getCoordinates());
	        }
	        else if (shipDirection == 1) {
	            highlightVertically(findShipSize(),
	                    ((BoardCell)e.getSource()).getCoordinates());;
	        }
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			clearHighlightsFromAllButtons();
		}
	}

	
                        
}
