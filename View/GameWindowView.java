package View;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameWindowView {
	
	private JFrame gameWindow;
	
	// Default Constructor:
	public GameWindowView() {
		this.gameWindow = new JFrame("Networked Battleship");

		initialize();
	}
	
	// Getter methods:
	
	public JFrame getGameWindow() {
		return this.gameWindow;
	}
	
	// Class methods:
	
	private void initialize() {
		this.gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.gameWindow.setLayout(null);
		this.gameWindow.setSize(600, 200);
		this.gameWindow.setLocationRelativeTo(null);
		this.gameWindow.setResizable(false);
	}
	
	public void addContent(Component component) {
		this.gameWindow.getContentPane().add(component);
		this.gameWindow.revalidate();
		this.gameWindow.repaint();
	}
	
	public void addMenuBar(MenuBarView menuBarView) {
		this.gameWindow.setJMenuBar(menuBarView);
	}
	
	public void setNewSize(int width, int height) {
		this.gameWindow.setSize(width, height);
		this.gameWindow.setLocationRelativeTo(null);
		this.gameWindow.revalidate();
		this.gameWindow.repaint();
	}
	
	public void showMainWindow() {
		this.gameWindow.setVisible(true);
	}
	
	public void setDefault() {
		this.gameWindow.getContentPane().removeAll();
		this.setNewSize(600, 200);
	}

}