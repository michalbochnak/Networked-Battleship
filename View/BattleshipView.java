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
    private Board playerBoard;
    private Board opponentBoard;
    private MenuBar menuBar;
    private JLabel statusLabel;

    public BattleshipView() {
        setupFrame();
        setupBoards();
        setupMenuBar();
        setupStatusBar();

        frame.setVisible(true);
    }

    private void setupFrame() {
        frame = new JFrame("Networked Battleship");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1220, 680);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void setupBoards() {
        Container mainPanel = new JPanel();
        playerBoard = new Board("You");
        opponentBoard = new Board("Enemy");
        playerBoard.setMaximumSize(new Dimension(600,600));
        mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
        mainPanel.add(playerBoard);
        mainPanel.add(opponentBoard);
        mainPanel.setBackground(Color.white);
        frame.getContentPane().add(mainPanel);
    }

    private void setupMenuBar() {
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);
    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setText("Choose File -> Set Connection to begin the battle...");
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(Color.white);
        statusBar.setBorder(new BevelBorder(BevelBorder.RAISED));

        statusBar.add(statusLabel);
        frame.add(statusBar, BorderLayout.SOUTH);
    }

    public void setStatusLabel(String text) {
        statusLabel.setText(text);
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

    public void displayAboutDialog() {
        String message = "Authors:\n\n" +
                "Michal Bochnak\nNetid: mbochn2\n\n" +
                "Alex Viznytsya\nNetid: avizny2\n\n" +
                "Jakub Glebocki\nNetid: jglebo2\n\n\n" +
                "CS 342 Project #4 - Networked battlefield\n" +
                "Nov 16, 2017";
        String title = "About";
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    public void displayGameHelpDialog() {
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
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
    }

}































