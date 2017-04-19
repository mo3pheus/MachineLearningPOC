package learning.solutions.advanced.matrix.engineeringLevel;

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
	private int						cellWidth		= 0;

	public NavigationEngine(Properties matrixConfig) {
		this.matrixConfig = matrixConfig;
		this.gridMap = new HashMap<Integer, NavCell>();
		gridMap = NavUtil.populateGridMap(matrixConfig);
		this.cellWidth = Integer.parseInt(matrixConfig.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
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
		List<NavCell> open = new ArrayList<NavCell>();
		List<NavCell> closed = new ArrayList<NavCell>();
		open.add(start);

		boolean done = false;
		int iter = 0;
		while (!done) {
			System.out.println("Iteration = " + iter++);
			int minFIndex = NavUtil.getMinFCell(open, start, end, cellWidth);
			NavCell current = open.get(minFIndex);
			open.remove(minFIndex);
			closed.add(current);
			System.out.println("Node added = " + current.getId());

			if (current.equals(end)) {
				System.out.println("You did reach the end");
				return calcPath(start, current);
			}

			List<NavCell> adjacentNodes = NavUtil.getAdjNodesFromGrid(gridMap, current.getAdjacentNodes());
			for (int i = 0; i < adjacentNodes.size(); i++) {
				NavCell cAdjNode = adjacentNodes.get(i);
				

				if (!open.contains(cAdjNode)) {
					cAdjNode.setParent(current);
					double gCost = NavUtil.getGCost(cAdjNode, start, cellWidth);
					cAdjNode.setgCost(gCost);
					int hCost = Math.abs(cAdjNode.getCenter().x - end.getCenter().x)
							+ Math.abs(cAdjNode.getCenter().y - end.getCenter().y);
					cAdjNode.sethCost(hCost);
					cAdjNode.setfCost(hCost + cAdjNode.getgCost());
					open.add(cAdjNode);
				} else {
					double newGCost = NavUtil.getGCost(current, start, cellWidth);
					if (cAdjNode.getgCost() > newGCost) {
						cAdjNode.setgCost(newGCost);
						cAdjNode.setParent(current);
					}
				}
			}
		}

		if (open.isEmpty()) {
			System.out.println(
					"No path was found between start => " + start.toString() + " and end => " + end.toString());
			return null;
		}
		return null;
	}

	private List<NavCell> calcPath(NavCell start, NavCell current) {
		List<NavCell> path = new ArrayList<NavCell>();

		while (true) {
			System.out.println(" Node along path = " + current.getId());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			path.add(current);
			if (current.getParent() != null) {
				if (current.getParent().equals(start)) {
					path.add(start);
					return path;
				}
				path.addAll(calcPath(start, current.getParent()));
			} else {
				break;
			}
		}

		return path;
	}

	/**
	 * @return the gridMap
	 */
	public Map<Integer, NavCell> getGridMap() {
		return gridMap;
	}

	/**
	 * @param gridMap
	 *            the gridMap to set
	 */
	public void setGridMap(Map<Integer, NavCell> gridMap) {
		this.gridMap = gridMap;
	}

	private void saveCosts(NavCell start, NavCell end, List<NavCell> openList) {
		for (NavCell nCell : openList) {
			double gCost = NavUtil.getGCost(nCell, start, cellWidth);
			double hCost = nCell.getCenter().distance(end.getCenter());
			double fCost = gCost + hCost;
			nCell.setfCost(fCost);
			nCell.setgCost(gCost);
			nCell.sethCost(hCost);
		}
	}

	private void configureAdjacency() {
		for (int id : gridMap.keySet()) {
			NavCell nCell = gridMap.get(id);
			AdjacencyCalculator adSensor = new AdjacencyCalculator(nCell.getCenter(), matrixConfig);
			nCell.setAdjacentNodes(adSensor.getAdjacentNodes());
		}
	}

	private void setParent(NavCell parent, List<NavCell> children) {
		for (NavCell child : children) {
			child.setParent(parent);
		}
	}
}
