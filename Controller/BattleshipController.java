//
// Michal Bochnak, Netid: mbochn2
// Alex Viznytsya, Netid: avizny2
// Jakub Glebocki: Netid: jglebo2
//
// CS 342 Project #4 - Networked Battleship
// Nov 16, 2017
// UIC, Pat Troy
//
// BattleshipController.java
//

//
//  class description...
//

package Controller;

import Model.BattleshipModel;
import Model.Coordinates;
import View.BattleshipView;
import View.Button;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;


public class BattleshipController {

    private BattleshipView view;
    private BattleshipModel model;
    // 0 - 2: 0 - connection, 1 - ship placement, 2 - game
    private int gameStage;
    // 5 - 1: 5 - aircraft, 4 - battleship, 3 - destroyer, 2 - submarine, 1 - patrol boat
    private int shipSelected;
    // 0 - horizontal, 1 - vertical
    private int shipDirection;
    private Set shipsOnBoard;


    public BattleshipController() {
        view = new BattleshipView();
        model = new BattleshipModel();
        gameStage = 1;
        shipDirection = 0;
        shipSelected = -1;
        shipsOnBoard = new HashSet<Integer>();

        view.addMenuBarListeners(new fileMenuHandler());
        view.addButtonsListener(new buttonHandler());
        view.addMouseListener(new mouseHoveredHandler());
        view.addShipSelectionListener(new shipSelectorHandler());
        view.addPlaceModeButtonListener(new placeModeButtonListener());
    }


//FIXME: implement specific handlers
    private class fileMenuHandler implements  ActionListener {
        @Override
        public void actionPerformed (ActionEvent event) {
            if (event.getActionCommand().equals("Set Connection")) {
                System.out.println("set connection");
                // toggle game mode if connection established
            } else if (event.getActionCommand().equals("Statistics")) {
                System.out.println("Statistics");
            } else if (event.getActionCommand().equals("Quit")) {
                java.lang.System.exit(0);
            } else if (event.getActionCommand().equals("Connection help")) {
                System.out.println("connection help");
            } else if (event.getActionCommand().equals("Game help")) {
                view.displayGameHelpDialog();
            } else if (event.getActionCommand().equals("About")) {
                System.out.println("Here");
                view.displayAboutDialog();
            }
        }
    }

