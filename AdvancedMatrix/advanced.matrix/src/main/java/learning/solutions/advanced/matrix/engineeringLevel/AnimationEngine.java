package learning.solutions.advanced.matrix.engineeringLevel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.Cell;
import learning.solutions.advanced.matrix.domain.Grid;
import learning.solutions.advanced.matrix.domain.MatrixElement;
import learning.solutions.advanced.matrix.domain.WallBuilder;
import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class AnimationEngine {
	public static void animateRobot(Graphics2D canvas, List<Point> positions, Properties matrixConfig)
			throws Exception {
		if (positions.isEmpty()) {
			throw new Exception("List of positions is empty!");
		}

		int cellWidth = Integer.parseInt(matrixConfig.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
		Point oldPosition = positions.get(0);
		for (int i = 0; i < positions.size(); i++) {
			canvas.clearRect(oldPosition.x, oldPosition.y, cellWidth, cellWidth);

			Point position = positions.get(i);
			List<MatrixElement> matrixCitizens = new ArrayList<MatrixElement>();
			Cell cell = new Cell(matrixConfig);
			Grid grid = new Grid(matrixConfig);
			WallBuilder wallBuilder = new WallBuilder(matrixConfig);
			Cell robo = new Cell(matrixConfig);
			robo.setLocation(position);
			robo.setColor(EnvironmentUtils.findColor("royalBlue"));
			robo.setCellWidth(15);

			matrixCitizens.add(grid);
			matrixCitizens.add(cell);
			matrixCitizens.add(wallBuilder);
			matrixCitizens.add(robo);

			long delayMs = Long.parseLong(matrixConfig.getProperty(EnvironmentUtils.ANIMATION_PACE_DELAY));
			createMatrix(matrixCitizens, canvas, delayMs);
			oldPosition = position;
		}
	}

	private static void createMatrix(List<MatrixElement> matrixCitizens, Graphics2D canvas, long delayMs) {
		for (MatrixElement mCitizen : matrixCitizens) {
			mCitizen.draw(canvas);
		}

		try {
			Thread.sleep(delayMs);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
