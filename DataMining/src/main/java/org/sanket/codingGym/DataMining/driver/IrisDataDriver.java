package org.sanket.codingGym.DataMining.driver;

import java.io.IOException;

import org.sanket.codingGym.DataMining.etl.NumericalDatasetExtractor;

public class IrisDataDriver {

	public static void main(String[] args) throws IOException {
		NumericalDatasetExtractor numericalExtractor = new NumericalDatasetExtractor("IrisDataset/iris.data");
		
		
	}
}