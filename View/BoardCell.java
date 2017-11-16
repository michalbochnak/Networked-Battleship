package View;

import java.awt.Dimension;

import javax.swing.JLabel;

import Model.Coordinates;

public class BoardCell extends JLabel {

	private static final long serialVersionUID = 1L;
    private Coordinates coordinates;
    private double opacity;
	
	// Default constructor:
	
	public BoardCell(int row, int col) {
		
		this.coordinates = new Coordinates(row, col);
		this.opacity = 0.0;
		
		this.initialize();
	}
			
	// Getter methods:
	
	public  Coordinates getCoordinates() {
        return this.coordinates;
    }

    protected double getOpacity() {
        return this.opacity;
    }
				
	// Setter methods:
	
	public void setOpacity(double opacity) {
        this.opacity = opacity;
    }
					
	// Class methods

	private void initialize() {
		setPreferredSize(new Dimension(45, 45));
	}

}
