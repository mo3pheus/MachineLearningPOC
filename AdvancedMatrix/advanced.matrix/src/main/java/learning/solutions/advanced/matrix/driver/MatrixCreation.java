package learning.solutions.advanced.matrix.driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

import learning.solutions.advanced.matrix.domain.MatrixArchitect;
import learning.solutions.advanced.matrix.engineeringLevel.NavigationEngine;

public class MatrixCreation {

	private static Properties matrixConfig = null;

	public static void main(String[] args) throws IOException {
		configureLogging();
		System.out.println("Hello to the Robo-Maze_World");
		NavigationEngine navEngine = new NavigationEngine(getMatrixConfig());
		new MatrixArchitect(getMatrixConfig(), navEngine.getAnimationCalibratedRobotPath());
	}

	private static void configureLogging() {
		FileAppender fa = new FileAppender();
		fa.setFile("navEngineOutput/navEnginePath_" + Long.toString(System.currentTimeMillis()) + ".txt");
		fa.setLayout(new PatternLayout(PatternLayout.DEFAULT_CONVERSION_PATTERN));
		fa.activateOptions();
		org.apache.log4j.Logger.getRootLogger().addAppender(fa);
	}

	public static Properties getMatrixConfig() throws IOException {
		URL url = MatrixCreation.class.getResource("/mazeDefinition.properties");
		FileInputStream propFile = new FileInputStream(url.getPath());
		matrixConfig = new Properties();
		matrixConfig.load(propFile);
		return matrixConfig;
	}
}
