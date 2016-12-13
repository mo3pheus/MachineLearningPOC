package org.sanket.codingGym.iMazeForRobot.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Properties;
import org.sanket.codingGym.iMazeForRobot.data.IDrawStuff;
import org.sanket.codingGym.iMazeForRobot.environment.EnvironmentUtils;

public class Wall implements IDrawStuff {
	private static final String	WALL_COLOR	= "maze.environment.wall.color";
	private int					frameWidth;
	private int					frameHeight;
	private int[]				definition	= new int[4];
	private Properties			mazeDefinition;

	public static class IllegalWallDefinitionException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IllegalWallDefinitionException(String message) {
			super(message);
		}
	}

	public Wall(Properties mazeDefinition, int[] definition) throws IllegalWallDefinitionException {
		this.mazeDefinition = mazeDefinition;
		this.frameHeight = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		this.frameWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));

		if (definition.length == 4 || definition[0] + definition[3] >= frameWidth
				|| definition[1] + definition[2] >= frameHeight) {
			this.definition = definition;
		} else {
			throw new IllegalWallDefinitionException(
					" Invalid definition passed in. definition[] should be of length = 4"
							+ " \n Format is as follows: 0,1 are x0,y0 point of origin, 2,3 are height, width of the wall");
		}
	}

	public void build() {
		// TODO Auto-generated method stub

	}

	public Color getColor() {
		return EnvironmentUtils.findColor(mazeDefinition.getProperty(WALL_COLOR));
	}

	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.setColor(getColor());
		g2.fill(getWall());
	}

	private Rectangle2D getWall() {
		return new Rectangle2D.Double((double) definition[0], (double) definition[1], (double) definition[2],
				(double) definition[3]);
	}

}
