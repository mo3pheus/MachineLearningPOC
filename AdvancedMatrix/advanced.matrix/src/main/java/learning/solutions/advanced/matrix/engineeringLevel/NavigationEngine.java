package learning.solutions.advanced.matrix.engineeringLevel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.NavCell;
import learning.solutions.advanced.matrix.domain.PerformsNavigation;
import learning.solutions.advanced.matrix.utils.EnvironmentUtils;
import learning.solutions.advanced.matrix.utils.NavUtil;

public class NavigationEngine implements PerformsNavigation {

	private Map<Integer, NavCell>	gridMap			= null;
	private Properties				matrixConfig	= null;

	public NavigationEngine(Properties matrixConfig) {
		this.matrixConfig = matrixConfig;
		this.gridMap = new HashMap<Integer, NavCell>();
		gridMap = NavUtil.populateGridMap(matrixConfig);
		configureAdjacency();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Integer i : gridMap.keySet()) {
			NavCell nCell = gridMap.get(i);
			sb.append("\n ============================================ ");
			sb.append("\n Id = " + i + " Center = " + nCell.toString());
		}

		return sb.toString();
	}

	/**
	 * This method provides the navigation functionality to get from point start
	 * to point end. The following code implements the A* algorithm.
	 */
	public List<NavCell> navigate(NavCell start, NavCell end) {
		List<NavCell> path = new ArrayList<NavCell>();
		List<NavCell> open = new ArrayList<NavCell>();
		List<NavCell> closed = new ArrayList<NavCell>();

		if (start.equals(end)) {
			path.add(start);
			return path;
		}

		open.add(start);
		boolean cont = true;
		while (cont) {
			NavCell current = getMinFNode(open);
			closed.add(current);

			if (current.equals(end)) {
				end = current;
				cont = false;
				break;
			}

			double gCost = 0.0d;
			double hCost = start.getCenter().distance(end.getCenter());
			double fCost = gCost + hCost;

			for (NavCell adjNode : current.getAdjacentNodes()) {
				checkAndUpdateCost(current, adjNode, open, closed,
						current.getfCost() + (current.getCenter().distance(adjNode.getCenter())));

				if ((adjNode.getCenter() != null) && (!closed.contains(adjNode))) {
					double newGCost = current.getgCost() + current.getCenter().distance(adjNode.getCenter());
					if ((newGCost < adjNode.getCenter().distance(start.getCenter())) || (!open.contains(adjNode))) {

						adjNode.setParent(current);
						if (!open.contains(adjNode)) {
							open.add(adjNode);
						}
					}
				}
			}
		}

		if (open.isEmpty() && !closed.contains(end)) {
			// no path
			return null;
		}

		NavCell tempNode = end;
		while (tempNode.getParent() != null) {
			path.add(tempNode);
			tempNode = tempNode.getParent();
		}

		return path;
	}

	private void checkAndUpdateCost(NavCell current, NavCell t, List<NavCell> open, List<NavCell> closed, double cost) {
		if (t.getCenter() == null || closed.contains(t)) {
			return;
		}

		double tFCost = t.gethCost() + cost;
		boolean inOpen = open.contains(t);
		if (!inOpen || tFCost < t.getfCost()) {
			t.setfCost(tFCost);
			t.setParent(current);
			if (!inOpen) {
				open.add(t);
			}
		}
	}

	private void configureAdjacency() {
		for (int id : gridMap.keySet()) {
			NavCell nCell = gridMap.get(id);
			AdjacencyCalculator adSensor = new AdjacencyCalculator(nCell.getCenter(), matrixConfig);
			nCell.setAdjacentNodes(adSensor.getAdjacentNodes());
			gridMap.put(id, nCell);
		}
	}

	private NavCell getMinFNode(List<NavCell> list) {
		int minIndex = 0;
		double minFScore = Double.MAX_VALUE;
		for (int i = 0; i < list.size(); i++) {
			NavCell nCell = list.get(i);
			if (nCell.getfCost() < minFScore) {
				minFScore = nCell.getfCost();
				minIndex = i;
			}
		}
		NavCell leastFCell = list.remove(minIndex);
		return leastFCell;
	}

}
