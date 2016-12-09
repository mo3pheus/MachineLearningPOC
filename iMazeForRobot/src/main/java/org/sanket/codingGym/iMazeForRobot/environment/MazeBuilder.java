package org.sanket.codingGym.iMazeForRobot.environment;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileInputStream;
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
 * @author sanket This class defines the maze structure and all graphical elements that go into it.
 */
public class MazeBuilder<T extends IDrawStuff> extends Frame
{
    /**
	 * Defines the elements making up the maze.
	 */
    private static final long serialVersionUID = 1L;
    private MazeFactory       mazeFactory;
    private Wall[]            walls;
    private Robot             robot;
    private Grid              grid;

    private List<T>           elements         = new ArrayList<T>();
    private Properties        mazeDefinition;
    private int               numWalls;

    private void defineWalls(Properties properties) throws IllegalWallDefinitionException
    {
        this.numWalls = Integer.parseInt(properties.getProperty(EnvironmentUtils.NUM_WALLS_PROPERTY));
        this.walls = new Wall[this.numWalls];

        for (int i = 0; i < numWalls; i++)
        {
            String wallDef = properties.getProperty(EnvironmentUtils.WALL_DEFS_PROPERTY + "." + Integer.toString(i));
            String[] walParamStr = wallDef.split(",");
            int[] walParams = new int[4];
            if (walParamStr.length != 4)
            {
                System.out.println(" Wall params and wall Prop string are incompatible.");
                return;
            }

            for (int j = 0; j < walParams.length; j++)
            {
                walParams[j] = Integer.parseInt(walParamStr[j]);
            }

            walls[i] = (Wall)mazeFactory.getMazeObject(mazeDefinition, walParams);
        }
    }

    @SuppressWarnings("unchecked")
    private void build()
    {
        /* Robot and grid */
        elements.add((T) robot);
        elements.add((T) grid);

        int frameHeight = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
        int frameWidth = Integer.parseInt(mazeDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
        this.setSize(frameHeight, frameWidth);
        this.setVisible(true);

        /* Walls */
        try
        {
            defineWalls(mazeDefinition);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        elements.addAll((Collection<? extends T>) Arrays.asList(walls));

        /* Destination */
        elements.add((T) new Cell(mazeDefinition));

        System.out.println(elements.size());
    }
    
    /**
     * 
     * @param mazeDefinition
     */
    public MazeBuilder(Properties mazeDefinition)
    {
        super("ROBO MAZE");
        this.mazeDefinition = mazeDefinition;
        
        mazeFactory = new MazeFactory();
        robot = (Robot) mazeFactory.getMazeObject("Robot", mazeDefinition);
        grid = (Grid) mazeFactory.getMazeObject("Grid", mazeDefinition);

        /*
         * Build the graphics
         */
        build();
    }

    /**
     * 
     * @param elements
     * @param g2
     */
    public void render(List<T> elements, Graphics2D g2)
    {
        /* Draw the maze */
        for (int i = 0; i < elements.size(); i++)
        {
            elements.get(i).build();
            elements.get(i).draw(g2);
        }

        /* Animate the robot. */
        try
        {
            AnimationEngine.animate(elements, g2, NavigationEngine.getRobotPath(mazeDefinition, robot), 0, Integer.parseInt(mazeDefinition.getProperty(Cell.CELL_WIDTH)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        /* Terminate execution */
        if(robot.getLocation().equals((new Cell(mazeDefinition)).getLocation())){
            System.out.println("Robot has reached the destination. End of game!");
            System.exit(1);
        }
    }

    /**
     * 
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        render(elements, g2);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
        System.out.println("Hello to the Robo-Maze_World");
        URL url = MazeBuilder.class.getClassLoader().getResource("mazeDefinition.properties");
        FileInputStream propFile = new FileInputStream(url.getPath());
        Properties mazeProperties = new Properties();
        mazeProperties.load(propFile);

        new MazeBuilder<IDrawStuff>(mazeProperties);
    }
}
