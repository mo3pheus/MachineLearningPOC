package org.sanket.codingGym.MatrixLogger;

import java.io.PrintWriter;
import java.util.Date;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author sanket
 * 
 *         This class is used to log program errors and exceptions along with
 *         normal operational statements.
 *
 */
public class Logger {

	public static final int	NUM_EXTRA_FIELDS	= 2;
	public String			logFormat			= "Timestamp,Date,ApplicationName,LogType,ErrorMessage";
	public String			delimiter			= ",";

	private PrintWriter		writer				= null;
	private FileWriter		fileWriter			= null;
	private String			dateFields			= "Timestmap,Date,";

	/**
	 * @param fileName
	 * @param delimiter
	 * @throws IOException
	 * 
	 *             This constructor initializes the fileName to be used for
	 *             storing the log statements. Note:: Actual fileName will have
	 *             System.time() appended to the end of the given string.
	 * 
	 */
	public Logger(String fileName, String delimiter) throws IOException {
		this.delimiter = delimiter;
		this.fileWriter = new FileWriter(fileName + Long.toString(System.currentTimeMillis()) + ".txt");
		this.writer = new PrintWriter(fileWriter, true);
	}

	/**
	 * @param fileName
	 * @param logFormat
	 * @param delimiter
	 * @throws IOException
	 * 
	 *             This constructor initializes the fileName to be used for
	 *             storing the log statements. Note:: Actual fileName will have
	 *             System.time() appended to the end of the given string. This
	 *             constructor allows the user to specify the log statement
	 *             format.
	 */
	public Logger(String fileName, String logFormat, String delimiter) throws IOException {
		this.logFormat = dateFields + logFormat;
		this.delimiter = delimiter;

		this.fileWriter = new FileWriter(fileName + Long.toString(System.currentTimeMillis()) + ".txt");
		this.writer = new PrintWriter(fileWriter, true);
	}

	/**
	 * @param fileName
	 * @param logFormat
	 * @param delimiter
	 * @param appendToFile
	 * @throws IOException
	 * 
	 *             This constructor initializes the fileName to be used for
	 *             storing the log statements. Note:: Actual fileName will have
	 *             System.time() appended to the end of the given string. This
	 *             constructor allows the user to specify the log statement
	 *             format. This takes in a boolean to determine if the statement
	 *             is to be appended to the given file or not.
	 */
	public Logger(String fileName, String logFormat, String delimiter, boolean appendToFile) throws IOException {
		this.logFormat = dateFields + logFormat;
		this.delimiter = delimiter;

		this.fileWriter = new FileWriter(fileName + ".txt");
		this.writer = new PrintWriter(fileWriter, appendToFile);
	}

	/**
	 * @param logStatement
	 * @throws Exception
	 * 
	 *             Appends the logStatment at the end of the logFile this class
	 *             was asked to create.
	 */
	public void log(String logStatement) throws IOException {
		/*
		 * Perform sanity checks!
		 */
		if (logFormat.split(delimiter).length != logStatement.split(delimiter).length + NUM_EXTRA_FIELDS) {
			throw new IOException(
					"Given log statment is not consistent with the logger instance logFormat. Please give log statements in the following format ::"
							+ logFormat);
		}

		/*
		 * Write the statment to the file
		 */
		writer.println(Long.toString(System.currentTimeMillis()) + "," + new Date(System.currentTimeMillis()) + ","
				+ logStatement);

		/*
		 * flush the writer
		 */
		writer.flush();
		fileWriter.flush();
	}

	@Override
	public void finalize() throws IOException{
		if (fileWriter != null)
			fileWriter.close();
		if (writer != null)
			writer.close();
	}

}
