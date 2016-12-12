package org.sanket.codingGym.iMazeForRobot.environment;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import org.sanket.codingGym.iMazeForRobot.data.IDrawStuff;
import org.sanket.codingGym.iMazeForRobot.data.Robot;

public class AnimationEngine {

	public static void animate(List<? extends IDrawStuff> elements, Graphics2D g2, List<Point> positions,
			int robotIndex, int cellWidth) throws Exception {
		Robot robot = (Robot) elements.get(robotIndex);

		for (int j = 0; j < positions.size(); j++) {
			Point currentLocation = robot.getLocation();
			for (int i = 0; i < elements.size(); i++) {
				if (i != robotIndex) {
					elements.get(i).draw(g2);
				}
			}
			robot.draw(g2);
			robot.setLocation(positions.get(j));
			Thread.sleep(25);
			if (j != positions.size() - 1) {
				g2.clearRect(currentLocation.x, currentLocation.y, cellWidth, cellWidth);
			}
		}
	}
}
