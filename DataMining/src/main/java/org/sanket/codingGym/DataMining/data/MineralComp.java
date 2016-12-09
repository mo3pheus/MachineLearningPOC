package org.sanket.codingGym.DataMining.data;

import java.util.Arrays;

public class MineralComp {
	// Schema - BHID FROM TO FE P SIO2 AL2O3 LOI CAO MN MGO TIO2 K2O S NA2O
	public String	bhId;
	public double	fromDepthInM;
	public double	toDepthInM;
	public double[]	composition;

	public MineralComp(String dataPoint) {
		try {
			composition = new double[3];
			String[] values = dataPoint.split(",");

			bhId = values[0];
			fromDepthInM = Double.parseDouble(values[1]);
			toDepthInM = Double.parseDouble(values[2]);
			int j = 0;
			for (int i = 3; i < 6; i++) {
				composition[j] = Double.parseDouble(values[i]);
			}
		} catch (Exception e) {
		}
	}

	public boolean isMineral() {
		boolean mineral = false;
		if (composition[0] > 30.0d && composition[1] <= 50.0d && composition[2] <= 5.0d) {
			mineral = true;
		}
		return mineral;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(
				bhId + "," + fromDepthInM + "," + toDepthInM + "," + Arrays.toString(composition) + " ," + isMineral());

		return sb.toString();
	}
}