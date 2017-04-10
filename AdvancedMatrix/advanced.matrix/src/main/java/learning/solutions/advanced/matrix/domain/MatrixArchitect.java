package learning.solutions.advanced.matrix.domain;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import learning.solutions.advanced.matrix.engineeringLevel.AnimationEngine;
import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class MatrixArchitect extends Frame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4198825794676956718L;
	private Properties			matrixConfig		= null;
	private List<Point>			robotPositions		= null;

	private void render(Graphics2D canvas) {
		String robotStPositionStr = matrixConfig.getProperty(EnvironmentUtils.ROBOT_START_LOCATION);
		String robotEndPositionStr = matrixConfig.getProperty(EnvironmentUtils.DESTN_POSN_PROPERTY);
		Point robotStartLocation = new Point(Integer.parseInt(robotStPositionStr.split(",")[0]),
				Integer.parseInt(robotStPositionStr.split(",")[1]));
		Point robotEndLocation = new Point(Integer.parseInt(robotEndPositionStr.split(",")[0]),
				Integer.parseInt(robotEndPositionStr.split(",")[1]));
		robotPositions = (robotPositions == null)
				? MatrixDataIllusion.generateRobotPositions(robotStartLocation, robotEndLocation) : robotPositions;
		try {
			AnimationEngine.animateRobot(canvas, robotPositions, matrixConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(NORMAL);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D canvas = (Graphics2D) g;
		render(canvas);
	}

	public void setRobotPositions(List<Point> robotPositions) {
		this.robotPositions = new ArrayList<Point>();
		this.robotPositions = robotPositions;
	}

	public MatrixArchitect(Properties matrixDefinition) {
		super("Matrix");
		this.matrixConfig = matrixDefinition;

		int frameHeight = Integer.parseInt(this.matrixConfig.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		int frameWidth = Integer.parseInt(this.matrixConfig.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
		this.setSize(frameHeight, frameWidth);
		this.setVisible(true);
	}
}
