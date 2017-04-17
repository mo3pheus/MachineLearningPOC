package learning.solutions.advanced.matrix.domain;

import java.awt.Point;

public class NavCell {
	public enum Direction {
		NORTH(0), SOUTH(1), EAST(2), WEST(3);
		private final int value;

		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private Point	center			= null;
	private int		id				= -1;
	private Point[]	adjacentNodes	= new Point[Direction.values().length];

	public NavCell(Point center, int id) {
		this.center = center;
		this.id = id;
	}

	public Point[] getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(Point[] adjacentNodes) {
		this.adjacentNodes = adjacentNodes;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return " Id = " + id + " Center = " + center.toString();
	}
}
