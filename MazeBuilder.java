package org.sanket.codingGym.iMazeForRobot.environment;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.sanket.codingGym.iMazeForRobot.data.Cell;
import org.sanket.codingGym.iMazeForRobot.data.Grid;
import org.sanket.codingGym.iMazeForRobot.data.IDrawStuff;
import org.sanket.codingGym.iMazeForRobot.data.MazeFactory;
import org.sanket.codingGym.iMazeForRobot.data.Robot;
import org.sanket.codingGym.iMazeForRobot.data.Wall;
import org.sanket.codingGym.iMazeForRobot.data.Wall.IllegalWallDefinitionException;
import org.sanket.codingGym.iMazeForRobot.navigation.NavigationEngine;

/**
 * @author sanket
 * 
 *         This class defines the maze structure and all graphical elements that
 *         go into it.
 *
 */
public class MazeBuilder<T extends IDrawStuff> extends Frame {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private MazeFactory			mazeFactory;
	private NavigationEngine	navCore;
	private Wall[]				walls;
	private Robot				robot;
	private Grid				grid;

	private List<T>				elements			= new ArrayList<T>();
	private Properties			mazeDefinition;
	private int					numWalls;
	private boolean             executed            = false;

	public MazeBuilder(Properties mazeDefinition) {
		super("ROBO MAZE");
		mazeFactory = new MazeFactory();
		this.mazeDefinition = mazeDefinition;
		this.robot = (Robot) mazeFactory.getMazeObject("Robot", mazeDefinition);
		this.grid = (Grid) mazeFactory.getMazeObject("Grid", mazeDefinition);
		
		/*
		 * Build the graphics
		 */
		build();
	}
	
	private void writeToFile(){
	    try
        {
            FileWriter fw = new FileWriter(new File("output//NavEngineOutputFile" + System.currentTimeMillis() + ".csv"));
            fw.write(navCore.toString());
            fw.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	private void defineWalls(Properties properties) throws IllegalWallDefinitionException {
		this.numWalls = Integer.parseInt(properties.getProperty(EnvironmentUtils.NUM_WALLS_PROPERTY));
		this.walls = new Wall[this.numWalls];
		
		for (int i = 0; i < numWalls; i++) {
			String wallDef = properties.getProperty(EnvironmentUtils.WALL_DEFS_PROPERTY + "." + Integer.toString(i));
			String[] walParamStr = wallDef.split(",");
			int[] walParams = new int[4];
			if (walParamStr.length != 4) {
				throw new IllegalWallDefinitionException(" Wall params and wall Prop string are incompatible.");
			}

			for (int j = 0; j < walParams.length; j++) {
				walParams[j] = Integer.parseInt(walParamStr[j]);
			}
			
			walls[i] = (Wall) mazeFactory.getMazeObject(mazeDefinition, walParams);
		}
		navCore = new NavigationEngine(mazeDefinition, robot, walls);
		System.out.println(navCore.toString());
    }

	@SuppressWarnings("unchecked")
	private void build() {
		// robot and grid
		elements.add((T) robot);
		elements.add((T) grid);

		int frameHeight = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		int frameWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
		this.setSize(frameHeight, frameWidth);
		this.setVisible(true);

		// walls
		try {
			defineWalls(mazeDefinition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < walls.length; i++) {
			walls[i].build();
		}
		elements.addAll((Collection<? extends T>) Arrays.asList(walls));

		// destination
		elements.add((T) new Cell(mazeDefinition));
		System.out.println(elements.size());
	}

	public void render(List<T> elements, Graphics2D g2) {
	    executed = !executed;
	    
		/* Draw the maze */
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).build();
			elements.get(i).draw(g2);
		}
		
		/* Save off the navEngine output */
		writeToFile();

		/* Animate the maze. */
		try {
			AnimationEngine.animate(elements, g2, navCore.getDemoRobotPath(robot, mazeDefinition), 0,
					Integer.parseInt(mazeDefinition.getProperty(Cell.CELL_WIDTH)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		if(!executed){
    		Graphics2D g2 = (Graphics2D) g;
    		render(elements, g2);
		} else{
		    System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Hello to the Robo-Maze_World");
		URL url = MazeBuilder.class.getClassLoader().getResource("mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		Properties mazeProperties = new Properties();
		mazeProperties.load(propFile);

		new MazeBuilder<IDrawStuff>(mazeProperties);
	}
}
