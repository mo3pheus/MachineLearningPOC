package maze.environment;

import java.awt.Color;
import java.awt.Frame;
import java.awt.geom.Rectangle2D;

public class Wall {
	private int[] definition = new int[4];

	public class IllegalWallDefinitionException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IllegalWallDefinitionException(String message) {
			super(message);
		}
	}

	public static final Color WALL_COLOR = Color.darkGray;

	public Wall(Frame frame, int[] definition) throws IllegalWallDefinitionException {
		if (definition.length == 4 || definition[0] + definition[3] >= frame.getWidth()
				|| definition[1] + definition[2] >= frame.getHeight()) {
			this.definition = definition;
		} else {
			throw new IllegalWallDefinitionException(
					" Invalid definition passed in. definition[] should be of length = 4"
							+ " \n Format is as follows: 0,1 are x0,y0 point of origin, 2,3 are height, width of the wall");
		}
	}

	public Rectangle2D getWall() {
		return new Rectangle2D.Double((double) definition[0], (double) definition[1], (double) definition[2],
				(double) definition[3]);
	}
}
