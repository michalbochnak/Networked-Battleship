/* MenuBarController.java
 *
 * Michal Bochnak, Netid: mbochn2
 * Alex Viznytsya, Netid: avizny2
 * Jakub Glebocki, Netid: jglebo2
 *
 * CS 342 Project #4 - Networked Battleship
 * Nov 16, 2017
 * UIC, Pat Troy
 *
 * Class description:
 * 
 * This class sets and controls behavior all menu items.
 * 
 */

package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import javax.swing.JFrame;

import View.*;
import Model.Network;

class MenuBarController {

	private MenuBar menuBar;
	
	// Default constructor:
	public MenuBarController(JFrame mwJFrame) {
		menuBar = new MenuBar();
		mwJFrame.setJMenuBar(menuBar);
		addMenuListeners();
	}
	
	private void addMenuListeners() {
		menuBar.addMenuItemListener("CreateHost", new CreateHost());
		menuBar.addMenuItemListener("ConnectToHost", new ConnectToHost());
		menuBar.addMenuItemListener("Quit", new QuitMenu());
	}
	
	class CreateHost implements ActionListener {
		
		int socketPort;
		ServerSocket networkSocket;
		Network networkModel;
		
		private CreateHostWindow connectionWindow;
		
		public CreateHost() {
			networkModel = new Network();
			networkSocket = null;
			socketPort = 0;
			
			connectionWindow = new CreateHostWindow();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
	            InetAddress ipAddress = InetAddress.getLocalHost();
	            connectionWindow.setIPAddress(ipAddress.getHostAddress());
	        } catch (UnknownHostException uhe) {
	        		uhe.printStackTrace();
	        }
			
			connectionWindow.setVisible(true);
			connectionWindow.addCreateHostButttonEventListener(new ActionListener() {
			
				
				

				@Override
				public void actionPerformed(ActionEvent e) {
					if(connectionWindow.isRandomBoxChecked() == false) {
						socketPort = Integer.parseInt(connectionWindow.getPortField());
					}
					try {
						networkSocket = networkModel.createSocket();
						networkSocket.close();
						
						
					} catch (IOException ioe) {
						
					}
					
					connectionWindow.setConnectionIncon(true);
					connectionWindow.setConnectionStatus("Connected");
				}
				
			});
		}
	}
	
	class ConnectToHost implements ActionListener {
		
		private ConnectToHostWindow connectionWindow;
		
		public ConnectToHost() {
			connectionWindow = new ConnectToHostWindow();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			connectionWindow.setVisible(true);
		}
	}
	
	class QuitMenu implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
}
