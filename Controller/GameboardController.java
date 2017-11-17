//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// GameBoardController.java
//

//
//  class description...
//testing




package Controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.Coordinates;
import Model.NetworkDataModel;
import Model.NetworkModel;
import View.BoardCell;
import View.BoardView;
import View.ControlsView;
import View.GameboardView;
import View.OpponentBoardView;
import View.PlayerBoardView;

public class GameboardController {

	private GameController gamecontroller;
	private GameboardView gameboardView;

	private ControlsView controlsView;
	private PlayerBoardView playerBoardView;
	private OpponentBoardView opponentBoardView;

	private NetworkDataModel rxData;
	private NetworkDataModel txData;
	private NetworkModel networkConnection;
	
	private Set shipsOnBoard;
	private int gameStage;
	private PlayerBoardCellsMouseLisener playerBoardCellsMouseListener;
	private OpponentBoardCellsMouseLisener opponentBoardCellsMouseLisener;
	
	private boolean playerTurn;
	private Cursor customCusrsor;
	private int shipSelected;
	private int shipDirection;
	private int shipSpace;

	private int hits;
	private int misses;
	
	// Default constructor:
	
	public GameboardController(GameController gamecontroller) {
		this.gamecontroller = gamecontroller;
		this.gameboardView = new GameboardView();
		

		this.shipSelected = 0;
		this.shipsOnBoard = new HashSet<Integer>(5);
		this.shipDirection = 0;
		this.gameStage = 1;
		this.playerBoardCellsMouseListener = new PlayerBoardCellsMouseLisener();
		this.opponentBoardCellsMouseLisener = new OpponentBoardCellsMouseLisener();

		this.shipSpace = 17;
		this.customCusrsor = null;
		
		this.hits = 0;
		this.misses = 0;
		
		this.initialize();
	}
	
	// Getter methods:
	
	public GameboardView getGameboardView( ) {
		return this.gameboardView;
	}
	
	// Setter methods:
	
	// Class methods:
	
	private void initialize() {

		this.controlsView = this.gameboardView.getControlsView();
		this.playerBoardView = this.gameboardView.getPlayerBoardView();
		this.opponentBoardView = this.gameboardView.getOpponentBoardView();
		
		this.txData = new NetworkDataModel();
		this.networkConnection = this.gamecontroller.getNetworkConnection();

		if(this.gamecontroller.getGameMode() == 1) {
			this.playerTurn = false;
		} else {
			this.playerTurn = true;
		}


		opponentBoardView.setVisible(true);
		this.startPlayingGame();

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


		this.playerBoardView.addCellsMouseListener(playerBoardCellsMouseListener);

	}


