package org.sanket.codingGym.DataMining.utilities;

import java.util.List;

import internal.tools.machine.learning.DatasetProtoOuterClass;
import internal.tools.machine.learning.DatasetProtoOuterClass.DatasetProto;
import internal.tools.machine.learning.DatasetProtoOuterClass.DatasetProto.Pair;
import internal.tools.machine.learning.DatasetProtoOuterClass.DatasetProto.Sample;
import internal.tools.machine.learning.DatasetProtoOuterClass.DatasetProto.Sample.Builder;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.InstanceTools;

public class DatasetToProtoConverter {

	public static Dataset fromProtoToDataset(DatasetProto proto) {
		Dataset data = new DefaultDataset();

		List<Sample> samples = proto.getSamplesList();
		for (Sample s : samples) {
			List<Pair> dataPoints = s.getInputVectorList();
			double[] vals = new double[dataPoints.size()];
			for (int j = 0; j < dataPoints.size(); j++) {
				vals[j] = dataPoints.get(j).getValue();
			}
			Instance i = new DenseInstance(vals);
			i.setClassValue(s.getClassId());
			data.add(i);
		}

		return data;
	}

	public static DatasetProto fromDatasetToProto(Dataset data) {
		DatasetProto.Builder builder = DatasetProto.newBuilder();
		for (int i = 0; i < data.size(); i++) {
			Instance instance = data.get(i);
			Builder sampleBuilder = DatasetProto.Sample.newBuilder();
			for (Integer j : instance.keySet()) {
				Pair.Builder pairBuilder = Pair.newBuilder();
				pairBuilder.setKey(j);
				pairBuilder.setValue(instance.get(j));
				sampleBuilder.addInputVector(pairBuilder.build());
			}
			sampleBuilder.setClassId((String) (instance.classValue()));
			builder.addSamples(sampleBuilder.build());
		}
		return builder.build();
	}
}
