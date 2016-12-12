package org.sanket.codingGym.iMazeForRobot.data;

import java.util.Properties;
import org.sanket.codingGym.iMazeForRobot.data.IDrawStuff;
import org.sanket.codingGym.iMazeForRobot.data.Wall.IllegalWallDefinitionException;

public class MazeFactory {

	public IDrawStuff getMazeObject(String name, Properties mazeConfiguration) {
		if (name.equals("Robot")) {
			return new Robot(mazeConfiguration);
		} else if (name.equals("Grid")) {
			return new Grid(mazeConfiguration);
		} else if (name.equals("Destination")) {
			return new Cell(mazeConfiguration);
		}
		return null;
	}

	public IDrawStuff getMazeObject(Properties mazeConfiguration, int[] definition) throws IllegalWallDefinitionException {
			return new Wall(mazeConfiguration, definition);
	}

}
