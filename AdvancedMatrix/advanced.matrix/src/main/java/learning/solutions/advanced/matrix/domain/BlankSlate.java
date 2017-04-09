package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Properties;

public class BlankSlate extends MatrixElement {
	public static final String	WINDOW_WIDTH	= "maze.environment.window.width";

	private Point				location		= null;
	
	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	public BlankSlate(Properties matrixConfig) {
		location = new Point(0, 0);
		this.setMatrixConfig(matrixConfig);
	}

	public Point getLocation() {
		return location;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
	}
}
