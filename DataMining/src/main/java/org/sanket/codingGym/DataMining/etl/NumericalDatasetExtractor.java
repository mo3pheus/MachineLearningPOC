package org.sanket.codingGym.DataMining.etl;

import java.util.List;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.sanket.codingGym.DataMining.data.IrisDataPoint;
import org.sanket.codingGym.DataMining.utilities.Environment;

public class NumericalDatasetExtractor {
	private List<IrisDataPoint>	irisData	= new ArrayList<IrisDataPoint>();
	private FileReader			fileReader;
	private BufferedReader		bufferedReader;

	public NumericalDatasetExtractor(String location) throws IOException {
		location = Environment.dataLocation + "/" + location;
		try {
			fileReader = new FileReader(location);
		} catch (FileNotFoundException nfe) {
			nfe.printStackTrace(System.out);
		}

		bufferedReader = new BufferedReader(fileReader);
		String line = null;
		try {
			line = bufferedReader.readLine();
			while (line != null) {
				IrisDataPoint dataPoint = new IrisDataPoint(line, ",");
				irisData.add(dataPoint);
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bufferedReader.close();
		fileReader.close();

		System.out.println(" IrisData read from location," + location + " length = " + irisData.size());

		System.out.println("Here is the data");
		for (IrisDataPoint d : irisData) {
			System.out.println(d.toString());
		}
	}

	public List<IrisDataPoint> getIrisData() {
		return irisData;
	}
}
