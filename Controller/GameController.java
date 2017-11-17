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
//  This class switches between game modes. There is a switch statement which
// sets it to either true or false
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
	
  
    private String playerName;
    private String opponentName;
    private int gameMode;			// 0 - server, 1 - client
    
    
    
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
   		
    		this.serverModeController = null;
    		this.clientModeController = null;
    		this.gameMenuController = new GameMenuController(this);
    		this.menuBarController = new MenuBarController(this);
    		this.gameboardController = null;
    		
    		
    		this.playerName = null;
    		this.opponentName = null;
    		this.gameMode = 0;
    		
    	
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
    		this.networkConnection = networkConnection;
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
    				
    				this.serverModeController = new ServerModeController(this);
    				this.gameWindowView.addContent(this.serverModeController.getView());
    				break;
    			case 4:
    				this.gameMode = 1;
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(false);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(false);
    				
    				this.clientModeController = new ClientModeController(this);
    				this.gameWindowView.addContent(this.clientModeController.getView());
    				break;
    			case 5:
    				this.menuBarController.getMenuBar().getMenuItems().get("CreateHost").setEnabled(true);
    				this.menuBarController.getMenuBar().getMenuItems().get("JoinHost").setEnabled(true);
    				this.gameWindowView.setNewSize(1400, 700);
    				
    				this.gameboardController = new GameboardController(this);
    				this.gameboardController.setPlayerNames();
    				
    				this.gameWindowView.addContent(this.gameboardController.getGameboardView());
    				break;
    		}
 
    }

    public void setDefaultMenuWindow() {
    		this.gameWindowView.setDefault();
//    		this.serverModeController.setDefault();
//    		this.clientModeController.setDefault();
//    		this.gameboardController = new GameboardController(this);
    		
    }
    
    public void exitFromStartedGame() {
    		if(this.gameboardController != null) {
    			this.gameboardController.setExitFlag(true);
    		}
    }
    
    public boolean isGameStarted() {
    		if(this.gameboardController != null) {
    			return true;
    		} else {
    			return false;
    		}
    }

}




















