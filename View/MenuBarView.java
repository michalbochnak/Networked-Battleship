//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// MenuBarView.java
//

//
//  MenuBarView class is a class in which we display the contents of the menubar,
// adding file menu and submenu's for user interference
//

package View;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.util.Map;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MenuBarView extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu fileMenu, helpMenu;
	private Map<String, JMenuItem> menuItems;

	// Default constructor:
    
	public MenuBarView() {
		menuItems = new HashMap<String, JMenuItem>();
        addFileMenu();
        addHelpMenu();
    }

    // Getter functions:
	
	public JMenuBar getMenuBar() {
		return this;
	}
	
	public Map<String, JMenuItem> getMenuItems() {
		return this.menuItems;
	}
	
	// Class functions:
	
    private void addFileMenu() {
    		fileMenu = new JMenu("File");
    		menuItems.put("CreateHost", new JMenuItem("Create Host"));
    		fileMenu.add(menuItems.get("CreateHost"));
    		menuItems.put("JoinHost", new JMenuItem("Join Host"));
    		fileMenu.add(menuItems.get("JoinHost"));
//    		menuItems.put("Statistics", new JMenuItem("Statistics"));
//    		fileMenu.add(menuItems.get("Statistics"));
    		menuItems.put("Quit", new JMenuItem("Quit"));
    		fileMenu.add(menuItems.get("Quit"));
        add(fileMenu);
    }

    private void addHelpMenu() {
        helpMenu = new JMenu("Help");
        menuItems.put("ConnectionHelp", new JMenuItem("Connection Help"));
        helpMenu.add(menuItems.get("ConnectionHelp"));
        menuItems.put("GameRules", new JMenuItem("Game Rules"));
        helpMenu.add(menuItems.get("GameRules"));
        menuItems.put("About", new JMenuItem("About"));
        helpMenu.add(menuItems.get("About"));
        add(helpMenu);
    }

    
    public void addMenuItemListener(String menuItem, ActionListener actionListener) {
    		menuItems.get(menuItem).addActionListener(actionListener);
    }

}