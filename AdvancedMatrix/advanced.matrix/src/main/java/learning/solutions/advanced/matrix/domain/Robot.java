package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class Robot extends VirtualElement {
	private static final String	ROBOT_START_LOCATION	= "maze.environment.robot.position";
	private static final String	roboImageLocation		= "robot-tool.png";
	private Properties			matrixConfig			= null;
	private Point				location				= null;
	private MatrixElement		existingWorld			= null;
	private BufferedImage		robotImage				= null;

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Robot(MatrixElement existingWorld) {
		this.existingWorld = existingWorld;
		build(existingWorld.getMatrixConfig());
	}

	@Override
	public void build(Properties mazeDefinition) {
		this.matrixConfig = mazeDefinition;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Robot.class.getClassLoader().getResource(roboImageLocation).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		robotImage = (BufferedImage) img;

		location = new Point(Integer.parseInt(matrixConfig.getProperty(ROBOT_START_LOCATION).split(",")[0]),
				Integer.parseInt(matrixConfig.getProperty(ROBOT_START_LOCATION).split(",")[1]));
	}

	@Override
	public Color getColor() {
		return null;
	}

	@Override
	public void draw(Graphics2D g2) {
		AffineTransform at = new AffineTransform();
		int cellWidth = Integer.parseInt(matrixConfig.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
		at.scale(cellWidth, cellWidth);
		g2.drawImage(robotImage, null, location.x, location.y);
		existingWorld.draw(g2);
	}
}