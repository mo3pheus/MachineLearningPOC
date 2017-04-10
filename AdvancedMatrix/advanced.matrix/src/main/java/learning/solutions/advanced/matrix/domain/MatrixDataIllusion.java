package learning.solutions.advanced.matrix.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MatrixDataIllusion {
	public static List<Point> generateRobotPositions(Point start, Point end) {
		List<Point> positions = new ArrayList<Point>();
		int startX = start.x;
		int startY = start.y;
		while (startX != end.x) {
			Point tempPoint = (startX > end.x) ? new Point(--startX, startY) : new Point(++startX, startY);
			positions.add(tempPoint);
		}

		while (startY != end.y) {
			Point tempPoint = (startY > end.y) ? new Point(startX, --startY) : new Point(startX, startY++);
			positions.add(tempPoint);
		}

		return positions;
	}
}
