package learning.solutions.advanced.matrix.utils;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
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
		for (int i = 0; i < pointCount; i++) {
			for (int j = 0; j < pointCount; j++) {
				Point tempPoint = new Point(j * cellWidth, i * cellWidth);
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

	public static final double getGCost(NavCell current, NavCell start, int cellWidth) {
		if (current == null || current.equals(start)) {
			return 0.0d;
		} else
			return cellWidth + getGCost(current.getParent(), start, cellWidth);
	}

	public static final double getFCost(NavCell current, NavCell start, NavCell end, int cellWidth) {
		return (getGCost(current, start, cellWidth) + current.getCenter().distance(end.getCenter()));
	}
	
	public static final int getMinFCell(List<NavCell> listCells, NavCell start, NavCell end, int cellWidth){
		double minFScore = Double.MAX_VALUE;
		int minFId = 0;
		
		for(int i = 0; i < listCells.size(); i++){
			NavCell nCell = listCells.get(i);
			double currentFScore = getFCost(nCell, start, end, cellWidth);
			if( currentFScore < minFScore ){
				minFId = i;
				minFScore = currentFScore;
			}
		}
		
		return minFId;
	}
}
