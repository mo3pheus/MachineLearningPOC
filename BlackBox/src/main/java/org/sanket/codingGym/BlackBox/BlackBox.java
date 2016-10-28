package org.sanket.codingGym.BlackBox;

import org.sanket.codingGym.MatrixLogger.Logger;

public class BlackBox {

	public static void main(String[] args) {
		Logger logger = null;
		try {
			logger = new Logger(
					"/home/sanket/Documents/Code/MyProjects/Java/MavenProjects/codingGym/BlackBox/src/test/Output/BlackBox",
					"name,type,message", ",");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("Hello");
				logger.log("BlackBox,Info,This is a statement " + Integer.toString(i));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}

}
