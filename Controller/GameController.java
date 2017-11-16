//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// BattleshipController.java
//

//
//  class description...
//

package Controller;

import Model.*;
import View.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;


public class GameController {

	private NetworkModel networkConnection;
	
	private GameWindowView gameWindowView;
	
	private MenuBarController menuBarController; 
	private ServerModeController serverModeController;
	private ClientModeController clientModeController;
	private GameMenuController gameMenuController;
	private GameboardController gameboardController;


    private BattleshipModel model;
   
    
    private String playerName;
    private String opponentName;
    private int gameMode;			// 0 - server, 1 - client
    private boolean gameStarted;
    
    
    // 0 - 2: 0 - connection, 1 - ship placement, 2 - game
    private int gameStage;
    // 5 - 1: 5 - aircraft, 4 - battleship, 3 - destroyer, 2 - submarine, 1 - patrol boat
    private int shipSelected;
    // 0 - horizontal, 1 - vertical
    private int shipDirection;
    private Set shipsOnBoard;

    // Default Constructor: 
    public GameController() {
    
    		this.networkConnection  = null;
    	
    		this.gameWindowView = new GameWindowView();
   		
    		this.serverModeController = new ServerModeController(this);
    		this.clientModeController = new ClientModeController(this);
    		this.gameMenuController = new GameMenuController(this);
    		this.menuBarController = new MenuBarController(this);
    		this.gameboardController = new GameboardController(this);
    		
    		
    		this.playerName = null;
    		this.opponentName = null;
    		this.gameMode = 0;
    	
//        view = new BattleshipView();
//        model = new BattleshipModel();
//        
//        
//        gameStage = 1;
//        shipDirection = 0;
//        shipSelected = -1;
//        shipsOnBoard = new HashSet<Integer>();
//        
//        view.addButtonsListener(new buttonHandler());
//        view.addMouseListener(new hoverHandlerShipSelection());
//        view.addShipSelectionListener(new shipSelectorHandler());
//        view.addPlaceModeButtonListener(new placeModeButtonListener());
    		initialize();
    }

    // Getter methods:
    
    public String getPlayerName() {
    		return this.playerName;
    }
    
    public int getGameMode() {
    		return this.gameMode;
    }
    
    public String getOpponentName() {
		return this.opponentName;
    }
    
    public JFrame getMainWindow() {
    		return this.gameWindowView.getGameWindow();
    }
    
    public NetworkModel getNetworkConnection() {
    		if(this.networkConnection != null) {
    			return this.networkConnection;
    		} else {
    			return null;
    		}
    }
    
    // Setter methods:
    
    public void setPlayerName(String playerName) {
    		this.playerName = playerName;
    }
    
    public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
    }
    
    public void setGameMode(int mode) {
    		this.gameMode = mode;
    }
    
    
    public void setNetworkConnection(NetworkModel networkConnection) {
    		if(this.networkConnection == null) {
    			this.networkConnection = networkConnection;
    		}
    }
    
    // Class methods:
    
    private void initialize() {
    	
    		this.gameWindowView.addMenuBar(this.menuBarController.getMenuBar());
    		this.gameWindowView.showMainWindow();
    		
    }
    
    public void startGame(int gameOption) {
    	
    		this.gameWindowView.getGameWindow().getContentPane().removeAll();
    		
    		switch (gameOption) {
    			
    			case 1:
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(false);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(false);
    				//this.menuBarController.getMenuBar().getMenuItems().get("Statistics").setEnabled(false);
    				this.gameWindowView.addContent(this.gameMenuController.getWelcomeMessageView());
    				break;
    			case 2:
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(false);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(false);
    				//this.menuBarController.getMenuBar().getMenuItems().get("Statistics").setEnabled(true);
    				this.gameWindowView.addContent(this.gameMenuController.getSelectGameModeView());
    				break;
    			case 3:
    				this.gameMode = 0;
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(false);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(false);
    				this.gameWindowView.addContent(this.serverModeController.getView());
    				break;
    			case 4:
    				this.gameMode = 1;
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(false);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(false);
    				this.gameWindowView.addContent(this.clientModeController.getView());
    				break;
    			case 5:
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(true);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(true);
    				this.gameWindowView.setNewSize(1400, 700);
    				this.gameboardController.setPlayerNames();
    				this.gameWindowView.addContent(this.gameboardController.getGameboardView());
    				break;
    		}
 
    }

    public void setDefaultMenuWindow() {
    		this.gameWindowView.setDefault();
    }


