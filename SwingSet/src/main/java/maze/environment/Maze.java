package maze.environment;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

import maze.environment.Wall.IllegalWallDefinitionException;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.io.FileInputStream;

public class Maze extends Frame {
	/**
	 * 
	 */
	private static final long	serialVersionUID		= 34L;

	private static final String	FRAME_HEIGHT_PROPERTY	= "maze.environment.frame.height";
	private static final String	FRAME_WIDTH_PROPERTY	= "maze.environment.frame.width";
	private static final String	CELL_WIDTH_PROPERTY		= "maze.environment.cell.width";
	private static final String	NUM_WALLS_PROPERTY		= "maze.environment.num.walls";
	private static final String	WALL_DEFS_PROPERTY		= "maze.environment.wall.definitions";
	private static final String	START_POSN_PROPERTY		= "maze.environment.start.position";
	private static final String	DESTN_POSN_PROPERTY		= "maze.environment.destination.position";

	private static final Color	GRID_COLOR				= Color.lightGray;

	private List<Pair>			gridPoints;
	private int					frameHeight;
	private int					frameWidth;
	private int					cellWidth;
	private int					numWalls;

	private AlertCell			origin;
	private AlertCell			destination;

	private Wall[]				walls;
	private MazeListener		mazeListener			= new MazeListener();

	/**
	 * @param properties
	 * 
	 *            The following properties are essential.
	 *            "maze.environment.frame.height",
	 *            "maze.environment.frame.width", "maze.environment.cell.width",
	 *            "maze.environment.num.walls",
	 *            "maze.environment.wall.definitions";
	 */
	public Maze(Properties properties) {
		/*
		 * Call parent
		 */
		super("ROBO-MAZE");

		/*
		 * Collect properties for maze definition
		 */
		this.frameHeight = Integer.parseInt(properties.getProperty(FRAME_HEIGHT_PROPERTY));
		this.frameWidth = Integer.parseInt(properties.getProperty(FRAME_WIDTH_PROPERTY));
		this.cellWidth = Integer.parseInt(properties.getProperty(CELL_WIDTH_PROPERTY));

		/*
		 * Instantiate essentials
		 */
		this.setSize(frameWidth, frameHeight);
		this.gridPoints = new ArrayList<Pair>();
		this.setVisible(true);
		this.setName("RoboMaze");
		
		this.addWindowListener(mazeListener);

		/*
		 * Define walls, gridPoints, travelCells
		 */
		defineEndPoints(properties.getProperty(START_POSN_PROPERTY), properties.getProperty(DESTN_POSN_PROPERTY));
		defineGrid();
		try {
			defineWalls(properties);
		} catch (IllegalWallDefinitionException e) {
			e.printStackTrace();
		}
	}

	private void defineWalls(Properties properties) throws IllegalWallDefinitionException {
		this.numWalls = Integer.parseInt(properties.getProperty(NUM_WALLS_PROPERTY));
		this.walls = new Wall[this.numWalls];

		for (int i = 0; i < numWalls; i++) {
			String wallDef = properties.getProperty(WALL_DEFS_PROPERTY + "." + Integer.toString(i));
			String[] walParamStr = wallDef.split(",");
			int[] walParams = new int[4];
			if (walParamStr.length != 4) {
				System.out.println(" Wall params and wall Prop string are incompatible.");
				return;
			}

			for (int j = 0; j < walParams.length; j++) {
				walParams[j] = Integer.parseInt(walParamStr[j]);
			}

			walls[i] = new Wall(this, walParams);
		}
	}

	private void defineEndPoints(String startDef, String endDef) {
		Point startPosn = new Point(Integer.parseInt(startDef.split(",")[0]), Integer.parseInt(startDef.split(",")[1]));
		Point destPosn = new Point(Integer.parseInt(endDef.split(",")[0]), Integer.parseInt(endDef.split(",")[1]));
		origin = new AlertCell(2 * cellWidth, 2 * cellWidth, startPosn, Color.cyan);
		destination = new AlertCell(2 * cellWidth, 2 * cellWidth, destPosn, Color.green);
	}

