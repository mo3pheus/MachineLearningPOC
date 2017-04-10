package learning.solutions.advanced.matrix.engineeringLevel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.Cell;
import learning.solutions.advanced.matrix.domain.Grid;
import learning.solutions.advanced.matrix.domain.MatrixElement;
import learning.solutions.advanced.matrix.domain.Robot;
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
			Robot robot = new Robot(matrixConfig);
			robot.setLocation(position);
			Cell cell = new Cell(matrixConfig);
			Grid grid = new Grid(matrixConfig);
			WallBuilder wallBuilder = new WallBuilder(matrixConfig);
			matrixCitizens.add(grid);
			matrixCitizens.add(cell);
			matrixCitizens.add(wallBuilder);
			matrixCitizens.add(robot);

			createMatrix(matrixCitizens, canvas, matrixConfig);
			oldPosition = position;
		}
	}

	private static void createMatrix(List<MatrixElement> matrixCitizens, Graphics2D canvas, Properties matrixConfig) {
		for (MatrixElement mCitizen : matrixCitizens) {
			mCitizen.draw(canvas);
		}

		try {
			Thread.sleep(Long.parseLong(matrixConfig.getProperty(EnvironmentUtils.ANIMATION_PACE_DELAY)));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
