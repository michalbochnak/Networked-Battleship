package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.SelectGameModeView;
import View.WelcomeMessageView;




public class GameMenuController {
	
	private WelcomeMessageView welcomeMessageView;
	private SelectGameModeView selectGameModeView;
	
	private GameController gameController;
	
	public GameMenuController(GameController gameController) {
		
		this.gameController = gameController;
		
		this.welcomeMessageView = new WelcomeMessageView();
		this.selectGameModeView = new SelectGameModeView();
		
		this.initialize();
	}
	
	// Getter methods:
	
	// Setter methods:
	
	// Class methods:
	
	private void initialize() {
		this.welcomeMessageView.addSubmitButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (welcomeMessageView.getPlayerName().length() > 0) {
					gameController.setPlayerName(welcomeMessageView.getPlayerName());
					gameController.startGame(2);
				} else {
					welcomeMessageView.setPlayerName("Enter your name here ...");
				}
			}
		});
		
		this.selectGameModeView.addCreateHostButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.setGameMode(0);
				gameController.startGame(3);
			}
		});
		
		this.selectGameModeView.addJoinHostButtonActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.setGameMode(1);
				gameController.startGame(4);
			}
		});
	}
	
	public WelcomeMessageView getWelcomeMessageView() {
		return this.welcomeMessageView.getView();
	}
	
	public SelectGameModeView getSelectGameModeView() {
		return this.selectGameModeView.getView();
	}
}
