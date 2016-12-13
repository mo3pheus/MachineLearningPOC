package org.sanket.codingGym.iMazeForRobot.navigation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.sanket.codingGym.iMazeForRobot.data.Cell;
import org.sanket.codingGym.iMazeForRobot.data.Robot;
import org.sanket.codingGym.iMazeForRobot.data.Wall;
import org.sanket.codingGym.iMazeForRobot.environment.EnvironmentUtils;

public class NavigationEngine
{

    private Properties    mazeConfiguration;
    private Robot         robot;
    private Cell          destination;
    private Wall[]        walls;
    private List<NavNode> adjacencyMatrix;
    private List<Point>   path;
    private int           frameLength;
    private int           cellWidth;

    private List<Point> findAdjacentNodes(Point p)
    {
        /* One node can have at most 4 adjacent nodes, since diagonal movement is not allowed */
        final Point[] adNodes =
        { new Point(p.x, p.y + cellWidth), new Point(p.x, p.y - cellWidth), new Point(p.x + cellWidth, p.y), new Point(p.x - cellWidth, p.y) };

        List<Point> adPoints = new ArrayList<Point>();
        for (Point tmp : adNodes)
        {
            if (tmp.x >= 0 && tmp.y >= 0 && !hitsWall(tmp))
            {
                adPoints.add(tmp);
            }
        }
        return adPoints;
    }

    private boolean hitsWall(Point p)
    {
        for (Wall w : walls)
        {
            if (w.isPartOfWall(p))
            {
                return true;
            }
        }
        return false;
    }

    public NavigationEngine(Properties mazeConfig, Robot robot, final Wall[] walls)
    {
        /* load the basics */
        this.mazeConfiguration = mazeConfig;
        this.robot = robot;
        this.destination = new Cell(mazeConfiguration);
        this.walls = walls;
        this.frameLength = Integer.parseInt(mazeConfiguration.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
        this.cellWidth = Integer.parseInt(mazeConfiguration.getProperty(EnvironmentUtils.CELL_WIDTH_PROPERTY));
        this.adjacencyMatrix = new ArrayList<NavNode>();
        
        fillAdjacencyMatrix();
        this.path = null;
    }

    /**
     * Returns a string representation of the NavEngine
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.adjacencyMatrix.size(); i++)
        {
            sb.append(adjacencyMatrix.get(i).toString());
        }
        return sb.toString();
    }

    /**
     * @param robot
     * @param mazeDefinition
     * @return
     */
    public List<Point> getDemoRobotPath(Robot robot, Properties mazeDefinition)
    {
        List<Point> points = new ArrayList<Point>();
        Cell temp = new Cell(mazeDefinition);

        // move horizontally, y = robotStartLocation.y
        for (int x = robot.getLocation().x; x != temp.getLocation().x;)
        {
            Point tmp = new Point(x, robot.getLocation().y);
            points.add(tmp);
            x = (x < temp.getLocation().x) ? (x + 1) : (x - 1);
        }

        // move vertically, x = startPosition of temp
        for (int y = robot.getLocation().y; y != temp.getLocation().y;)
        {
            Point tmp = new Point(temp.getLocation().x, y);
            points.add(tmp);
            y = (y < temp.getLocation().y) ? (y + 1) : (y - 1);
        }

        try
        {
            Thread.sleep(20l);
            System.out.println(" Points size = " + points.size());
        }
        catch (Exception e)
        {
        }
        return points;
    }

    public List<Point> getPath()
    {
        return path;
    }

    /**
     * Given the wall definitions, fill the adjacencyMatrix.
     */
    private void fillAdjacencyMatrix()
    {
        int i = 0;
        for (int x = 0; x < frameLength; x = x + cellWidth)
        {
            for (int y = 0; y < frameLength; y = y + cellWidth)
            {
                i = i + 1;
                Point temp = new Point(x, y);
                if (!hitsWall(temp))
                {
                    NavNode navNode = new NavNode(i, temp);
                    navNode.setAdjacentNodes(findAdjacentNodes(navNode.getLocation()));
                    adjacencyMatrix.add(navNode);
                }
            }
        }
    }
}
