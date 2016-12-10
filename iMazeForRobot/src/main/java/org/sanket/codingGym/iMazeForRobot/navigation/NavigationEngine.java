package org.sanket.codingGym.iMazeForRobot.navigation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.sanket.codingGym.iMazeForRobot.data.Cell;
import org.sanket.codingGym.iMazeForRobot.data.Robot;

public class NavigationEngine {

	public static List<Point> getRobotPath(Robot robot, Properties mazeDefinition) {
		List<Point> points = new ArrayList<Point>();
		Cell temp = new Cell(mazeDefinition);

		// move horizontally, y = robotStartLocation.y
		for (int x = robot.getLocation().x; x != temp.getLocation().x;) {
			Point tmp = new Point(x, robot.getLocation().y);
			points.add(tmp);
			x = (x < temp.getLocation().x) ? (x + 3) : (x - 3);
		}

		// move vertically, x = startPosition of temp
		for (int y = robot.getLocation().y; y != temp.getLocation().y;) {
			Point tmp = new Point(temp.getLocation().x, y);
			points.add(tmp);
			y = (y < temp.getLocation().y) ? (y + 3) : (y - 3);
		}

		try {
			Thread.sleep(200l);
			System.out.println(" Points size = " + points.size());
		} catch (Exception e) {
		}
		return points;
	}
}
