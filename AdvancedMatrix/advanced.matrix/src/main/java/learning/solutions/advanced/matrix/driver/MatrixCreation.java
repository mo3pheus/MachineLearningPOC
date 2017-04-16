package learning.solutions.advanced.matrix.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import learning.solutions.advanced.matrix.domain.MatrixArchitect;
import learning.solutions.advanced.matrix.engineeringLevel.NavigationEngine;

public class MatrixCreation {

	private static Properties matrixConfig = null;

	public static void main(String[] args) throws IOException {
		System.out.println("Hello to the Robo-Maze_World");
		MatrixArchitect creator = new MatrixArchitect(getMatrixConfig());
	}

	public static Properties getMatrixConfig() throws IOException {
		URL url = MatrixCreation.class.getResource("/mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		matrixConfig = new Properties();
		matrixConfig.load(propFile);
		return matrixConfig;
	}
}
