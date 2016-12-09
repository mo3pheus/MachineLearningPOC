package org.sanket.codingGym.DataMining.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BlastHoleGeo {
	public double		east;
	public double		north;
	public String		bhId;
	public Date			date;
	public DateFormat	df	= new SimpleDateFormat("yyyyMMdd");

	public BlastHoleGeo(String dataPoint) {
		String[] values = dataPoint.split(",");
		try {
			bhId = values[0];
			east = Double.parseDouble(values[1]);
			north = Double.parseDouble(values[2]);
			date = df.parse(values[10]);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public String toString() {
		String temp = bhId + " ," + north + " ," + east + " ." + date.toString();
		return temp;
	}
}