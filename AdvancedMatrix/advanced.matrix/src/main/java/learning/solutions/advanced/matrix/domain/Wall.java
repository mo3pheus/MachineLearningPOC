package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Properties;

import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class Wall extends VirtualElement {

	private static final String	WALL_COLOR		= "maze.environment.wall.color";
	private int					frameWidth;
	private int					frameHeight;
	private int					cellWidth;
	private int[]				definition		= new int[4];
	private Properties			mazeDefinition	= null;
	private MatrixElement		existingWorld	= null;

	public static class IllegalWallDefinitionException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IllegalWallDefinitionException(String message) {
			super(message);
		}
	}

	private Rectangle2D getWall() {
		return new Rectangle2D.Double((double) definition[0], (double) definition[1], (double) definition[2],
				(double) definition[3]);
	}
	
	public Wall(MatrixElement existingWorld){
		this.existingWorld = existingWorld;
	}

	public boolean intersects(Point p) {
		Rectangle2D cell = new Rectangle2D.Double(p.x, p.y, cellWidth, cellWidth);
		Rectangle2D wl = getWall();
		return cell.intersects(wl);
	}

	public void setDefinition(int[] definition) throws IllegalWallDefinitionException {
		if (definition.length == 4 || definition[0] + definition[3] >= frameWidth
				|| definition[1] + definition[2] >= frameHeight) {
			this.definition = definition;
		} else {
			throw new IllegalWallDefinitionException(
					" Invalid definition passed in. definition[] should be of length = 4"
							+ " \n Format is as follows: 0,1 are x0,y0 point of origin, 2,3 are height, width of the wall");
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(getColor());
		g2.fill(getWall());
		existingWorld.draw(g2);
	}

	@Override
	public void build(Properties mazeDefinition) {
		this.mazeDefinition = mazeDefinition;
		this.frameHeight = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		this.frameWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
		this.cellWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
	}

	@Override
	public Color getColor() {
		return EnvironmentUtils.findColor(mazeDefinition.getProperty(WALL_COLOR));
	}
}