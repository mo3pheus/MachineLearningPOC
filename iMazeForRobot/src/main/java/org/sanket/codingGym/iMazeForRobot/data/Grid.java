package org.sanket.codingGym.iMazeForRobot.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.sanket.codingGym.iMazeForRobot.environment.EnvironmentUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Grid implements IDrawStuff {
	private static final String	GRID_COLOR	= "maze.environment.grid.color";
	private List<Line>			gridLines;
	private Properties			mazeDefinition;

	private class Line {
		public Point	x;
		public Point	y;

		public Line(int x1, int y1, int x2, int y2) {
			x = new Point(x1,y1);
			y = new Point(x2, y2);
		}
	}

	public Grid(Properties mazeDefinition) {
		this.mazeDefinition = mazeDefinition;
		gridLines = new ArrayList<Line>();
	}

	/**
	 * @param g2
	 * 
	 *            This function takes all the graphical elements in the class
	 *            and draws them on the graphics2D object passed in.
	 */
	public void build() {
		// Based on the mazeDefinitions, draw grid
		int frameHeight = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		int frameWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
		int cellWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));

		for (int i = 0; i < frameHeight; i = i + cellWidth ) {
			gridLines.add(new Line(i,0, i, frameHeight) );
			gridLines.add(new Line(0, i, frameWidth, i));
		}
	}

	public Color getColor() {
		return EnvironmentUtils.findColor(mazeDefinition.getProperty(GRID_COLOR));
	}

	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
		for (int i = 0; i < gridLines.size(); i++) {
			g2.drawLine(gridLines.get(i).x.x, gridLines.get(i).x.y, gridLines.get(i).y.x, gridLines.get(i).y.y);
		}
	}
}
