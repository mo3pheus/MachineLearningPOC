package maze.environment;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class AlertCell {
	private Point	location;
	int				width;
	int				height;
	Rectangle		alertCell;
	Area			area;
	Color			color;

	public AlertCell(int width, int height, Point location, Color color) {
		this.width = width;
		this.height = height;
		this.location = location;
		this.alertCell = new Rectangle(location.x, location.y, width, height);
		this.area = new Area(alertCell);
		this.color = color;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point p) {
		location = p;
		alertCell = new Rectangle(location.x, location.y, width, height);
	}

	public Area getArea() {
		return area;
	}

	public Rectangle getCell() {
		return alertCell;
	}

	public Color getColor() {
		return color;
	}
}