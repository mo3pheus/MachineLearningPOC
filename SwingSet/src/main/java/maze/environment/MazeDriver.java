package maze.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class MazeDriver {

	public static void main(String[] args) throws Exception {
		System.out.println("Hello from the RoboMaze");
		// FileInputStream propFile = new FileInputStream(args[0]);
		FileInputStream propFile = new FileInputStream(
				"//home/sanket//Documents//Code//MyProjects//Java/MavenProjects//codingGym//SwingSet//src//main//resources//mazeDefinition.properties");
		Properties mazeProperties = new Properties();
		mazeProperties.load(propFile);

		new Maze(mazeProperties);
		// maze.renderMaze(g2);
	}

}