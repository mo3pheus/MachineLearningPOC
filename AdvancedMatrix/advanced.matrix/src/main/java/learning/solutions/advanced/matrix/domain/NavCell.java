package learning.solutions.advanced.matrix.domain;

import java.awt.Point;

public class NavCell {
	public enum Direction {
		NORTH(0), SOUTH(1), WEST(2), EAST(3);
		private final int value;

		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private NavCell[]	adjacentNodes	= new NavCell[Direction.values().length];
	private NavCell		parent			= null;
	private Point		center			= null;
	private int			id				= -1;
	private double		gCost			= 0.0d;
	private double		fCost			= 0.0d;
	private double		hCost			= 0.0d;

	/**
	 * @return the parent
	 */
	public NavCell getPaarent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setPaarent(NavCell parent) {
		this.parent = parent;
	}

	/**
	 * @return the gCost
	 */
	public double getgCost() {
		if (parent != null) {
			return parent.getgCost() + center.distance(parent.getCenter());
		}
		return gCost;
	}

	/**
	 * @param gCost
	 *            the gCost to set
	 */
	public void setgCost(double gCost) {
		this.gCost = gCost;
	}

	/**
	 * @return the fCost
	 */
	public double getfCost() {
		return fCost;
	}

	/**
	 * @param fCost
	 *            the fCost to set
	 */
	public void setfCost(double fCost) {
		this.fCost = fCost;
	}

	/**
	 * @return the hCost
	 */
	public double gethCost() {
		return hCost;
	}

	/**
	 * @param hCost
	 *            the hCost to set
	 */
	public void sethCost(double hCost) {
		this.hCost = hCost;
	}

	public NavCell(Point center, int id) {
		this.center = center;
		this.id = id;
	}

	public NavCell[] getAdjacentNodes() {
		return adjacentNodes;
	}

	public void setAdjacentNodes(NavCell[] adjacentNodes) {
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
		StringBuilder sb = new StringBuilder();
		sb.append(" Id = " + id + " Center = " + center.toString());
		int j = 0;
		for (int i = 0; i < adjacentNodes.length; i++) {
			NavCell nCell = adjacentNodes[i];
			if (nCell != null && nCell.getCenter() != null) {
				sb.append("\n adId = " + nCell.getId() + " Location => " + nCell.getCenter().toString());
				j++;
			}
		}

		if (parent != null) {
			sb.append(" \n Parent = " + parent.toString());
		}
		return sb.toString();
	}
}