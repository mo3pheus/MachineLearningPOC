package org.sanket.codingGym.iMazeForRobot.data;

import java.util.Properties;

import org.sanket.codingGym.iMazeForRobot.data.Wall.IllegalWallDefinitionException;

public class MazeFactory
{
    public IDrawStuff getMazeObject(String objectType, Properties mazeConfiguration)
    {
        if (objectType.equals("Robot"))
        {
            return new Robot(mazeConfiguration);
        }
        else if (objectType.equals("Destination"))
        {
            return new Cell(mazeConfiguration);
        }
        else if (objectType.equals("Grid"))
        {
            return new Grid(mazeConfiguration);
        }
        return null;
    }

    public IDrawStuff getMazeObject(Properties mazeConfiguration, int[] definition)
    {
        try
        {
            return new Wall(mazeConfiguration, definition);
        }
        catch (IllegalWallDefinitionException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
