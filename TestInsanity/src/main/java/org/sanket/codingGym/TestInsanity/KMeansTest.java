package org.sanket.codingGym.TestInsanity;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.DatasetTools;

public class KMeansTest {

	public static void main(String[] args) throws Exception {
		KMeansClusterer clusterer = new KMeansClusterer(Environment.dataLocation + "//IrisDataset//iris.data", 4, ",");
		Dataset[] clusters = clusterer.cluster(3, 1000);
		int j = 1;
		for (Dataset d : clusters) {
			System.out.println("==============================================================");
			System.out.println(" Centroid for cluster " + j + " is :: " + DatasetTools.average(d).entrySet());
			/*
			 * for (int i = 0; i < d.size(); i++) { System.out.println(
			 * " Class = " + j + " sampleId = " + d.get(i).getID() +
			 * " sample :: " + d.get(i).entrySet()); }
			 */
			j++;
		}
	}

	/**
	 * What a wonderful experience it is to type on this keyboard! This is a
	 * wonderful piece of equipment.
	 * 
	 * I mean yes, this is what I am talking about and it should remain this way
	 * for the rest of my life. I should keep getting this keyboard.
	 */

}
