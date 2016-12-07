package org.sanket.codingGym.iMazeForRobot.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Properties;
import org.sanket.codingGym.iMazeForRobot.data.IDrawStuff;
import org.sanket.codingGym.iMazeForRobot.environment.EnvironmentUtils;

public class Cell implements IDrawStuff {

	private static final String	CELL_WIDTH		= "maze.environment.cell.width";
	private static final String	CELL_LOCATION	= "maze.environment.cell.location";
	private static final String	CELL_COLOR		= "maze.environment.cell.color";
	private Point				location;
	private Properties			mazeDefinition;
	private int					cellWidth;

	public Cell(Properties mazeDefinition) {
		this.mazeDefinition = mazeDefinition;

		cellWidth = Integer.parseInt(mazeDefinition.getProperty(CELL_WIDTH));
		location = new Point(Integer.parseInt(mazeDefinition.getProperty(CELL_LOCATION).split(",")[0]),
				Integer.parseInt(mazeDefinition.getProperty(CELL_LOCATION).split(",")[1]));

	}

	public Color getColor() {
		return EnvironmentUtils.findColor(mazeDefinition.getProperty(CELL_COLOR));
	}

	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
		g2.fill(getCell());
	}

	public void build() {
		// TODO Auto-generated method stub
	}

	private Rectangle2D getCell() {
		return new Rectangle2D.Double(location.getX(), location.getY(), (double) cellWidth, (double) cellWidth);
	}
}
