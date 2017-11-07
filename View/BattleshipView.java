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

import Model.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;


public class BattleshipView {

    private JFrame frame;
    private Board playerBoard;

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    private Board opponentBoard;
    private MenuBar menuBar;
    private JLabel statusLabel;
    private JPanel shipSelectionPanel;
    private JButton shipSelectionButtons[];



    public BattleshipView() {
        setupFrame();
        setupBoards();
        setupMenuBar();
        setupStatusBar();
        setupShipSelectionPanel();
        opponentBoard.setVisible(false);

        frame.setVisible(true);
    }

    public JPanel getShipSelectionPanel() {
        return shipSelectionPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public Icon getShipIcon(int shipID) {
        return shipSelectionButtons[shipID-1].getIcon();
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    private void setupFrame() {
        frame = new JFrame("Networked Battleship");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1420, 680);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void setupBoards() {
        Container mainPanel = new JPanel();
        playerBoard = new Board("You");
        opponentBoard = new Board("Enemy");
        playerBoard.setBgImage("images/water_00.jpg");
        opponentBoard.setBgImage("images/water_02_a.png");
        playerBoard.setMaximumSize(new Dimension(600,600));
        mainPanel.setLayout(new GridLayout(1, 2, 10, 0));
        mainPanel.add(playerBoard);
        mainPanel.add(opponentBoard);
        mainPanel.setBackground(Color.white);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
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

    private void setupShipSelectionPanel() {
        shipSelectionPanel = new JPanel();
        shipSelectionButtons = new JButton[5];
        for (int i = 0; i< 5; ++i) {
            shipSelectionButtons[i] = new JButton();
            shipSelectionPanel.add(shipSelectionButtons[i]);
        }
        shipSelectionPanel.setLayout(new GridLayout(9, 1));
        shipSelectionPanel.setPreferredSize(new Dimension(280, 500));
        shipSelectionPanel.setBackground(Color.white);
        shipSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.white, 8));
        addShips();
        frame.add(shipSelectionPanel, BorderLayout.WEST);
    }

    private void addShips() {
        addAircraft();
        addBattleship();
        addDestroyer();
        addSubmarine();
        addPatrolBoat();
    }

    private void addAircraft() {
        BufferedImage img = resize(loadImage
                ("images/russia-kuznetsov.png"), 260, 60);
        ImageIcon icon = new ImageIcon(img, "Aircraft Carrier");
        shipSelectionButtons[0].setIcon(icon);
    }

    private void addBattleship() {
        BufferedImage img = resize(loadImage
                ("images/yamato.png"), 208, 60);
        ImageIcon icon = new ImageIcon(img, "Battleship");
        shipSelectionButtons[1].setIcon(icon);
    }

    private void addDestroyer() {
        BufferedImage img = resize(loadImage
                ("images/destroyer.png"), 154, 60);
        ImageIcon icon = new ImageIcon(img, "Destroyer");
        shipSelectionButtons[2].setIcon(icon);
    }

    private void addSubmarine() {
        BufferedImage img = resize(loadImage
                ("images/typhoon.png"), 154, 60);
        ImageIcon icon = new ImageIcon(img, "Submarine");
        shipSelectionButtons[3].setIcon(icon);
    }

    private void addPatrolBoat() {
        BufferedImage img = resize(loadImage
                ("images/patrol_boat.png"), 102, 50);
        ImageIcon icon = new ImageIcon(img, "Patrol Boat");
        shipSelectionButtons[4].setIcon(icon);
    }

    public BufferedImage loadImage (String filepath) {
        BufferedImage img = null;
        try {
            File f = new File(filepath);
            FileInputStream fs = new FileInputStream(f);
            img = ImageIO.read(fs);
        } catch (Exception ex) {
            System.out.println("Loading image error");
            System.out.println(ex);
        }

        return img;
    }

    public BufferedImage resize(BufferedImage img, int width, int height) {

        Image scaledImg = img.getScaledInstance
                (width, height, Image.SCALE_DEFAULT);


        BufferedImage tempImage = new BufferedImage(scaledImg.getWidth(null),
                scaledImg.getHeight(null), BufferedImage.TYPE_INT_ARGB );

        // draw the image
        Graphics2D temp = tempImage.createGraphics();
        temp.drawImage(scaledImg, 0, 0, null);
        temp.dispose();

        return tempImage;
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

    // FIXME: might update to seperate handlers for player / opponent
    public void addButtonsListener(ActionListener actionListener) {
        addButtonsListenerPlayer(actionListener);
        addButtonsListenerOpponent(actionListener);
    }

    private void addButtonsListenerPlayer(ActionListener al) {
        for (Button buttons[]: playerBoard.getButtons())
            for (Button b: buttons)
                b.addActionListener(al);
    }

    private void addButtonsListenerOpponent(ActionListener al) {
        for (Button buttons[]: opponentBoard.getButtons())
            for (Button b: buttons)
                b.addActionListener(al);
    }

    public void addShipSelectionListener(ActionListener actionListener) {
        for (JButton b : shipSelectionButtons)
            b.addActionListener(actionListener);
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

    public void setCursor(String filepath) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage(filepath);
        Cursor newCursor = toolkit.createCustomCursor(cursorImage,
                new Point(10,10), "Aircraft");
        frame.setCursor(newCursor);
    }

    /*
    // FIXME: add array with cutted image as parameter, loop thru buttons to add
    // direction: 0 - horizontal, 1 - vertical
    public void placeShip(ImageIcon shipIcons[], Coordinates c, int shipDir) {
        if (shipDir == 0)
            placeShipHorizontally(shipIcons, c);
        else
            placeShipVertically(shipIcons, c);
    }
    */

    public void placeShipHorizontally(ImageIcon shipIcons[], Coordinates c) {
        int row = c.getMyRow();
        int col = c.getMyCol();
        for (int i = 0; i < shipIcons.length; ++i) {
            playerBoard.getButtons()[row][col++].setIcon(shipIcons[i]);
            //opponentBoard.getButtons()[row][col++].setIcon(shipIcons[i]);
        }
    }

    public void placeShipVertically(Icon shipIcons[], Coordinates c) {
        int row = c.getMyRow();
        int col = c.getMyCol();
        for (Icon i : shipIcons) {
            playerBoard.getButtons()[row++][col].setIcon(i);
        }
    }


}































