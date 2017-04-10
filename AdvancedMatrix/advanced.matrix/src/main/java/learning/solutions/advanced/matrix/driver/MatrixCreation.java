package learning.solutions.advanced.matrix.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.MatrixElement;
import learning.solutions.advanced.matrix.domain.Robot;
import learning.solutions.advanced.matrix.domain.WallBuilder;
import learning.solutions.advanced.matrix.domain.Cell;
import learning.solutions.advanced.matrix.domain.Grid;
import learning.solutions.advanced.matrix.domain.MatrixArchitect;

public class MatrixCreation {

	private static Properties matrixConfig = null;

	public static void main(String[] args) throws IOException {
		System.out.println("Hello to the Robo-Maze_World");
		URL url = MatrixCreation.class.getClassLoader().getResource("mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		matrixConfig = new Properties();
		matrixConfig.load(propFile);

		List<MatrixElement> elements = new ArrayList<MatrixElement>();
		Robot robot = new Robot(matrixConfig);
		WallBuilder wallBuilder = new WallBuilder(matrixConfig);
		Grid grid = new Grid(matrixConfig);
		Cell destination = new Cell(matrixConfig);
		elements.add(grid);
		elements.add(destination);
		elements.add(wallBuilder);
		elements.add(robot);

		MatrixArchitect creator = new MatrixArchitect(elements, matrixConfig);
		System.out.println(creator.getHeight());
	}
}
