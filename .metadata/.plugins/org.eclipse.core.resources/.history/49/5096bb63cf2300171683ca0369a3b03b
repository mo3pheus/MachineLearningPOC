package learning.solutions.advanced.matrix.engineeringLevel;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.NavCell;
import learning.solutions.advanced.matrix.utils.EnvironmentUtils;
import learning.solutions.advanced.matrix.utils.NavUtil;

public class NavigationEngine {

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

	private void configureAdjacency() {
		for (int id : gridMap.keySet()) {
			NavCell nCell = gridMap.get(id);
			AdjacencyCalculator adSensor = new AdjacencyCalculator(nCell.getCenter(), matrixConfig);
			nCell.setAdjacentNodes(adSensor.getAdjacentNodes());
			gridMap.put(id, nCell);
		}
	}
}
