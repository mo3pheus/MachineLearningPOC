package org.sanket.codingGym.iMazeForRobot.navigation;

import java.awt.Point;
import java.util.List;

public class NavNode
{
    List<Point> adjacentNodes;
    Point       location;
    int         id;
    public float f, g, h;
    public NavNode parent;

    public NavNode(int id, Point p)
    {
        location = p;
        this.id = id;
        parent = null;
    }
    
    public NavNode(Point p){
        location = p;
        id = -1;
        parent = null;
    }

    public int getID()
    {
        return id;
    }

    public Point getLocation()
    {
        return location;
    }

    public List<Point> getAdjacentNodes()
    {
        return adjacentNodes;
    }

    public void setAdjacentNodes(List<Point> adNodes)
    {
        this.adjacentNodes = adNodes;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n" + id);
        sb.append("," + location.x + "," + location.y);
        for (Point p : adjacentNodes)
        {
            sb.append("," + p.x + "," + p.y);
        }

        return sb.toString();
    }
}
