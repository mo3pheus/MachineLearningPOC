package learning.solutions.advanced.matrix.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import learning.solutions.advanced.matrix.domain.MatrixArchitect;

public class MatrixCreation {

	private static Properties matrixConfig = null;

	public static void main(String[] args) throws IOException {
		System.out.println("Hello to the Robo-Maze_World");
		URL url = MatrixCreation.class.getClassLoader().getResource("mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		matrixConfig = new Properties();
		matrixConfig.load(propFile);

		MatrixArchitect creator = new MatrixArchitect(matrixConfig);
		creator.dispose();
	}
}
