package org.sanket.codingGym.iMazeForRobot.navigation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathFinder {
	private Point			destination;
	private List<NavNode>	graph;
	private List<NavNode>	open;
	private List<NavNode>	closed;
	private NavNode			start;

	public PathFinder(Point source, Point destination, List<NavNode> graph) {
		if (graph == null || graph.isEmpty()) {
			System.out.println(" Cant have an empty adjacencyMatrix! ");
			return;
		} else {
			System.out.println(" Graph length = " + graph.size());
		}

		this.graph = graph;
		this.destination = destination;
		
		start = null;
		for (NavNode n : graph) {
			/*if (n.getLocation() == null || source == null) {
				System.out.println(" Location was null!");
			} else {
				System.out.println(" Source = " + source.x + "," + source.y + " Node = " + n.getLocation().x + ","
						+ n.getLocation().y);
			}*/

			if (n.getLocation().equals(source)) {
				start = n;
				break;
			}
		}

		if (start == null) {
			throw new RuntimeException(" Start not found");
		} else {
			System.out.println(" Start found => " + start.toString());
		}
	}

	private List<Point> getAdjacentNodes(NavNode n) {
		for (NavNode t : graph) {
			if (t.getLocation().equals(n.getLocation())) {
				return t.getAdjacentNodes();
			}
		}
		return null;
	}

	private static float computeManhattanDistance(Point a, Point b) {
		float dx = (float) Math.abs((double) (a.x - b.x));
		float dy = (float) Math.abs((double) (a.y - b.y));
		return dx + dy;
	}

	public List<Point> findPath() throws NullPointerException {
		// Initialize open and closed list
		open = new ArrayList<NavNode>();
		closed = new ArrayList<NavNode>();

		// insert the starting Node
		start.f = 0;
		open.add(start);

		// iterate
		while (!open.isEmpty()) {
			int loc = leastF(open);
			NavNode q = open.remove(loc);

			/*
			 * generate q's 8 successors and set their parents to q
			 */
			List<NavNode> successors = new ArrayList<NavNode>();
			for (Point successor : q.getAdjacentNodes()) {
				NavNode tmp = new NavNode(successor);
				tmp.parent = q;
				tmp.setAdjacentNodes(getAdjacentNodes(tmp));
				successors.add(tmp);
			}

			for (NavNode tmp : successors) {
				// reached the destination
				if (tmp.getLocation().equals(destination)) {
					return extractLoc(tmp);
				}

				tmp.g = q.g + computeManhattanDistance(tmp.getLocation(), q.getLocation());
				tmp.h = computeManhattanDistance(tmp.getLocation(), destination);
				tmp.f = tmp.g + tmp.h;

				if (!lowerFPresent(tmp, open) && !lowerFPresent(tmp, closed)) {
					open.add(tmp);
				}
			}

			closed.add(q);
		}
		return null;
	}

	private boolean lowerFPresent(NavNode candidate, List<NavNode> list) {
		for (NavNode tmp : list) {
			if (tmp.f < candidate.f) {
				return true;
			}
		}

		return false;
	}

	private int leastF(List<NavNode> list) {
		if (list == null || list.isEmpty()) {
			return -1;
		}

		int loc = 0;
		float f = Float.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).f < f) {
				loc = i;
			}
		}

		return loc;
	}

	public static List<Point> extractLoc(NavNode dest) {
		List<Point> pointsList = new ArrayList<Point>();
		NavNode tmp = dest;
		while (tmp.parent != null) {
			pointsList.add(tmp.getLocation());
			tmp = tmp.parent;
		}

		Collections.reverse(pointsList);

		return pointsList;
	}
}
