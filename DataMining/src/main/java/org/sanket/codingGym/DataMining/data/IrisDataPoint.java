package org.sanket.codingGym.DataMining.data;

import java.text.ParseException;

public class IrisDataPoint {
	private double	sepalLength;
	private double	sepalWidth;
	private double	petalLength;
	private double	petalWidth;
	private String	classification;
	private int		dataLength	= 5;

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public void setSepalLength(double sepalLength) {
		this.sepalLength = sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public void setSepalWidth(double sepalWidth) {
		this.sepalWidth = sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public void setPetalLength(double petalLength) {
		this.petalLength = petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public void setPetalWidth(double petalWidth) {
		this.petalWidth = petalWidth;
	}

	public String toString() {
		return "SepalLength," + sepalLength + ",SepalWidth" + sepalWidth + ",PetalLength" + petalLength + ",PetalWidth"
				+ petalWidth + ",Class" + classification;
	}

	public IrisDataPoint(String line, String delimiter) {
		if (line.split(delimiter).length != dataLength) {
			// throw new IllegalArgumentException("Data length does not match
			// with provided data.");
			System.out.println(" Weird Line = " + line);

		} else {
			String[] data = line.split(delimiter);
			try {
				sepalLength = Double.parseDouble(data[0]);
				sepalWidth = Double.parseDouble(data[1]);
				petalLength = Double.parseDouble(data[2]);
				petalWidth = Double.parseDouble(data[3]);
				classification = data[4];
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace(System.out);
			}
		}
	}
}
