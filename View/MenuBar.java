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


import javax.swing.*;

public class MenuBar extends JMenuBar {

    private JMenu fileMenu, helpMenu;

    protected MenuBar() {
        addFileMenu();
        addHelpMenu();
    }

    private void addFileMenu() {
        fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Set Connection"));
        fileMenu.add(new JMenuItem("Statistics"));
        fileMenu.add(new JMenuItem("Quit"));
        add(fileMenu);
    }

    private void addHelpMenu() {
        helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem("Connection help"));
        helpMenu.add(new JMenuItem("Game help"));
        helpMenu.add(new JMenuItem("About"));
        add(helpMenu);
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    @Override
    public JMenu getHelpMenu() {
        return helpMenu;
    }
}