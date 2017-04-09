package learning.solutions.advanced.matrix.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import learning.solutions.advanced.matrix.domain.MatrixElement;
import learning.solutions.advanced.matrix.domain.Robot;
import learning.solutions.advanced.matrix.domain.BlankSlate;
import learning.solutions.advanced.matrix.domain.Grid;
import learning.solutions.advanced.matrix.domain.MatrixArchitect;

public class MatrixCreation {

	public static void main(String[] args) throws IOException {
		System.out.println("Hello to the Robo-Maze_World");
		URL url = MatrixCreation.class.getClassLoader().getResource("mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		Properties matrixConfig = new Properties();
		matrixConfig.load(propFile);

		MatrixElement matrix = new BlankSlate(matrixConfig);
		matrix.setProperties(matrixConfig);
		matrix = new Grid(matrix);
		matrix = new Robot(matrix);

		MatrixArchitect creator = new MatrixArchitect(matrixConfig);
		creator.setMatrix(matrix);
		
	}

}