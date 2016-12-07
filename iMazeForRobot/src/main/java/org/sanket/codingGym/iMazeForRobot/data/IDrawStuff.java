package org.sanket.codingGym.iMazeForRobot.data;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IDrawStuff {
	public void build();
	public Color getColor();
	public void draw(Graphics2D g2);
}
