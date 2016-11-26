package maze.environment;

import java.awt.Point;

public class Pair {
	private Point	a;
	private Point	b;

	public Pair(Point a, Point b) {
		this.a = a;
		this.b = b;
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public String toString() {
		return " A.x = " + a.getX() + " A.y = " + a.getY() + " B.x = " + b.getX() + " B.y = " + b.getY();
	}
}
