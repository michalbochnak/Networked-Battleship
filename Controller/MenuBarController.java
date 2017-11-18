//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// MenuBarController.java
//

//
// This class sets and controls behavior all menu items.
//


package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import Model.NetworkDataModel;
import View.*;


class MenuBarController {
	
	private MenuBarView menuBarView;
	private GameController gameController;
	private NetworkDataModel txData;
	private JFrame mainWindow;

	//
	// Default constructor:
	//
	public MenuBarController(GameController gameController) {
		
		this.gameController = gameController;
		this.menuBarView = new MenuBarView();
		this.initialize();
	}
	
	//
	// Getter methods:
	//
	public MenuBarView getMenuBar() {
		return this.menuBarView;
	}

	//
	// Class methods:
	//
	private void initialize() {
		
		this.mainWindow = this.gameController.getMainWindow();
		
		this.menuBarView.addMenuItemListener("CreateHost", new CreateHost());
		this.menuBarView.addMenuItemListener("JoinHost", new JoinHost());
		this.menuBarView.addMenuItemListener("Quit", new QuitMenu());
		this.menuBarView.addMenuItemListener("About", new AboutMenu());
		this.menuBarView.addMenuItemListener
				("GameRules", new GameRulesMenu());
		this.menuBarView.addMenuItemListener("Statistics", new StatisticsMenu());
		this.menuBarView.addMenuItemListener
				("Connection", new ConnectionMenu());
	
		
	}
		
	class CreateHost implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(gameController.getNetworkConnection() != null) {
				txData = new NetworkDataModel();
				txData.setDisconectSignal(true);
				gameController.getNetworkConnection().sendData(txData);
				gameController.exitFromStartedGame();
				gameController.getNetworkConnection().closeConnection();
				gameController.setDefaultMenuWindow();
				gameController.startGame(3);
			}
		}
	}
	
	class JoinHost implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(gameController.getNetworkConnection() != null) {
				txData = new NetworkDataModel();
				txData.setDisconectSignal(true);
				gameController.getNetworkConnection().sendData(txData);
				gameController.getNetworkConnection().closeConnection();
				gameController.setDefaultMenuWindow();
				gameController.startGame(4);
			}
		}
	}
	
	class QuitMenu implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(gameController.getNetworkConnection() != null) {
				if(gameController.isGameStarted()) {
					txData = new NetworkDataModel();
					txData.setDisconectSignal(true);
					gameController.getNetworkConnection().sendData(txData);
					gameController.getNetworkConnection().closeConnection();
				}
			}
			System.exit(0);
		}
	}
	
	class AboutMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = "Authors:\n\n" +
	                "Michal Bochnak\nNetid: mbochn2\n\n" +
	                "Alex Viznytsya\nNetid: avizny2\n\n" +
	                "Jakub Glebocki\nNetid: jglebo2\n\n\n" +
	                "CS 342 Project #4 - Networked battlefield\n" +
	                "Nov 16, 2017";
	        String title = "About";
	        JOptionPane.showMessageDialog(mainWindow,
					message, title, JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	class GameRulesMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			 String message = "Two player game. The goal is to sunk all the\n" +
		                "ships of the opponent player!\n\n" +
		                "Board on  the left presents your board\n" +
		                "and opponent's tries to hit your ships.\n" +
		                "Board on the right presents your tries\n" +
		                "and hits on the opponent's ships.\n\n" +
		                "Rules:\n" +
		                "1. Both players arrange their ships on the board.\n" +
		                "2. Players take turns by choosing the square\n" +
		                "on the board.\n" +
		                "3. Player who first sunk all the opponent's\n" +
		                "ships is the winner.\n\n" +
		                "Good Luck!";
		        String title = "Game help";
	        JOptionPane.showMessageDialog(mainWindow, message,
					title, JOptionPane.PLAIN_MESSAGE);
		}
	}

	class StatisticsMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int hits = gameController.getGameboardController().getHits();
			int misses = gameController.getGameboardController().getMisses();
			int accuracy = (int)(((double)hits / (hits + misses)) * 100);

			String message = "Player " + gameController.getPlayerName()
					+ ":\n" + "Hits: " + hits + "\n"
					+ "Misses: " + misses+ "\n"
					+ "Accuracy: " +accuracy + "%\n\n";

			String title = "Statistics";
			JOptionPane.showMessageDialog(mainWindow, message,
					title, JOptionPane.PLAIN_MESSAGE);
		}
	}

	class ConnectionMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = "To create a host or join to the existing network\n" +
					"please fallow the menu options. To create a new host you can\n" +
					"specify your port of choice or use random port by clicking the checkbox\n" +
					"that will open first available port on your computer.\n" +
					"To join to the existing host, please provide the same IP address\n" +
					"and port as specified by the host creator.";
			String title = "Connection";
			JOptionPane.showMessageDialog(mainWindow, message,
					title, JOptionPane.PLAIN_MESSAGE);
		}
	}
}
