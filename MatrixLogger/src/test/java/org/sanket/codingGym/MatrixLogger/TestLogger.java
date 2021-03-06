package org.sanket.codingGym.MatrixLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestLogger extends TestCase {
	public static String		fileName	= "/home/sanket/Documents/Code/MyProjects/Java/MavenProjects/codingGym/MatrixLogger/src/test/Output/testFile";
	public static final String	DELIMITER	= ",";
	public static final String	LOG_FORMAT	= "ApplicationName, logType, logStatment";
	public static final boolean	APPEND		= false;

	private Logger				logger		= null;

	public TestLogger(String testName) throws IOException {
		super(testName);
		setUp();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(TestLogger.class);
	}

	public void setUp() throws IOException {
		logger = new Logger(fileName, LOG_FORMAT, DELIMITER, APPEND);
	}

	public void testLogger() throws Exception {

		String logStatement = "TestLogger, Info, This is a test statement";
		String[] expectedComponents = logStatement.split(DELIMITER);

		logger.log(logStatement);

		/*
		 * Open the logFile and read from it.
		 */

		BufferedReader br = new BufferedReader(new FileReader(fileName + ".txt"));
		try {
			String line = br.readLine();

			while (line != null) {
				String[] actualComponents = line.split(DELIMITER);
				assert (actualComponents.length == expectedComponents.length + Logger.NUM_EXTRA_FIELDS);

				for (int i = Logger.NUM_EXTRA_FIELDS; i < actualComponents.length; i++) {
					System.out.println(" i = " + i + " other Index " + (i - Logger.NUM_EXTRA_FIELDS) + ","
							+ actualComponents[i] + "," + expectedComponents[i - Logger.NUM_EXTRA_FIELDS]);
					assert (actualComponents[i].equals(expectedComponents[i - Logger.NUM_EXTRA_FIELDS]));
				}

				line = br.readLine();
			}
		} finally {
			br.close();
		}

		/*
		 * Delete the created file
		 */
		try {
			File file = new File(fileName + ".txt");
			file.delete();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		assert (true);
	}

	/*
	 * public void testTimeLogger() throws IOException { logger = new
	 * Logger(fileName, LOG_FORMAT, DELIMITER); String logStatement =
	 * "TestLogger, Info, This is a test statement"; logger.log(logStatement);
	 * //assert (true); }
	 */
}
