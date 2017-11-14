//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// MenuBar.java
//

//
//  class description...
//

package View;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.Map;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu fileMenu, helpMenu;
	private Map<String, JMenuItem> jMenuItems;

	// Default constructor:
    
	public MenuBar() {
		jMenuItems = new HashMap<String, JMenuItem>();
        addFileMenu();
        addHelpMenu();
    }

    // Getter functions:
		
    private void addFileMenu() {
    		fileMenu = new JMenu("File");
    		jMenuItems.put("CreateHost", new JMenuItem("Create Host"));
    		fileMenu.add(jMenuItems.get("CreateHost"));
    		jMenuItems.put("ConnectToHost", new JMenuItem("Connect to Host"));
    		fileMenu.add(jMenuItems.get("ConnectToHost"));
    		jMenuItems.put("Statistics", new JMenuItem("Statistics"));
    		fileMenu.add(jMenuItems.get("Statistics"));
    		jMenuItems.put("Quit", new JMenuItem("Quit"));
    		fileMenu.add(jMenuItems.get("Quit"));
        add(fileMenu);
    }

    private void addHelpMenu() {
        helpMenu = new JMenu("Help");
        jMenuItems.put("ConnectionHelp", new JMenuItem("Connection Help"));
        helpMenu.add(jMenuItems.get("ConnectionHelp"));
        jMenuItems.put("GameRules", new JMenuItem("Game Rules"));
        helpMenu.add(jMenuItems.get("GameRules"));
        jMenuItems.put("About", new JMenuItem("About"));
        helpMenu.add(jMenuItems.get("About"));
        add(helpMenu);
    }

    
    public void addMenuItemListener(String menuItem, ActionListener actionListener) {
    		jMenuItems.get(menuItem).addActionListener(actionListener);
    }

}