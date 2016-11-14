package org.sanket.codingGym.DataMining;

import java.io.File;
import java.io.IOException;

import org.sanket.codingGym.DataMining.utilities.DatasetToProtoConverter;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

public class DatasetProtoConversionTest {

	public static void main(String[] args) throws IOException {
		// Get the data into a dataset.
		String location = "/home/sanket/Documents/Code/MyProjects/Java/MavenProjects/codingGym/DataMining/src/main/resources/datasets/IrisDataset/iris.data";
		Dataset input = FileHandler.loadDataset(new File(location), 4, ",");
		System.out.println(DatasetToProtoConverter.fromDatasetToProto(input));
		System.out.println("================================================");
		Dataset x = DatasetToProtoConverter.fromProtoToDataset((DatasetToProtoConverter.fromDatasetToProto(input)));
		for( int i = 0; i < x.size(); i++){
			System.out.println(x.get(i).entrySet() + (String)x.get(i).classValue());
		}
		
	}

}
