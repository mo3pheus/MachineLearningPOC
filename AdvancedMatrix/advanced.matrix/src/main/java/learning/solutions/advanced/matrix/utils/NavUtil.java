package learning.solutions.advanced.matrix.utils;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.NavCell;

public class NavUtil {
	public static Map<Integer, NavCell> populateGridMap(Properties matrixConfig) {
		int totalHeight = Integer.parseInt(matrixConfig.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		int cellWidth = Integer.parseInt(matrixConfig.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));

		int pointCount = totalHeight / cellWidth;
		int id = 0;
		Map<Integer, NavCell> gridMap = new HashMap<Integer, NavCell>();
		for (int i = 1; i < pointCount; i++) {
			for (int j = 1; j < pointCount; j++) {
				Point tempPoint = new Point(i * cellWidth, j * cellWidth);
				NavCell nCell = new NavCell(tempPoint, id);
				gridMap.put(id, nCell);
				id++;
			}
		}
		return gridMap;
	}

	public static int findNavId(Map<Integer, NavCell> gridMap, Point center) {
		int id = -1;

		for (int i : gridMap.keySet()) {
			NavCell temp = gridMap.get(i);
			if (temp.getCenter().equals(center)) {
				id = i;
				break;
			}
		}

		return id;
	}
}