    //FIXME: Implement specific action handlers
    private class buttonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            // Connection stage
            if (gameStage == 0) {
                // ignore click
                System.out.println("Click ignored...");
            }
            // Ship placement stage
            else if (gameStage == 1 && !shipsOnBoard.contains(shipSelected)
                    && shipSelected != -1) {
                tryToPlaceShip(event);
                if (shipsOnBoard.size() == 5) {
                    view.getOpponentBoard().setVisible(true);
                }
            }
            // Game stage
            else if (gameStage == 2){
                System.out.println("Game in progress");
            }
        }
    }

    private void tryToPlaceShip(ActionEvent ev) {
        Coordinates c = ((Button) ev.getSource()).getCoordinates();
        if (shipDirection == 0) {       // horizontal
            if (enoughSpaceForShip(c)) {
                ImageIcon shipPieces[] = cutIcon(findShipSize());
                view.placeShipHorizontally(shipPieces, c);
                shipsOnBoard.add(shipSelected);
            }
        }
        else  {             // vertical
            if (enoughSpaceForShip(c)) {
                ImageIcon shipPieces[] = cutIcon(findShipSize());
                // FIXME:  twist images 90 degrees
                view.placeShipVertically(shipPieces, c);
                shipsOnBoard.add(shipSelected);
            }
        }
    }

    private boolean enoughSpaceForShip(Coordinates c) {
        return fitsOnBoard(c) && doNotCollideWithOtherShip(c);
    }

    private boolean fitsOnBoard(Coordinates c) {
        int bound = ((shipDirection == 0) ? c.getMyCol() : c.getMyRow());
        return (9 - bound) + 1 >= findShipSize();
    }

    private boolean doNotCollideWithOtherShip(Coordinates c) {
        if (shipDirection == 0) { // horizontal
            return findClosestShipHorizontally(c) >= findShipSize();
        }
        else {      // vertical
            return findClosestShipVertically(c) >= findShipSize();
        }
    }

    private int findClosestShipHorizontally(Coordinates c) {
        int freeSpots = 0;
        int row = c.getMyRow();
        Button buttons[][] = view.getPlayerBoard().getButtons();
        for (int col = c.getMyCol(); col < 10; ++col) {
            if (buttons[row][col].getIcon() == null)
                freeSpots++;
            else
                break;
        }
        return freeSpots;
    }

    private int findClosestShipVertically(Coordinates c) {
        int freeSpots = 0;
        int col = c.getMyCol();
        Button buttons[][] = view.getPlayerBoard().getButtons();
        for (int row = c.getMyRow(); row < 10; ++row) {
            if (buttons[row][col].getIcon() == null)
                freeSpots++;
            else
                break;
        }
        return freeSpots;
    }

    private ImageIcon[] cutIcon(int columns) {
        BufferedImage origImg = generateScaledShip();
        BufferedImage img[] = new BufferedImage[columns];
        int pieceW = origImg.getWidth() / columns;
        int pieceH = origImg.getHeight();

        for (int i = 0; i < columns; ++i) {
            img[i] = new BufferedImage(pieceW, pieceH, origImg.getType());
            // draw the image chunk
            Graphics2D gr = img[i].createGraphics();
            gr.drawImage(origImg, 0,0, pieceW, pieceH,
                    pieceW * i, 0, pieceW*i +pieceW, pieceH,
                    null);
            gr.dispose();
        }

        ImageIcon icon[] = new ImageIcon[columns];
        for (int i = 0; i < img.length; ++i) {
            icon[i] = new ImageIcon(img[i]);
        }

        return icon;
    }

    private String findShipFilepath() {
        String filepath = "Filepath not found";

        switch (shipSelected) {
            case 5:
                filepath = "images/russia-kuznetsov.png";
                break;
            case 4:
                filepath = "images/yamato.png";
                break;
            case 3:
                filepath = "images/destroyer.png";
                break;
            case 2:
                filepath = "images/typhoon.png";
                break;
            case 1:
                filepath = "images/patrol_boat.png";
                break;
        }
        return filepath;
    }

    private int findShipSize() {
        // size same as ID
        if (shipSelected == 5 || shipSelected == 4 || shipSelected == 3)
            return shipSelected;
        // size one more that ID
        else if (shipSelected == 2 || shipSelected == 1)
            return shipSelected + 1;
        else
            return -1;
    }

    private class shipSelectorHandler implements  ActionListener {
        public void actionPerformed (ActionEvent event) {
            // get imagename clicked
            String shipType  = ((JButton)event.getSource()).getIcon().toString();

            switch (shipType) {
                case "Aircraft Carrier":
                    shipSelected = 5;
                    break;
                case "Battleship":
                    shipSelected = 4;
                    break;
                case "Destroyer":
                    shipSelected = 3;
                    break;
                case "Submarine":
                    shipSelected = 2;
                    break;
                case "Patrol Boat":
                    shipSelected = 1;
                    break;
            }

            view.removeSelectionShipBorders();
            ((JButton) event.getSource()).setBorder( BorderFactory.
                    createLineBorder(Color.orange, 3));
            view.setPlayerBoardCursor("images/skull_02_cursor.png");
           // view.setStatusLabel("Place your ships on the grid soldier!");

            System.out.println(shipSelected + " was selected.");
        }
    }

    private class mouseHoveredHandler extends MouseAdapter{
        public void mouseEntered(java.awt.event.MouseEvent evt) {

            view.clearHighlightsFromAllButtons();

            if (shipDirection == 0){
                view.highllightHorizontally(findShipSize(),
                        ((Button)evt.getSource()).getCoordinates());
            }
            else if (shipDirection == 1) {
                view.highlightVertically(findShipSize(),
                        ((Button)evt.getSource()).getCoordinates());;
            }
        }
    }

    private BufferedImage generateScaledShip() {
        BufferedImage tempImg = null;

        switch (shipSelected) {
            case 5:
                tempImg = view.resize(view.loadImage
                        (findShipFilepath()), 270, 60);
                break;
            case 4:
                tempImg = view.resize(view.loadImage
                        (findShipFilepath()), 218, 60);
                break;
            case 3:
                tempImg = view.resize(view.loadImage
                        (findShipFilepath()), 164, 60);
                break;
            case 2:
                tempImg = view.resize(view.loadImage
                        (findShipFilepath()), 164, 60);
                break;
            case 1:
                tempImg = view.resize(view.loadImage
                        (findShipFilepath()), 110, 50);
                break;
        }
        return tempImg;
    }

    private class placeModeButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent evt) {
            if (shipDirection == 1){
                shipDirection = 0;
                view.getPlaceModeButton().setText("Placing horizontally");
            }
            else if (shipDirection == 0) {
                shipDirection = 1;
                view.getPlaceModeButton().setText("Placing vertically");
            }
        }
    }



}




