	private void defineGrid() {
		for (int x = 0; x < getWidth(); x = x + cellWidth) {
			Pair temp = new Pair(new Point(x, 0), new Point(x, getHeight()));
			gridPoints.add(temp);
		}

		for (int y = 0; y < getHeight(); y = y + cellWidth) {
			Pair temp = new Pair(new Point(0, y), new Point(getWidth(), y));
			gridPoints.add(temp);
		}
	}

	private void drawGrid(Graphics2D g2) {
		g2.setColor(GRID_COLOR);

		/*
		 * Draw the grid
		 */
		if (gridPoints.isEmpty()) {
			System.out.println("Sorry grid is empty");
			return;
		}

		for (int i = 0; i < gridPoints.size(); i++) {
			Pair linePoints = gridPoints.get(i);
			g2.setColor(Color.lightGray);
			g2.drawLine((int) linePoints.getA().getX(), (int) linePoints.getA().getY(), (int) linePoints.getB().getX(),
					(int) linePoints.getB().getY());
		}
	}

	private void drawWalls(Graphics2D g2) {
		g2.setColor(Wall.WALL_COLOR);

		/*
		 * Draw walls
		 */
		for (int i = 0; i < walls.length; i++) {
			g2.setColor(Color.darkGray);
			g2.fill(walls[i].getWall());
		}
	}

	private void drawAlertCells(Graphics2D g2) {
		/*
		 * Draw start and end positions
		 */
		g2.setColor(origin.getColor());
		g2.fill(origin.getCell());
		g2.setColor(destination.getColor());
		g2.fill(destination.getCell());
	}

	/**
	 * @param g2
	 * @throws Exception
	 * 
	 *             Some bug in the navigation code, its not moving
	 */
	private void navigate(Graphics2D g2) throws Exception {
		if (origin.getLocation().equals(destination.getLocation())) {
			return;
		}

		/*
		 * reduce horizontal distance
		 */
		while (origin.getLocation().x != destination.getLocation().x) {
			g2.clearRect(origin.getCell().x, origin.getCell().y, origin.getCell().height, origin.getCell().width);
			int x = origin.getLocation().x;

			if (origin.getLocation().x < destination.getLocation().x) {
				x += cellWidth;
				origin.setLocation(new Point(x, origin.getLocation().y));
			} else {
				x -= cellWidth;
				origin.setLocation(new Point(x, origin.getLocation().y));
			}
			Thread.sleep(200l);
			drawGrid(g2);
			drawWalls(g2);
			drawAlertCells(g2);
			g2.fill(origin.getCell());
		}

		/*
		 * reduce vertical distance
		 */
		while (origin.getLocation().y != destination.getLocation().y) {
			g2.clearRect(origin.getCell().x, origin.getCell().y, origin.getCell().height, origin.getCell().width);

			int y = origin.getLocation().y;
			if (origin.getLocation().y < destination.getLocation().y) {
				y = y + cellWidth;
				origin.setLocation(new Point(origin.getLocation().x, y));
			} else {
				y = y - cellWidth;
				origin.setLocation(new Point(origin.getLocation().x, y));
			}
			Thread.sleep(200l);
			drawGrid(g2);
			drawWalls(g2);
			drawAlertCells(g2);
			g2.fill(origin.getCell());
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		/*
		 * navigate
		 */
		try {
			navigate(g2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Hello from the RoboMaze");
		FileInputStream propFile = new FileInputStream(
				"//home/sanket//Documents//Code//MyProjects//Java/MavenProjects//codingGym//SwingSet//src//main//resources//mazeDefinition.properties");
		//FileInputStream propFile = new FileInputStream(args[0]);
		Properties mazeProperties = new Properties();
		mazeProperties.load(propFile);

		new Maze(mazeProperties);
		// maze.renderMaze(g2);
	}

}
