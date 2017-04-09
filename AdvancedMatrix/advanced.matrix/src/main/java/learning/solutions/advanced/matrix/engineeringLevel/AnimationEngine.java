package learning.solutions.advanced.matrix.engineeringLevel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import learning.solutions.advanced.matrix.domain.BlankSlate;
import learning.solutions.advanced.matrix.domain.MatrixElement;
import learning.solutions.advanced.matrix.domain.Robot;

public class AnimationEngine {
	public static void animate(List<MatrixElement> elements, Graphics2D canvas, List<Point> positions, int robotIndex,
			int cellWidth) throws Exception {
		Robot robot = (Robot) elements.get(robotIndex);

		for (int j = 0; j < positions.size(); j++) {
			Point currentLocation = robot.getLocation();
			for (int i = 0; i < elements.size(); i++) {
				if (i != robotIndex) {
					elements.get(i).draw(canvas);
				}
			}
			robot.draw(canvas);
			robot.setLocation(positions.get(j));
			Thread.sleep(25);
			if (j != positions.size() - 1) {
				canvas.clearRect(currentLocation.x, currentLocation.y, cellWidth, cellWidth);
			}
		}
	}
	
	public static void animateRobot(Graphics2D canvas, List<Point> positions, int cellWidth) {
		MatrixElement matrix = new BlankSlate();
		
		for(int i = 0; i < positions.size(); i++){
			Point position = positions.get(i);
			
		}
	}
}
