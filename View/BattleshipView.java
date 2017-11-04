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
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;


public class BattleshipView {

    private JFrame frame;
    private ImagePanel bgPanel;
    private MenuBar menuBar;
    private Button[][] buttons;
    private JLabel statusLabel;

    public BattleshipView() {
        setupFrame();
        setupPanel();
        setupMenuBar();
        setupButtons();
        setupStatusBar();

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
        bgPanel.setMaximumSize(new Dimension(600,600));
        bgPanel.setLayout(new GridLayout(10,10));
        frame.getContentPane().add(bgPanel);
    }

    private void setupMenuBar() {
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);
    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel();
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(Color.white);
        statusBar.setBorder(new BevelBorder(BevelBorder.RAISED));

        statusBar.add(createStatusLabel());
        frame.add(statusBar, BorderLayout.SOUTH);
    }

    private JLabel createStatusLabel() {
        statusLabel = new JLabel("Choose File -> Set Connection to begin the battle...");
        return statusLabel;
    }

    public void setStatusLabel(String text) {
        statusLabel.setText(text);
    }

    private void setupButtons() {
        buttons = new Button[10][10];

        for (int i = 0; i < 10; ++i ) {
            for (int k = 0; k < 10; ++k) {
                buttons[i][k] = new Button(i, k);
                buttons[i][k].setBackground(Color.white);
                //buttons[i][k].setOpacity(0.5);
                buttons[i][k].repaint();
                buttons[i][k].setOpaque(false);
                bgPanel.add(buttons[i][k]);
            }
        }
    }

    public void addMenuBarListeners(ActionListener actionListener) {
        addFileMenuListeners(actionListener);
        addHelpMenuListeners(actionListener);
    }

    private void addHelpMenuListeners(ActionListener actionListener) {
        JMenu temp = menuBar.getHelpMenu();
        for (int i = 0; i < temp.getItemCount(); ++i) {
            temp.getItem(i).addActionListener(actionListener);
        }
    }

    private void addFileMenuListeners(ActionListener actionListener) {
        JMenu temp = menuBar.getFileMenu();
        for (int i = 0; i < temp.getItemCount(); ++i) {
            temp.getItem(i).addActionListener(actionListener);
        }
    }

}































