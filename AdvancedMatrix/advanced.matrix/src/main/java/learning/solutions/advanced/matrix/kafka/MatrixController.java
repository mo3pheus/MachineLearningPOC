/**
 * 
 */
package learning.solutions.advanced.matrix.kafka;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import learning.solutions.advanced.matrix.MatrixCommunication.MatrixCall;
import learning.solutions.advanced.matrix.domain.PortableMatrixConfig;
import learning.solutions.advanced.matrix.utils.SerializationUtil;

/**
 * @author sanketkorgaonkar
 *
 */
public class MatrixController {
	private Producer<String, byte[]>	kafkaTrain;
	private InputStream					inputStream;
	private Properties					kafkaProperties;

	private static byte[] makeCall() {
		PortableMatrixConfig matrixConfig = new PortableMatrixConfig();
		MatrixCall.Builder matrixCallMaker = MatrixCall.newBuilder();
		MatrixCall call = matrixCallMaker.setCommandCenter("zion.main.frame")
				.setMatrixConfig(ByteString.copyFrom(SerializationUtil.serialize(matrixConfig))).build();
		return call.toByteArray();
	}

	public MatrixController(KafkaShipmentBuilder kafkaShipmentBuilder) {
		/*
		 * Set up Kafka producer
		 */
		try {
			System.out.println(kafkaShipmentBuilder.getPropertyFileLocation());
			inputStream = new FileInputStream(kafkaShipmentBuilder.getPropertyFileLocation());
			kafkaProperties = new Properties();
			kafkaProperties.load(inputStream);
			kafkaProperties.put("sourceTopic", kafkaShipmentBuilder.sourceTopic);
			kafkaTrain = new Producer<String, byte[]>(new ProducerConfig(kafkaProperties));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage() throws InterruptedException, InvalidProtocolBufferException {
		for (int i = 0; i < 1; i++) {
			byte[] outputKafkaBytes = makeCall();
			kafkaTrain.send(
					new KeyedMessage<String, byte[]>(kafkaProperties.getProperty("sourceTopic"), outputKafkaBytes));
			System.out.println(" Sending canned interrupt messages to " + kafkaProperties.getProperty("sourceTopic"));
			System.out.println(" Sent message = " + MatrixCall.parseFrom(outputKafkaBytes));
			Thread.sleep(1500l);
		}
	}

	public void cleanUp() throws IOException {
		this.inputStream.close();
		this.kafkaTrain.close();
	}

	public static class KafkaShipmentBuilder {
		private String	sourceTopic;
		private String	propertyFileLocation;

		public KafkaShipmentBuilder withPropertyFileAt(String fileLocation) {
			this.propertyFileLocation = fileLocation;
			return this;
		}

		public KafkaShipmentBuilder withSourceTopic(String sourceTopic) {
			this.sourceTopic = sourceTopic;
			return this;
		}

		public KafkaShipmentBuilder build() throws Exception {
			return this;
		}

		public String getPropertyFileLocation() {
			return propertyFileLocation;
		}
	}

}