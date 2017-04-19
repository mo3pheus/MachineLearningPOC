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

		NavUtil.addUniqueToOpen(NavUtil.getAdjNodesFromGrid(gridMap, start.getAdjacentNodes()), open, closed);
		setParent(start, open);
		saveCosts(start, end, open);
		closed.add(start);
		boolean cont = true;
		while (cont) {
			int minFIndex = NavUtil.getMinFCell(open, start, end, cellWidth);
			NavCell current = open.get(minFIndex);
			open.remove(minFIndex);
			closed.add(current);

			if (current.equals(end) || (!current.equals(end) && open.isEmpty())) {
				cont = false;
			}

			List<NavCell> currAdjNodes = NavUtil.getAdjNodesFromGrid(gridMap, current.getAdjacentNodes());
			NavUtil.addUniqueToOpen(currAdjNodes, open, closed);

			for (NavCell nCell : currAdjNodes) {
				if (closed.contains(nCell)) {
					continue;
				}
				/* check to see if this path is better */
				double newGCost = current.getgCost() + current.getCenter().distance(nCell.getCenter());
				if (NavUtil.getGCost(nCell, start, cellWidth) > newGCost) {
					nCell.setParent(current);
					nCell.setgCost(newGCost);
					nCell.sethCost(nCell.getCenter().distance(end.getCenter()));
					nCell.setfCost(nCell.getgCost() + nCell.gethCost());
				}
			}
		}

		/* no path */
		if (open.isEmpty() && !closed.contains(end)) {
			return null;
		}

		List<NavCell> path = new ArrayList<NavCell>();
		NavCell tempNode = end;
		while (tempNode.getParent() != null) {
			path.add(tempNode);
			tempNode = tempNode.getParent();
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
