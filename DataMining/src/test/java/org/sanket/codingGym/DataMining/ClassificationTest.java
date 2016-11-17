package org.sanket.codingGym.DataMining;

import org.sanket.codingGym.DataMining.algorithms.classification.GenericClassifier;

public class ClassificationTest {

	public static void main(String[] args) throws Exception {
		/* datasets/IrisDataset/iris.data */
		GenericClassifier gc = new GenericClassifier("datasets/IrisDataset/iris.data", 4, ",", 3);
		gc.buildClassifier();
		gc.reportResults();
	}
}
