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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;


public class BattleshipView {

    private JFrame frame;
    private BoardView playerBoard;
    private BoardView opponentBoard;
//    private MenuBar menuBar;
    private JLabel statusLabel;
    private JPanel shipSelectionPanel;
    private JButton placeModeButton;
    private JButton shipSelectionButtons[];
    private Color myColor = Color.white;


    public BattleshipView() {
        setupFrame();
        setupBoards();
        setupStatusBar();
        setupShipSelectionPanel();
        //opponentBoard.setVisible(false);
        addInfoBar();

        frame.setVisible(true);
    }


    public JButton[] getShipSelectionButtons() {
        return shipSelectionButtons;
    }

    public BoardView getOpponentBoard() {
        return opponentBoard;
    }

    public JButton getPlaceModeButton() {
        return placeModeButton;
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

    public BoardView getPlayerBoard() {
        return playerBoard;
    }

    private void setupFrame() {
        frame = new JFrame("Networked Battleship");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1400, 700);
        frame.setLocationRelativeTo(null);
        frame.setBackground(myColor);
        //frame.setResizable(false);
    }

    private void setupBoards() {
        JPanel mainPanel = new JPanel();
        playerBoard = new BoardView("You");
        opponentBoard = new BoardView("Enemy");
        playerBoard.setBgImage("images/water_02_a.png");
        opponentBoard.setBgImage("images/water_02_b.png");
        playerBoard.setMaximumSize(new Dimension(600,600));
        mainPanel.setLayout(new GridLayout(1, 2, 0, 0));
        mainPanel.add(playerBoard);
        mainPanel.add(opponentBoard);
        mainPanel.setOpaque(false);
        mainPanel.setBackground(myColor);
        setBoardCursor("images/skull_02_cursor_orange.png");
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

//    private void setupMenuBar() {
//        menuBar = new MenuBar();
//        frame.setJMenuBar(menuBar);
//    }

    private void setupStatusBar() {
        JPanel statusBar = new JPanel();
        statusLabel = new JLabel();
        statusLabel.setText("Choose File -> Set Connection to begin the battle...");
        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(myColor);
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
        shipSelectionPanel.setLayout(new GridLayout(6, 1));
        shipSelectionPanel.setPreferredSize(new Dimension(280, 500));
        shipSelectionPanel.setBackground(myColor);
        shipSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.white, 8));
        shipSelectionPanel.setOpaque(false);
        addShips();
        addPlaceModeButton();
        frame.add(shipSelectionPanel, BorderLayout.WEST);
    }

    private void addInfoBar() {
        JPanel infoBar = new JPanel();
        JLabel label = new JLabel();
        label.setText("                                       Your Board                                              Opponent Board");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        infoBar.add(label);
        infoBar.setBackground(myColor);
        infoBar.setSize(1400, 50);
        infoBar.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        //infoBar.setOpaque(false);
        frame.add(infoBar, BorderLayout.NORTH);
    }

    public void resetShipSelectionButtonsBorders() {
        for (JButton b : shipSelectionButtons)
            b.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
    }

    private void addPlaceModeButton() {
        placeModeButton = new JButton("Placing horizontally");
        placeModeButton.setFont(new Font("Arial", Font.BOLD, 17));
        placeModeButton.setBackground(Color.orange);
        shipSelectionPanel.add(placeModeButton);
    }

    public void addPlaceModeButtonListener(ActionListener al) {
        placeModeButton.addActionListener(al);
    }

    private void addShips() {
        addAircraft();
        addBattleship();
        addDestroyer();
        addSubmarine();
        addPatrolBoat();
        for (JButton b : shipSelectionButtons)
            b.setBackground(myColor);
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

   

    // FIXME: might update to seperate handlers for player / opponent
    public void addButtonsListener(ActionListener actionListener) {
        addButtonsListenerPlayer(actionListener);
        //addButtonsListenerOpponent(actionListener);
    }

    private void addButtonsListenerPlayer(ActionListener al) {
        for (Button buttons[]: playerBoard.getButtons()){
            for (Button b: buttons) {
                b.addActionListener(al);
            }
        }
    }

    public void addMouseListener(MouseAdapter ma){
        for (Button buttons[]: playerBoard.getButtons()){
            for (Button b: buttons) {
                b.addMouseListener(ma);
            }
        }
    }

    public void removeHoverListener() {
    for (Button buttons[]: playerBoard.getButtons()){
        for (Button b: buttons) {
            MouseListener[] ml = b.getMouseListeners();
            for (MouseListener x : ml)
                b.removeMouseListener(x);
        }
    }
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

   

    public void setBoardCursor(String filepath) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage(filepath);
        Cursor newCursor = toolkit.createCustomCursor(cursorImage,
                new Point(10,10), "Aircraft");
        playerBoard.setCursor(newCursor);
        opponentBoard.setCursor(newCursor);
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

    public void removeSelectionShipBorders() {
        for (JButton b : shipSelectionButtons)
            b.setBorder( BorderFactory.
                    createEmptyBorder());
    }

    public void highlightHorizontally(int shipSize, Coordinates c) {
        if ((10 - c.getMyCol()) >= shipSize) {
            int row = c.getMyRow();
            for (int col = c.getMyCol(); col < c.getMyCol() + shipSize; ++col) {
                highlighButton(new Coordinates(row, col));
            }
        }
    }

    public void highlightVertically(int shipSize, Coordinates c) {
        if ((10 - c.getMyRow()) >= shipSize) {
            int col = c.getMyCol();
            for (int row = c.getMyRow(); row < c.getMyRow() + shipSize; ++row) {
                highlighButton(new Coordinates(row, col));
            }
        }
    }

    public void highlighButton(Coordinates c) {
        playerBoard.getButtons()[c.getMyRow()][c.getMyCol()].
                setBorder(BorderFactory.createLineBorder
                        (Color.ORANGE, 2));
    }

    public void clearHighlightsFromAllButtons() {
        for (Button row[] : playerBoard.getButtons())
            for (Button b : row )
                b.setBorder(BorderFactory.createLineBorder
                        (Color.GRAY, 1));
    }

    public void removeHighlightFromButton(Coordinates c) {
        playerBoard.getButtons()[c.getMyRow()][c.getMyCol()].
                setBorder(BorderFactory.createLineBorder
                        (Color.GRAY, 1));
    }



}   // end of BattleshipView class































