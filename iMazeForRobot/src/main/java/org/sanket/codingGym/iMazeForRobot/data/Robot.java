package org.sanket.codingGym.iMazeForRobot.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.sanket.codingGym.iMazeForRobot.environment.EnvironmentUtils;

public class Robot implements IDrawStuff {
	private static final String	ROBOT_START_LOCATION	= "maze.environment.robot.position";
	private static final String	roboImageLocation		= "robot-tool.png";
	//private static final String	roboImageLocation		= "r2d2-128.png";
	private Properties			mazeDefinition;
	private Point				location			= null;

	private BufferedImage		robotImage;

	public Robot(Properties mazeDefinition) {
		this.mazeDefinition = mazeDefinition;
	}

	public void build() {
		// get the image and resize the image
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Robot.class.getClassLoader().getResource(roboImageLocation).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		robotImage = (BufferedImage) img;

		location = new Point(Integer.parseInt(mazeDefinition.getProperty(ROBOT_START_LOCATION).split(",")[0]),
				Integer.parseInt(mazeDefinition.getProperty(ROBOT_START_LOCATION).split(",")[1]));
	}

	public Color getColor() {
		return null;
	}

	public void draw(Graphics2D g2) {
		AffineTransform at = new AffineTransform();
		int cellWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
		at.scale(cellWidth, cellWidth);
		g2.drawImage(robotImage, null, location.x, location.y);
	}
	
	public Point getLocation(){
		return location;
	}
	
	public void setLocation(Point location){
		this.location = location;
	}
}
