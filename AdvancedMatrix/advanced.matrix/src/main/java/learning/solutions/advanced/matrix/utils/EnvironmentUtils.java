package learning.solutions.advanced.matrix.utils;

import java.awt.Color;

public class EnvironmentUtils {
	public static final String	FRAME_HEIGHT_PROPERTY	= "maze.environment.frame.height";
	public static final String	FRAME_WIDTH_PROPERTY	= "maze.environment.frame.width";
	public static final String	CELL_WIDTH_PROPERTY		= "maze.environment.cell.width";
	public static final String	NUM_WALLS_PROPERTY		= "maze.environment.num.walls";
	public static final String	WALL_DEFS_PROPERTY		= "maze.environment.wall.definitions";
	public static final String	START_POSN_PROPERTY		= "maze.environment.start.position";
	public static final String	DESTN_POSN_PROPERTY		= "maze.environment.destination.position";

	public static Color findColor(String color) {
		if (color.equals("red")) {
			return Color.red;
		} else if (color.equals("lightGray")) {
			return Color.lightGray;
		} else if (color.equals("darkGray")) {
			return Color.darkGray;
		} else if (color.equals("blue")) {
			return Color.blue;
		} else if (color.equals("green")) {
			return Color.green;
		} else if (color.equals("orange")) {
			return Color.orange;
		}

		else {
			System.out.println(" Color is unknown - known choices are red, lightGray, darkGray, blue, green " + color);
			return null;
		}
	}
}