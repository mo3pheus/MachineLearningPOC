package org.sanket.codingGym.DataMining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sanket.codingGym.DataMining.data.BlastHoleGeo;
import org.sanket.codingGym.DataMining.data.MineralComp;

public class OreBoundaryDetection {
	private enum MINERAL_TYPE {
		FE, P, SIO2, AL2O3, LOI, CAO, MN, MGO, TIO2, K2O, S, NA2O
	}

	private static Map<String, BlastHoleGeo>	drillGeoData	= new HashMap<String, BlastHoleGeo>();
	private static List<MineralComp>			minerologyData	= new ArrayList<MineralComp>();

	public static void main(String[] args) throws Exception {
		String thisLine = null;
		BufferedReader geoReader;
		try {
			// open input stream test.txt for reading purpose.
			URL url = OreBoundaryDetection.class.getClassLoader()
					.getResource("datasets//UserStory1//Export_Collars.csv");
			geoReader = new BufferedReader(new FileReader(url.getPath()));
			while ((thisLine = geoReader.readLine()) != null) {
				// System.out.println(thisLine);
				BlastHoleGeo bg = new BlastHoleGeo(thisLine);
				drillGeoData.put(bg.bhId, bg);
				if (bg != null) {
					// System.out.println(bg.toString());
				}
			}

			System.out.println("======================================");
			geoReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		BufferedReader mineralReader;
		try {
			URL url = OreBoundaryDetection.class.getClassLoader()
					.getResource("datasets//UserStory1//Export_Samples_Head.csv");
			mineralReader = new BufferedReader(new FileReader(url.getPath()));
			while ((thisLine = mineralReader.readLine()) != null) {
				/* System.out.println(thisLine); */
				MineralComp mineralData = new MineralComp(thisLine);
				minerologyData.add(mineralData);
				if (mineralData != null) {
					// System.out.println(mineralData.toString());
				}
			}
			mineralReader.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}

		System.out.println("======================================");
		System.out.println("DrillData size = " + drillGeoData.size());
		System.out.println("Mineral Data size = " + minerologyData.size());

		/*
		 * for (String bhId : drillGeoData.keySet()) { try {
		 * System.out.println("BHID = " + (drillGeoData.get(bhId)).toString() +
		 * " mineral =" + minerologyData.get(bhId).isMineral()); } catch
		 * (Exception e) {
		 * }
		 * 
		 * }
		 */

		for (int i = 0; i < minerologyData.size(); i++) {
			MineralComp temp = minerologyData.get(i);
			System.out.println(" BHID = " + drillGeoData.get(temp.bhId).toString() + "::" + temp.toString());
		}
	}
}