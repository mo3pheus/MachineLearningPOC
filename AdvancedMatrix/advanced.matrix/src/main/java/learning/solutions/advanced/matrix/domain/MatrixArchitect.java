package learning.solutions.advanced.matrix.domain;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import learning.solutions.advanced.matrix.engineeringLevel.AnimationEngine;
import learning.solutions.advanced.matrix.engineeringLevel.NavigationEngine;
import learning.solutions.advanced.matrix.utils.EnvironmentUtils;
import learning.solutions.advanced.matrix.utils.MatrixDataIllusion;

public class MatrixArchitect extends Frame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4198825794676956718L;
	private Properties			matrixConfig		= null;
	private List<Point>			robotPositions		= null;
	private boolean				animationComplete	= false;
	private NavigationEngine	navigationEngine	= null;

	private void render(Graphics2D canvas) {
		robotPositions = (robotPositions == null) ? MatrixDataIllusion.generateAnimationProfile(matrixConfig)
				: robotPositions;
		try {
			AnimationEngine.animateRobot(canvas, robotPositions, matrixConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		animationComplete = true;
		//System.exit(NORMAL);
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

		this.navigationEngine = new NavigationEngine(matrixConfig);
		System.out.println(navigationEngine.toString());
	}

	public boolean getAnimationComplete() {
		return animationComplete;
	}

	public NavigationEngine getNavigationEngine() {
		return navigationEngine;
	}
}
