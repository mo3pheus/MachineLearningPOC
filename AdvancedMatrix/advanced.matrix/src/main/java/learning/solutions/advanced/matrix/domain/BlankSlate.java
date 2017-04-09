package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Properties;

public class BlankSlate extends MatrixElement {
	public static final String	WINDOW_WIDTH	= "maze.environment.window.width";

	private Point				location		= null;
	private int					slateWidth;

	@Override
	public Color getColor() {
		return Color.WHITE;
	}

	private Rectangle2D getCell() {
		return new Rectangle2D.Double(location.getX(), location.getY(), (double) slateWidth, (double) slateWidth);
	}

	public BlankSlate(Properties matrixConfig) {
		slateWidth = Integer.parseInt(matrixConfig.getProperty(WINDOW_WIDTH));
		location = new Point(0, 0);
	}

	public Point getLocation() {
		return location;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
	}
}