//    //FIXME: Implement specific action handlers
//    private class buttonHandler implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//
//            // Connection stage
//            if (gameStage == 0) {
//                // ignore click
//                System.out.println("Click ignored...");
//            }
//            // Ship placement stage
//            else if (gameStage == 1 && !shipsOnBoard.contains(shipSelected)
//                    && shipSelected != -1) {
//                tryToPlaceShip(event);
//                if (shipsOnBoard.size() == 5) {
//                    view.getOpponentBoard().setVisible(true);
//                }
//            }
//            // Game stage
//            else if (gameStage == 2){
//                System.out.println("Game in progress");
//            }
//        }
//    }
//
//    private void tryToPlaceShip(ActionEvent ev) {
//        Coordinates c = ((Button) ev.getSource()).getCoordinates();
//        if (shipDirection == 0) {       // horizontal
//            if (enoughSpaceForShip(c)) {
//                ImageIcon shipPieces[] = cutIcon(findShipSize());
//                view.placeShipHorizontally(shipPieces, c);
//                shipsOnBoard.add(shipSelected);
//                view.getShipSelectionButtons()[findIndex(shipSelected)].setBackground(Color.gray);
//            }
//        }
//        else  {             // vertical
//            if (enoughSpaceForShip(c)) {
//                ImageIcon shipPieces[] = cutIcon(findShipSize());
//                view.placeShipVertically(shipPieces, c);
//                shipsOnBoard.add(shipSelected);
//                view.getShipSelectionButtons()[findIndex(shipSelected)].setBackground(Color.gray);
//            }
//        }
//        if (shipsOnBoard.size() == 5) {
//            gameStage = 2;
//            view.removeHoverListener();
//            view.clearHighlightsFromAllButtons();
//        }
//    }
//
//    private int findIndex (int shipID) {
//        int index = -1;
//        switch (shipID) {
//            case 5:
//                index = 0;
//                break;
//            case 4:
//                index = 1;
//                break;
//            case 3:
//                index = 2;
//                break;
//            case 2:
//                index = 3;
//                break;
//            case 1:
//                index = 4;
//                break;
//        }
//        return index;
//    }
//
//    private boolean enoughSpaceForShip(Coordinates c) {
//        return fitsOnBoard(c) && doNotCollideWithOtherShip(c);
//    }
//
//    private boolean fitsOnBoard(Coordinates c) {
//        int bound = ((shipDirection == 0) ? c.getMyCol() : c.getMyRow());
//        return (9 - bound) + 1 >= findShipSize();
//    }
//
//    private boolean doNotCollideWithOtherShip(Coordinates c) {
//        if (shipDirection == 0) { // horizontal
//            return findClosestShipHorizontally(c) >= findShipSize();
//        }
//        else {      // vertical
//            return findClosestShipVertically(c) >= findShipSize();
//        }
//    }
//
//    private int findClosestShipHorizontally(Coordinates c) {
//        int freeSpots = 0;
//        int row = c.getMyRow();
//        Button buttons[][] = view.getPlayerBoard().getButtons();
//        for (int col = c.getMyCol(); col < 10; ++col) {
//            if (buttons[row][col].getIcon() == null)
//                freeSpots++;
//            else
//                break;
//        }
//        return freeSpots;
//    }
//
//    private int findClosestShipVertically(Coordinates c) {
//        int freeSpots = 0;
//        int col = c.getMyCol();
//        Button buttons[][] = view.getPlayerBoard().getButtons();
//        for (int row = c.getMyRow(); row < 10; ++row) {
//            if (buttons[row][col].getIcon() == null)
//                freeSpots++;
//            else
//                break;
//        }
//        return freeSpots;
//    }
//
//    private ImageIcon[] cutIcon(int pieces) {
//        BufferedImage origImg = generateScaledShip();
//        BufferedImage imgs[] = new BufferedImage[pieces];
//        int pieceW = origImg.getWidth() / pieces;
//        int pieceH = origImg.getHeight();
//
//        for (int i = 0; i < pieces; ++i) {
//            imgs[i] = new BufferedImage(pieceW, pieceH, origImg.getType());
//            // draw the image chunk
//            Graphics2D gr = imgs[i].createGraphics();
//            gr.drawImage(origImg, 0,0, pieceW, pieceH,
//                    pieceW * i, 0, pieceW*i +pieceW, pieceH,
//                    null);
//            gr.dispose();
//        }
//
//        ImageIcon icons[] = new ImageIcon[pieces];
//        for (int i = 0; i < imgs.length; ++i) {
//            // placing vertically -> rotate
//            if (shipDirection == 1) { imgs[i] = rotateImage(imgs[i]); };
//            icons[i] = new ImageIcon(imgs[i]);
//        }
//        return icons;
//    }
//
//    private BufferedImage rotateImage(BufferedImage img) {
//        BufferedImage temp = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
//        Graphics2D gr = (Graphics2D)temp.getGraphics();
//        gr.rotate(Math.toRadians(90), img.getWidth() / 2, img.getHeight() / 2);
//        gr.drawImage(img, 0, 0, null);
//        gr.dispose();
//        return temp;
//
//        /*
//        AffineTransform at = AffineTransform.getTranslateInstance
//                (img.getWidth(), img.getHeight());
//        at.rotate(Math.toRadians(45), img.getWidth()/2,
//                img.getHeight()/2);
//
//        Graphics2D gr = (Graphics2D)img.getGraphics();
//        gr.drawImage(img, at, null);
//        return img;
//        */
//    }
//
//    private String findShipFilepath() {
//        String filepath = "Filepath not found";
//
//        switch (shipSelected) {
//            case 5:
//                filepath = "images/russia-kuznetsov.png";
//                break;
//            case 4:
//                filepath = "images/yamato.png";
//                break;
//            case 3:
//                filepath = "images/destroyer.png";
//                break;
//            case 2:
//                filepath = "images/typhoon.png";
//                break;
//            case 1:
//                filepath = "images/patrol_boat.png";
//                break;
//        }
//        return filepath;
//    }
//
//    private int findShipSize() {
//        // size same as ID
//        if (shipSelected == 5 || shipSelected == 4 || shipSelected == 3)
//            return shipSelected;
//        // size one more that ID
//        else if (shipSelected == 2 || shipSelected == 1)
//            return shipSelected + 1;
//        else
//            return -1;
//    }
//
//    private class shipSelectorHandler implements  ActionListener {
//        public void actionPerformed (ActionEvent event) {
//            // get imagename clicked
//            String shipType  = ((JButton)event.getSource()).getIcon().toString();
//
//            switch (shipType) {
//                case "Aircraft Carrier":
//                    shipSelected = 5;
//                    break;
//                case "Battleship":
//                    shipSelected = 4;
//                    break;
//                case "Destroyer":
//                    shipSelected = 3;
//                    break;
//                case "Submarine":
//                    shipSelected = 2;
//                    break;
//                case "Patrol Boat":
//                    shipSelected = 1;
//                    break;
//            }
//
//            view.removeSelectionShipBorders();
//            view.resetShipSelectionButtonsBorders();
//            if (!shipsOnBoard.contains(shipSelected)) {
//                ((JButton) event.getSource()).setBorder(BorderFactory.
//                        createLineBorder(Color.DARK_GRAY, 4));
//            }
//           // view.setStatusLabel("Place your ships on the grid soldier!");
//
//            System.out.println(shipSelected + " was selected.");
//        }
//    }
//
//    private class hoverHandlerShipSelection extends MouseAdapter{
//        public void mouseEntered(java.awt.event.MouseEvent evt) {
//            view.clearHighlightsFromAllButtons();
//            if (shipDirection == 0){
//                view.highlightHorizontally(findShipSize(),
//                        ((Button)evt.getSource()).getCoordinates());
//            }
//            else if (shipDirection == 1) {
//                view.highlightVertically(findShipSize(),
//                        ((Button)evt.getSource()).getCoordinates());;
//            }
//        }
//
//        public void mouseExited(java.awt.event.MouseEvent evt) {
//            view.clearHighlightsFromAllButtons();
//        }
//    }
//
//    private BufferedImage generateScaledShip() {
//        BufferedImage tempImg = null;
//
//        switch (shipSelected) {
//            case 5:
//                tempImg = view.resize(view.loadImage
//                        (findShipFilepath()), 270, 60);
//                break;
//            case 4:
//                tempImg = view.resize(view.loadImage
//                        (findShipFilepath()), 218, 60);
//                break;
//            case 3:
//                tempImg = view.resize(view.loadImage
//                        (findShipFilepath()), 164, 60);
//                break;
//            case 2:
//                tempImg = view.resize(view.loadImage
//                        (findShipFilepath()), 164, 60);
//                break;
//            case 1:
//                tempImg = view.resize(view.loadImage
//                        (findShipFilepath()), 110, 50);
//                break;
//        }
//        return tempImg;
//    }
//
//    private class placeModeButtonListener implements ActionListener {
//        public void actionPerformed (ActionEvent evt) {
//            if (shipDirection == 1){
//                shipDirection = 0;
//                view.getPlaceModeButton().setText("Placing horizontally");
//            }
//            else if (shipDirection == 0) {
//                shipDirection = 1;
//                view.getPlaceModeButton().setText("Placing vertically");
//            }
//        }
//    }


    
    

}




















