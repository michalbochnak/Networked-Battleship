//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// BattleshipView.java
//

//
//  class description...
//

package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class BattleshipView {

    private JFrame frame;
    private ImagePanel bgPanel;
    private MenuBar menuBar;

    public BattleshipView() {
        setupFrame();
        setupPanel();
        setupMenu();

        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame("Networked Battleship");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void setupPanel() {
        bgPanel = new ImagePanel();
        bgPanel.setLayout(new GridLayout(10,10,2, 2));
        frame.getContentPane().add(bgPanel);
    }

    private void setupMenu() {
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);
    }

    public void addMenuListener(ActionListener actionListener) {
        addFileMenuListeners(actionListener);
        addHelpMenuListeners(actionListener);
    }

    public void addFileMenuListeners() {

    }

    private void addHelpMenuListeners(ActionListener actionListener) {
        JMenu temp = menuBar.getHelpMenu();
        for (int i = 0; i < temp.getItemCount(); ++i) {
            temp.getItem(i).addActionListener(actionListener);
        }
    }

    // FIXME: Implement
    private void addFileMenuListeners(ActionListener actionListener) {

    }

}