	public void setPlayerNames() {
		this.gameboardView.setPlayerName(this.gamecontroller.getPlayerName());
		this.gameboardView.setOpponentName(this.gamecontroller.getOpponentName());
		this.gameboardView.updatePlayerNames();
	}

//    public void updatePlayerBoard(Coordinates c) {
//        boolean hit = gameboardView.updatePlayerBoard(c);
//
//    }

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
	                highlighButton(this.playerBoardView, new Coordinates(row, col));
	         }
	      }
	}

	private void highlightVertically(int shipSize, Coordinates c) {
		 if ((10 - c.getRow()) >= shipSize) {
	        int col = c.getCol();
	        for (int row = c.getRow(); row < c.getRow() + shipSize; ++row) {
	        highlighButton(this.playerBoardView, new Coordinates(row, col));
	        }
	     }
	 }

	private void highlighButton(BoardView board, Coordinates c) {
		board.getButtons()[c.getRow()][c.getCol()].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
	}

	private int findShipSize() {
        // size same as ID
    if (shipSelected == 5 || shipSelected == 4 || shipSelected == 3)
            return shipSelected;
        // size one more that ID
        else if (shipSelected == 2 || shipSelected == 1)
            return shipSelected + 1;
        else
            return 0;
    }

	private void clearHighlightsFromAllButtons(BoardView board) {

		int borderWidth  = 1;
		Color borderColor = Color.BLACK;

		int i = 0, j = 0;
		for (BoardCell row[] : board.getButtons()) {
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

		for (int i = 0; i < pieces; ++i)
			imgs[i] = resize(imgs[i], 45, 45);

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
	        this.playerBoardView.removeCellsMouseListener(playerBoardCellsMouseListener);
	        this.clearHighlightsFromAllButtons(this.playerBoardView);
	        this.controlsView.setEnabledControls(false);
	    }
	}

	private void startPlayingGame() {
		try{
			this.customCusrsor = Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("images/skull_02_cursor_orange.png").getImage(),new Point(22,22),"custom cursor");

        } catch(Exception e){
        		System.err.println("Cannot create custom image:" + e.getMessage());
        }

		System.out.println(this.playerTurn);

		this.opponentBoardView.addCellsMouseListener(opponentBoardCellsMouseLisener);
		this.opponentBoardView.setCursor(this.customCusrsor);

		Thread waitForData = new Thread(new WaitForIncommingData());
		waitForData.start();
	}

	public void updateOpponentBoard(Coordinates c,  boolean hit) {
		int row = c.getRow();
		int col = c.getCol();
		BoardCell bc = this.opponentBoardView.getButtons()[row][col];

		System.out.println("updateOpponentBoard.................");

		if (hit == true) {
			updateHitJustExplosion(bc);
		}
		else {
			updateMiss(bc);
		}
	}

	private void updateHitJustExplosion(BoardCell bc) {
		BufferedImage img = resize(loadImage("images/hit.png"), 45, 45);
		bc.setIcon(new ImageIcon(img));
	}


	public boolean updatePlayerBoard(Coordinates c) {

		int row = c.getRow();
		int col = c.getCol();
		BoardCell bc = this.playerBoardView.getButtons()[row][col];
		//this.playerBoardView.getButtons()[row][col++].setIcon(shipIcons[i]);

		//System.out.println("updatePlayerBoard................." + bc.getIcon().toString());

		// miss
		if (bc.getIcon() == null) {
			updateMiss(bc);
			misses++;
			return false;
		}
		// hit
		else if ( ! (bc.getIcon().toString().equals("Miss") )) {
			updateHit(bc);
			hits++;
			return true;
		}
		// clicked button already marked as miss / hit
		else {
			return false;
		}

	}

	public void updateMiss(BoardCell bc) {
        System.out.println("Miss" );
        BufferedImage img = resize(loadImage("images/miss.png"),
				40, 40);
		bc.setIcon(new ImageIcon(img, "Miss"));
    }

	private void updateHit(BoardCell bc) {

        System.out.println("Hit");
        BufferedImage img = iconToBuffImg((ImageIcon)bc.getIcon());
        bc.setIcon(new ImageIcon(redrawIcon(img)));
	}

	// draw explosion image on the top of the current icon
	private BufferedImage redrawIcon(BufferedImage img) {
		BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(),
				img.getType());
		BufferedImage explosion = resize(loadImage("images/hit.png"),
			45, 45);
		Graphics2D gr = (Graphics2D)temp.getGraphics();
		gr.drawImage(img, 0, 0, null);
		gr.drawImage(explosion, 0, 0, null);
		gr.dispose();
		return temp;
	}

	private BufferedImage iconToBuffImg(ImageIcon i) {
		BufferedImage img = new BufferedImage(
				i.getIconWidth(),
				i.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		// paint the Icon to the BufferedImage.
		i.paintIcon(null, g, 0,0);
		g.dispose();

		return img;
	}

	private void toggleTurn(boolean playerTurn) {
		this.playerTurn = playerTurn;
		if(this.playerTurn == false) {
			this.opponentBoardView.removeCellsMouseListener(opponentBoardCellsMouseLisener);
			this.clearHighlightsFromAllButtons(this.opponentBoardView);
			this.opponentBoardView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			this.opponentBoardView.validate();
		} else {
			this.opponentBoardView.addCellsMouseListener(opponentBoardCellsMouseLisener);
			this.opponentBoardView.setCursor(this.customCusrsor);
			this.opponentBoardView.validate();
		}
	}


	public void sendAnswer(Coordinates c) {
		boolean success = updatePlayerBoard(c);
		txData.setCoordinates(c.getRow(), c.getCol());
		txData.setHitStatus(success);
		networkConnection.sendData(txData);
	}

	private void showWinMessage() {
		String message = "Congratulation " + this.gamecontroller.getPlayerName() + ", you WON!";
        String title = "You Win!";
        JOptionPane.showMessageDialog(this.gamecontroller.getMainWindow(), message, title, JOptionPane.PLAIN_MESSAGE);
		this.networkConnection.closeConnection();
        this.gamecontroller.setDefaultMenuWindow();
		this.gamecontroller.startGame(2);
	}

	private void showLoseMessage() {
		String message = "Sorry " + this.gamecontroller.getPlayerName() + ", but you lose :(";
        String title = "You Lose!";
        JOptionPane.showMessageDialog(this.gamecontroller.getMainWindow(), message, title, JOptionPane.PLAIN_MESSAGE);
        this.networkConnection.closeConnection();
        this.gamecontroller.setDefaultMenuWindow();
		this.gamecontroller.startGame(2);
	}

	// Inner Classes:

	class PlayerBoardCellsMouseLisener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {


            // Ship placement stage
            if (gameStage == 1 && !shipsOnBoard.contains(shipSelected) && shipSelected != -1) {
                tryToPlaceShip(e);

                if (shipsOnBoard.size() == 5) {
                    //	opponentBoardView.setVisible(true);

                }
            }
	        // Game stage
	        else if (gameStage == 2){
                System.out.println("Game in progress");
				updatePlayerBoard( ((BoardCell) e.getSource()).getCoordinates() );
                //startPlayingGame();
            }

        }

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			clearHighlightsFromAllButtons(playerBoardView);
	        if (shipDirection == 0){
	            highlightHorizontally(findShipSize(),
	                    ((BoardCell)e.getSource()).getCoordinates());
	        } else if (shipDirection == 1) {
	            		highlightVertically(findShipSize(),
	                    ((BoardCell)e.getSource()).getCoordinates());;
	        		}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				clearHighlightsFromAllButtons(playerBoardView);
			}
		}

		class OpponentBoardCellsMouseLisener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Coordinates coordinates = ((BoardCell)e.getSource()).getCoordinates();
				txData.setCoordinates(coordinates.getRow(), coordinates.getCol());
				//System.out.println("cordROW: " + coordinates.getRow() + " coordCOL: " + coordinates.getCol());
                //System.out.println("txROW: " + txData.getCoordinates().getRow()
				//+ " txCOL: " + txData.getCoordinates().getCol());
				txData.setRespond(false);
                txData.setHitAttempt(true);
				networkConnection.sendData(txData);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				highlighButton(opponentBoardView, ((BoardCell)e.getSource()).getCoordinates());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				clearHighlightsFromAllButtons(opponentBoardView);
			}

		}

		class WaitForIncommingData implements Runnable {

			@Override
			public void run() {

				while(true) {
					if(networkConnection != null) {
						try {
	                        rxData = networkConnection.getData();
	                        Coordinates c = rxData.getCoordinates();
							System.out.println("Get new Data: "
	                                + c.getRow() + " " + c.getCol());

							System.out.println("Respond():  " + rxData.getRespond());

							// respond message, update board only
							if (rxData.getRespond() == true) {
								// update opp board
								System.out.println("Responding....");

								updateOpponentBoard(c, rxData.getHitStatus());
							}
							// opponent tried to hit, update and send message back
							else if (rxData.getHitAttempt() == true) {
								boolean hit = updatePlayerBoard(c);
								txData.setCoordinates(c);
								txData.setHitStatus(hit);
								txData.setHitAttempt(false);
								txData.setRespond(true);
								networkConnection.sendData(txData);
								// respond
							}
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}

					
				}			
			}
		}


}

