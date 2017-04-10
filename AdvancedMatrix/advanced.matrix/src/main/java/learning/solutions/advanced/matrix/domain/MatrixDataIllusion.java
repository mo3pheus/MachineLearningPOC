package learning.solutions.advanced.matrix.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MatrixDataIllusion {
	public static final int STEP_SIZE = 5;

	public static List<Point> generateRobotPositions(Point start, Point end) {
		List<Point> positions = new ArrayList<Point>();
		int startX = start.x;
		int startY = start.y;
		while (startX != end.x) {
			startX = (startX > end.x) ? (startX - STEP_SIZE) : (startX + STEP_SIZE);
			Point tempPoint = new Point(startX, startY);
			positions.add(tempPoint);
		}

		while (startY != end.y) {
			startY = (startY > end.y) ? (startY - STEP_SIZE) : (startY + STEP_SIZE);
			Point tempPoint = new Point(startX, startY);
			positions.add(tempPoint);
		}

		return positions;
	}
}
