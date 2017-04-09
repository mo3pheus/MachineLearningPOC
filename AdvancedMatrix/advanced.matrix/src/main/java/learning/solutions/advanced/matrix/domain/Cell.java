package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Properties;

import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class Cell extends VirtualElement {
	public static final String	CELL_WIDTH		= "maze.environment.cell.width";
	public static final String	CELL_LOCATION	= "maze.environment.cell.location";
	public static final String	CELL_COLOR		= "maze.environment.cell.color";
	private MatrixElement		existingWorld;
	private Point				location;
	private Properties			matrixConfig;
	private int					cellWidth;

	private Rectangle2D getCell() {
		return new Rectangle2D.Double(location.getX(), location.getY(), (double) cellWidth, (double) cellWidth);
	}

	public Cell(VirtualElement existingWorld) {
		this.existingWorld = existingWorld;
	}

	public void build(Properties matrixConfig) {
		this.matrixConfig = matrixConfig;

		cellWidth = Integer.parseInt(matrixConfig.getProperty(CELL_WIDTH));
		location = new Point(Integer.parseInt(matrixConfig.getProperty(CELL_LOCATION).split(",")[0]),
				Integer.parseInt(matrixConfig.getProperty(CELL_LOCATION).split(",")[1]));
	}

	public Point getLocation() {
		return location;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
		g2.fill(getCell());
		existingWorld.draw(g2);
	}

	@Override
	public Color getColor() {
		return EnvironmentUtils.findColor(matrixConfig.getProperty(CELL_COLOR));
	}
}