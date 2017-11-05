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
import View.BattleshipView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleshipController {

    private BattleshipView view;
    private BattleshipModel model;

    public BattleshipController() {
        view = new BattleshipView();
        model = new BattleshipModel();

        view.addMenuBarListeners(new fileMenuHandler());
    }



//FIXME: implement specific handlers
    private class fileMenuHandler implements  ActionListener {
        @Override
        public void actionPerformed (ActionEvent event) {
            if (event.getActionCommand().equals("Set Connection")) {
                System.out.println("set connection");
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





}




















